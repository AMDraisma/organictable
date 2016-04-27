/**
 * Created by aj on 22-4-16.
 *
 * public
 * method (void) parse gene.fasta file
 *      open fasta (put in private arraylist)
 *      collect header
 *          protein accession (>numberhingy)
 *
 *public arraylist get proteins die returned
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;


public class FastaFile extends FileParser {

    public ArrayList<String> proteinList = new ArrayList<String>();
    FastaFileActionListener fastaFileActionListener = new FastaFileActionListener();
    JTextField orgaccessionField;

    public ArrayList<String> getProteins(){
        return proteinList;
    }

    FastaFile( OTDatabase database ){
        super(database);
        String[] extensions =  {"fa", "fasta"};
        File file = OpenFile("Fasta file", extensions);
        if (null != file) {
            parseFile( file );

            this.setSize(400, 300);
            this.setVisible(true);
            this.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();
            JScrollPane jScrollPane = new JScrollPane();
            c.weighty = 0.1f;
            c.weightx = 1;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.gridy = 0;

            c.gridx = 0;
            this.add(new JLabel("Organism accession ID:"), c);

            c.gridx = 1;
            orgaccessionField = new JTextField(10);
            this.add(orgaccessionField, c);


            c.weighty = 0.7f;
            c.gridx = 0;
            c.gridy = 1;
            this.add(jScrollPane, c);

            c.weighty = 0.2f;

            JButton dbButton = new JButton("Insert into database");
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 2;
            dbButton.setActionCommand("insert");
            dbButton.addActionListener(fastaFileActionListener);
            this.add(dbButton, c);

            JButton cancelButton = new JButton("Cancel");
            c.gridx = 1;
            c.gridy = 2;
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(fastaFileActionListener);
            this.add(cancelButton, c);
        }else{
            cancel();
        }
    }


    public void parseFile(File file) {
        FileReader file_to_read = null;
        try {
            file_to_read = new FileReader(file);
            BufferedReader bf = new BufferedReader(file_to_read);

            String aLine;

            while ( (aLine = bf.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ){
                    String pr = aLine.substring( aLine.indexOf( ">" ) +1 , aLine.indexOf( " " ));
                    proteinList.add(pr);
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] getSet() {
        String[][] result = new String[proteinList.size()][1];
        for (int i = 0; i < proteinList.size(); i++) {
            result[i][0] = proteinList.get(i);
        }
        return result;
    }

    public String[][] getSet(String orgaccession) {
        String[][] result = new String[proteinList.size()][2];
        for (int i = 0; i < proteinList.size(); i++) {
            result[i] = new String[]{orgaccession, proteinList.get(i)};
        }
        return result;
    }
    public void cancel() {
        proteinList.clear();
        dispose();
    }

    private class FastaFileActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (Objects.equals(actionEvent.getActionCommand(), "insert")) {
                String orgaccessionfieldtext = orgaccessionField.getText();
                if (orgaccessionfieldtext.length() > 0) {
                    try {
                        ResultSet r = database.ExecuteQuery(
                                "SELECT COUNT(*) FROM Organism WHERE orgaccession = '"+orgaccessionfieldtext+"'"
                        );

                        int i = r.getInt(1);
                        System.out.println(i+"");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                    database.insertIntoDatabase("Prot", getSet());
                    database.insertIntoDatabase("Organism_has_prot", getSet(orgaccessionfieldtext));
            }
            if (Objects.equals(actionEvent.getActionCommand(), "cancel")) {
                cancel();
            }
        }
    }
}

