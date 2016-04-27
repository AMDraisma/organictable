import java.sql.*;

/**
 * Klasse voor handlen van de database connectie en invoer/uitvoer
 */
public class OTDatabase {

    Connection databaseConnection;

//    public OTDatabase() {
//        try {
//            databaseConnection = DriverManager.getConnection(
//                    "jdbc:mysql://croil.net:3306/novelenzymes",
//                    "dnaj",
//                    ""
//            );
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public Connection getDatabaseConnection () {
        return databaseConnection;
    }

    // Voer een ruwe query uit op database en verkrijg resultaten
    public ResultSet ExecuteQuery(String q) {
        ResultSet r = null;
        try {
            Statement s = databaseConnection.createStatement();
            s.execute(q);
            r = s.getResultSet();
        }catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return r;
    }

    // voer een set aan gegevens [data] in in de tabel [table]
    public boolean insertIntoDatabase(String table, String[][] data) {
        ResultSet r = null;
        try {
            Statement s = databaseConnection.createStatement();
            String q;
            StringBuilder values = new StringBuilder();
            for (String[] set : data) {
                for (String value : set) {
                    values.append(value);
                    values.append(",");
                }
                values.deleteCharAt(values.length()-1);
                q = String.format("INSERT INTO %s VALUES (%s);", table, values.toString());
                s.addBatch(q);
            }
            System.out.println(s.toString());
        }catch (SQLException e) {
            // handle any errors
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return true;
    }
}
