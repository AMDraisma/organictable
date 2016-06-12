import javax.swing.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CSV {

    String blastfile;
    String z5fastafile;

    public CSV(OTDatabase database) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Choose fumigatus Z5 <-> fumigatus A1163 blast result location");
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            blastfile = fc.getSelectedFile().getAbsolutePath();
        }else{
            return;
        }
        fc = new JFileChooser();
        fc.setDialogTitle("Choose fumigatus Z5 fasta file location");
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            z5fastafile = fc.getSelectedFile().getAbsolutePath();
        }else{
            return;
        }
        fc = new JFileChooser();
        fc.setDialogTitle("Choose CSV save location");
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (file.canWrite()) {
                writeCSV(file, database);
            } else {
                System.out.print("Can't write file");
            }
        }
    }

    public void writeCSV (File file, OTDatabase database) {
        Long time;
        BlastParser blastParser = new BlastParser(blastfile);
        Mapping mapping = new Mapping();
        mapping.makeMapping(z5fastafile);
        time = System.currentTimeMillis();
        String q = "SELECT Prot.protaccession, " +
                "Organism_has_prot.orgaccession, " +
                "Signalp.sig, " +
                "Signalp.conf, " +
                "Hmmer.pfam, " +
                "GROUP_CONCAT(Hmmer.pfamext SEPARATOR ', ')" +
                "FROM Prot " +
                "join Organism_has_prot on Prot.protaccession = Organism_has_prot.protaccession " +
                "join (Select * from Signalp where sig = \"Y\") as Signalp on Prot.protaccession = Signalp.protaccession " +
                "join Hmmer on Prot.protaccession = Hmmer.protaccession " +
                "GROUP BY protaccession";
        ResultSet rs = database.ExecuteQuery(q);
        String org;
        int oint;
        String prot;
        CSVRow row;
        HashMap<String, CSVRow> map = new HashMap<>();
        int proteins = 0;
        if (null != rs) {
            try {
                int c = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    prot = rs.getString(1);
                    row = new CSVRow(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
                    map.put(prot, row);
                    proteins++;
                }
                System.out.println("Found "+proteins+" proteins (took "+ (System.currentTimeMillis() - time) +" millis)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        time = System.currentTimeMillis();
        q = "SELECT Prot.protaccession, " +
            "group_concat(Cluster.protaccession separator ':') " +
            "from Prot " +
            "join (Select * from Cluster where id in (Select id from Cluster where Cluster.protaccession = protaccession)) as Cluster on Cluster.protaccession = Prot.protaccession " +
            "group by id;";
        rs = database.ExecuteQuery(q);
        CSVRow invrow;
        if (null != rs) {
            try {
                int c = rs.getMetaData().getColumnCount();
                while (rs.next()) {
                    prot = rs.getString(1);
                    row = map.get(prot);
                    for (String orth : rs.getString(2).split(":")) {
                        invrow = map.get(orth);
                        if (null != row & null != invrow) {
                            row.addOrth(orth);
                            invrow.addOrth(prot);
                        }
                    }
                }
                System.out.println("Done processing ortholog groups (took "+ (System.currentTimeMillis() - time) +" millis)");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        q = "SELECT orgaccession, protaccession, substrate, expression from Expression;";
        rs = database.ExecuteQuery(q);

        if (null != rs) {
            try {
//                FileWriter asdf = new FileWriter(new File("/home/crude/asd.txt"));

                while (rs.next()) {
                    org = rs.getString(1);
                    prot = rs.getString(2);
                    oint = CSVRow.Organism.parseString(org);
                    switch (oint) {
                        case CSVRow.Organism.A_nidulans_FGSC_A4:
                            if (prot.startsWith("AN")) {
                                if (prot.endsWith("_at")) {
                                    prot = prot.substring(0, prot.length() - 3);
                                }
                                if (prot.endsWith(".3")) {
                                    prot = prot.substring(0, prot.length() - 2);
                                }
                            }else{
                                prot = null;
                            }
                            break;
                        case CSVRow.Organism.A_niger_BO1:
                            if (prot.startsWith("JGI")) {
                                prot = prot.substring(3, prot.length()-3)+"-mRNA";
                            }
                            break;
                        case CSVRow.Organism.A_oryzae_RIB40:
                            if (prot.startsWith("AO")) {
                                prot = prot.substring(0, prot.length()-3);
                            }
                            break;
                        case CSVRow.Organism.A_fumigatus_Z5:
                            if (prot.startsWith("Y6")) {
                                prot = mapping.giveProt(prot);
                                prot = blastParser.Z5ToA(prot);
                            }
                            break;
                        default:
                            prot = null;
                            break;
                    }
                    if (prot != null && map.containsKey(prot)) {
                        row = map.get(prot);
                        row.addExpr(rs.getString(3), Double.parseDouble(rs.getString(4)));
                    }
                }
                System.out.println("Done inserting expression values");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        q = "SELECT protaccession, substrate, data from proteomics where type = 'Area';";
        rs = database.ExecuteQuery(q);
        try {
            if (null != rs) {
                while (rs.next()) {
                    prot = rs.getString(1);
                    if (map.containsKey(prot)) {
                        map.get(prot).addProteomics(rs.getString(2), rs.getDouble(3));
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        try {
            int t = proteins / 10;
            int i = 0;
            FileWriter fw = new FileWriter(file);
            fw.write(CSVRow.header); // TODO: make csv have dynamic column headers
            for (CSVRow r : map.values()) {
                fw.write(r.toString());
                i++;
                if (i % t == 0)
                    System.out.println(String.format("Wrote %d lines", i));
            }
            System.out.println("Done");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
