import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Collections;

/**
 * Klasse voor handlen van de database connectie en invoer/uitvoer
 */
public class OTDatabase extends JFrame implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        connect();
    }

    enum DATA_TYPES {
        DATA_TYPE_INT,
        DATA_TYPE_STRING
    }

    Connection databaseConnection;

    JTextField host = new JTextField();
    JTextField db = new JTextField();
    JTextField user = new JTextField();
    JTextField pass = new JTextField();
    JButton cbutton = new JButton("Connect");

    JFrame mainWindow;

    public OTDatabase(JFrame mainWindow) {
        this.mainWindow = mainWindow;

        this.setVisible(true);
        this.setSize(300, 200);
        this.setLayout(new GridLayout(5, 2));

        this.add(new JLabel("host:port"));
        this.add(host);
        this.add(new JLabel("database"));
        this.add(db);
        this.add(new JLabel("Username"));
        this.add(user);
        this.add(new JLabel("Password"));
        this.add(pass);
        this.add(cbutton);
        cbutton.addActionListener(this);

        this.pack();

        this.setLocationRelativeTo(null);

        this.setTitle("SQL details");

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void connect() {
        try {
            databaseConnection = DriverManager.getConnection(
                    "jdbc:mysql://"+host.getText()+"/"+db.getText()+"?useServerPrepStmts=false&rewriteBatchedStatements=true",
                    user.getText(),
                    pass.getText()
            );
            mainWindow.setVisible(true);
            this.setVisible(false);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDatabaseConnection () {
        return databaseConnection;
    }

    // Voer een ruwe query uit op database en verkrijg resultaten
    public ResultSet ExecuteQuery(String q) {
        ResultSet r = null;
        try {
            Statement s = databaseConnection.createStatement();
            s.setFetchSize(50);
            r = s.executeQuery(q);
        }catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return r;
    }

    public boolean insertIntoDatabase(String table, String[][] data) {
        return insertIntoDatabase(table, data, null, false);
    }

    public boolean insertIntoDatabase(String table, String[][] data, DATA_TYPES[] types) {
        return insertIntoDatabase(table, data, types, false);
    }

    // voer een set aan gegevens [data] in in de tabel [table]
    public boolean insertIntoDatabase(String table, String[][] data, DATA_TYPES[] types, boolean verbose) {
        ResultSet r = null;
        String[] eset = {};
        try {
            databaseConnection.setAutoCommit(false);
            StringBuilder values = new StringBuilder();
            for (int i = 0; i < data[0].length; i++) {
                values.append("?");
                values.append(",");
            }
            values.deleteCharAt(values.length()-1);
            PreparedStatement s = databaseConnection.prepareStatement(
                    String.format("INSERT INTO %s VALUES (%s)", table, values)
            );
            for (String[] set : data) {
                eset = set;
                s.clearParameters();
                for (int i = 0; i < set.length; i++) {
                    if (types == null) {
                        s.setString(i+1, set[i]);
                    }else {
                        if (types[i] == DATA_TYPES.DATA_TYPE_INT) {
                            s.setInt(i + 1, Integer.parseInt(set[i]));
                        } else {
                            s.setString(i+1, set[i]);
                        }
                    }
                }
                if (verbose) {
                    System.out.println(s.toString());
                }
                s.addBatch();
            }
            System.out.println("Inserting "+s.getUpdateCount()+" rows");
            s.clearParameters();
            s.executeBatch();
            databaseConnection.commit();
            System.out.println("Done");

        }catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            if (verbose) {
                for (int i = 0; i < eset.length; i++) {
                    System.out.println(i + ": "+eset[i]);
                }
            }
        }
        return true;
    }
}
