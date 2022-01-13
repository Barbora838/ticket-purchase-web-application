package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PasswordsTable Class - methods that operates on passowrds table in mySQL.
 */
public class PasswordsTable {

    private static String tableName = "passwords";

    /**
     * Query that creates events table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createEventTable(Connection con) throws SQLException {
        String query = "CREATE TABLE " + tableName + " (email VARCHAR(256) NOT NULL, pass VARCHAR(256) NOT NULL, PRIMARY KEY (email));";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }

    /**
     * Method that verify the credentials and return true if they match and false otherwise.
     *
     * @param con
     * @param email
     * @param pass
     * @return
     * @throws SQLException
     */
    public static boolean verifyCredencials(Connection con, String email, String pass) throws SQLException {
        String select = "SELECT pass FROM " + tableName + " WHERE email='" + email + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(select);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            String password = results.getString("pass");
            if (password.equals(pass)) {
                return true;
            }
        }
        System.out.println("Password doesn't match");
        return false;
    }

    /**
     * method that connects to dB.
     *
     * @param email
     * @param pass
     * @return
     */
    public static boolean verifyDB(String email, String pass) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            return verifyCredencials(connection, email, pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
