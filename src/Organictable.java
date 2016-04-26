import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Organictable extends JFrame {

    private FilePanel filepanel;
    private ButtonGroup group;
    private Connection databaseConnection;

    public static void main(String[] args) {
        new Organictable();
    }

    public Organictable() {
        try {
            databaseConnection = DriverManager.getConnection("jdbc:mysql://croil.net?user=");
        }catch (SQLException e) {

        }
        this.setSize(600,300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        filepanel = new FilePanel();
        this.setContentPane(filepanel);
        this.pack();
    }

    public FilePanel getFilepanel() {
        return filepanel;
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
            c.gridwidth=3;
            c.gridheight=1;
            c.gridx=0;
            c.gridy=2;
            this.add(openButton, c);

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
            String ac = getFilepanel().getSelectedRadioButtonActionCommand();
            if (Objects.equals(ac, "fasta")) {
                // todo open fasta file and parse it
            }if (Objects.equals(ac, "orthomcl")) {
                // todo open orthomcl file and parse it
                new OrthologFile();
            }if (Objects.equals(ac, "wolfpsort")) {
                // todo open wolfpsort file and parse it
            }if (Objects.equals(ac, "signalp")) {
                // todo open signalp file and parse it
            }if (Objects.equals(ac, "hmmer")) {
                // todo open hmmer file and parse it
            }
        }
    }
}