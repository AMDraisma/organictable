import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CSV {
    public CSV(OTDatabase database) {
        JFileChooser fc = new JFileChooser();
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
                return;
            }
        }
    }

    public void writeCSV (File file, OTDatabase database) {
        String q = "SELECT Prot.protaccession, " +
                "Organism_has_prot.orgaccession, " +
                "Signalp.sig, " +
                "Signalp.conf, " +
                "Hmmer.pfam, " +
                "GROUP_CONCAT(Hmmer.pfamext SEPARATOR ', ')" +
                "FROM Prot " +
                "join Organism_has_prot on Prot.protaccession = Organism_has_prot.protaccession " +
                "join Signalp on Prot.protaccession = Signalp.protaccession " +
                "join Hmmer on Prot.protaccession = Hmmer.protaccession " +
                "GROUP BY protaccession";
        ResultSet rs = database.ExecuteQuery(q);
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
                System.out.println("Found "+proteins+" proteins");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
                System.out.println("Done processing ortholog groups");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        q = "SELECT protaccession, " +
                "substrate, " +
                "expression" +
                "from ExpressionA ";
        rs = database.ExecuteQuery(q);
        if (null != rs) {
            try {
                int c = rs.getMetaData().getColumnCount();
                System.out.println(String.format("Found %d expression values", c));
                while (rs.next()) {
                    prot = rs.getString(1);
                    row = map.get(prot);
                    row.addExpr(rs.getString(2), Double.parseDouble(rs.getString(3)));
                }
                System.out.println("Done inserting expression values");
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
