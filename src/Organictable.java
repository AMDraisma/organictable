import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class   Organictable extends JFrame {

    private FilePanel filepanel;
    private ButtonGroup group;
    private OTDatabase otDatabase;

    public static void main(String[] args) {
        new Organictable();
    }

    public Organictable() {
        otDatabase = new OTDatabase(this);
        this.setSize(600,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        filepanel = new FilePanel();
        this.setContentPane(filepanel);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public FilePanel getFilepanel() {
        return filepanel;
    }

    public OTDatabase getDatabase () {
        return otDatabase;
    }

    private class FilePanel extends JPanel {

        private FilePanelHandler filepanelhandler;

        public FilePanel() {
            this.setLayout(new GridBagLayout());
            this.filepanelhandler = new FilePanelHandler();

            GridBagConstraints c = new GridBagConstraints();
            c.weightx=1;
            c.weighty=1;
            c.fill= GridBagConstraints.BOTH;

            JRadioButton fastaButton = new JRadioButton("Fasta file");
            fastaButton.setActionCommand("fasta");
            fastaButton.setSelected(true);
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=0;
            this.add(fastaButton, c);

            JRadioButton groupButton = new JRadioButton("Orthomcl file");
            groupButton.setActionCommand("orthomcl");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=1;
            c.gridy=0;
            this.add(groupButton, c);

            JRadioButton wolfButton = new JRadioButton("WoLF PSORT file");
            wolfButton.setActionCommand("wolfpsort");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=2;
            c.gridy=0;
            this.add(wolfButton, c);

            JRadioButton signalButton = new JRadioButton("SignalP file");
            signalButton.setActionCommand("signalp");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=1;
            this.add(signalButton, c);

            JRadioButton hmmerButton = new JRadioButton("HMMER file");
            hmmerButton.setActionCommand("hmmer");
            c.gridwidth=1;
            c.gridheight=1;
            c.gridx=1;
            c.gridy=1;
            this.add(hmmerButton, c);

            JButton openButton = new JButton("Open file");
            openButton.addActionListener(filepanelhandler);
            openButton.setActionCommand("open");
            c.gridwidth=3;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=2;
            this.add(openButton, c);

            JButton csvButton = new JButton("Generate CSV");
            csvButton.addActionListener(filepanelhandler);
            csvButton.setActionCommand("csv");
            c.gridwidth=3;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=3;
            this.add(csvButton, c);

            group = new ButtonGroup();
            group.add(fastaButton);
            group.add(groupButton);
            group.add(wolfButton);
            group.add(signalButton);
            group.add(hmmerButton);
        }

        public String getSelectedRadioButtonActionCommand(){
            return group.getSelection().getActionCommand();
        }
    }

    class FilePanelHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (Objects.equals(actionEvent.getActionCommand(), "open")) {
                String ac = getFilepanel().getSelectedRadioButtonActionCommand();
                if (Objects.equals(ac, "fasta")) {
                    // todo open fasta file and parse it
                    new FastaFile(otDatabase);
                }
                if (Objects.equals(ac, "orthomcl")) {
                    // todo open orthomcl file and parse it
                    new OrthologFile(otDatabase);
                }
                if (Objects.equals(ac, "wolfpsort")) {
                    // todo open wolfpsort file and parse it
                }
                if (Objects.equals(ac, "signalp")) {
                    new SignalP_Out(otDatabase);
                }
                if (Objects.equals(ac, "hmmer")) {
                    new Hmmer_Out(otDatabase);
                    // todo open hmmer file and parse it
                }
            }
            if (Objects.equals(actionEvent.getActionCommand(), "csv")) {
                new CSV(otDatabase);
            }
        }
    }
}