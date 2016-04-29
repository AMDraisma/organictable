import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
                "Cluster.id, " +
                "Signalp.sig, " +
                "Signalp.conf, " +
                "Hmmer.pfam, " +
                "GROUP_CONCAT(Hmmer.pfamext SEPARATOR ' : ')" +
                "FROM Prot " +
                "join Organism_has_prot on Prot.protaccession = Organism_has_prot.protaccession " +
                "join Cluster on Prot.protaccession = Cluster.protaccession " +
                "join Signalp on Prot.protaccession = Signalp.protaccession " +
                "join Hmmer on Prot.protaccession = Hmmer.protaccession " +
                "GROUP BY Organism_has_prot.orgaccession";

        ResultSet rs = database.ExecuteQuery(q);
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            if (null != rs) {
                try {
                    int c = rs.getMetaData().getColumnCount();
                    StringBuilder sb;
                    fw.write("Protein, Organism, Ortholog group ID, SignalP sig, SignalP conf, Pfam-A family, Pfam-A family description\n"); // TODO: make csv have dynamic column headers
                    while (rs.next()) {
                        sb = new StringBuilder();
                        for (int i = 1; i <= c; i++) {
                            sb.append(rs.getString(i));
                            sb.append('\t');
                        }
                        sb.deleteCharAt(sb.length() - 1);
                        sb.append("\n");
                        fw.write(sb.toString());
//                        System.out.println(sb.toString());
                    }
                    System.out.println("Done");
                } catch (SQLException e) {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
