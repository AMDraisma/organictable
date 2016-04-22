import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Organictable extends JFrame {

    public static void main(String[] args) {
        new Organictable();
    }

    public Organictable() {
        this.setSize(600,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new FilePanel());
        this.pack();
    }

    private class FilePanel extends JPanel {
        public FilePanel() {
            this.setLayout(new GridBagLayout());


            GridBagConstraints c = new GridBagConstraints();
            c.weightx=1;
            c.weighty=1;
            c.fill= GridBagConstraints.BOTH;

            JRadioButton fastaButton = new JRadioButton("Fasta file");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=0;
            this.add(fastaButton, c);

            JRadioButton groupButton = new JRadioButton("Orthomcl file");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=1;
            c.gridy=0;
            this.add(groupButton, c);

            JRadioButton wolfButton = new JRadioButton("WoLF PSORT file");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=2;
            c.gridy=0;
            this.add(wolfButton, c);

            JRadioButton signalButton = new JRadioButton("SignalP file");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=1;
            this.add(signalButton, c);

            JRadioButton hmmerButton = new JRadioButton("HMMER file");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=1;
            c.gridy=1;
            this.add(hmmerButton, c);

            JButton openButton = new JButton("Open file");
            openButton.setSelected(true);
            c.gridwidth=3;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=2;
            this.add(openButton, c);

            ButtonGroup group = new ButtonGroup();
            group.add(fastaButton);
            group.add(groupButton);
            group.add(wolfButton);
            group.add(signalButton);
            group.add(hmmerButton);
        }
    }
}