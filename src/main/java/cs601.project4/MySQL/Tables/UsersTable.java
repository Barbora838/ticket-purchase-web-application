package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UsersTable Class - methods that operates on users table in mySQL.
 */
public class UsersTable {

    private static String tableName = "users";

    /**
     * Query that creates users table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createTable(Connection con) throws SQLException {
        String query = "CREATE TABLE " + tableName + " (first_name VARCHAR(256) NOT NULL, last_name VARCHAR(256) NOT NULL, email VARCHAR(256) NOT NULL, contact VARCHAR(256), country VARCHAR(256), zip INTEGER, PRIMARY KEY (email));";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }

    /**
     * A method to demonstrate using a PreparedStatement to execute a database insert.
     */
    public static void executeInsert(Connection con, String firstName, String lastName, String email, String contact, String country, int zip) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (first_name, last_name, email, contact, country, zip) VALUES (?, ?, ?, ?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, firstName);
        insertContactStmt.setString(2, lastName);
        insertContactStmt.setString(3, email);
        insertContactStmt.setString(4, contact);
        insertContactStmt.setString(5, country);
        insertContactStmt.setInt(6, zip);
        insertContactStmt.executeUpdate();
    }

    /**
     * Query that updates element based on email.
     *
     * @param con
     * @param body
     * @param email
     * @throws SQLException
     */
    public static void updateElements(Connection con, String body, String email) throws SQLException {
        String updateContactSql = "UPDATE " + tableName + " SET " +
                body +
                " WHERE email LIKE '%" + email + "%';";
        PreparedStatement insertContactStmt = con.prepareStatement(updateContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param body
     * @param email
     */
    public static void updateElements(String body, String email) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            updateElements(connection, body, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Query that deletes element from table.
     *
     * @param con
     * @param first
     * @param second
     * @throws SQLException
     */
    public static void executeDelete(Connection con, String first, String second) throws SQLException {
        String insertContactSql = "DELETE FROM " + tableName +
                " WHERE " + first + " = " + second + ";";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * Query that deletes particular table.
     *
     * @param con
     * @throws SQLException
     */
    public static void executeDrop(Connection con) throws SQLException {
        String insertContactSql = "DROP TABLE " + tableName + ";";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * A method to demonstrate using a PrepareStatement to execute a database select
     *
     * @param con
     * @throws SQLException
     */
    public static void executeSelectAndPrint(Connection con) throws SQLException {
        String selectAllContactsSql = "SELECT * FROM " + tableName + ";";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            System.out.printf("first_name: %s\n", results.getString("first_name"));
            System.out.printf("last_name: %s\n", results.getString("last_name"));
            System.out.printf("email: %s\n", results.getString("email"));
            System.out.printf("contact: %s\n", results.getString("contact"));
            System.out.printf("country: %s\n", results.getString("country"));
            System.out.printf("zip: %s\n", results.getString("zip"));
        }
    }

    /**
     * Method that returns HTML formatted users information.
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    public static String returnAccount(Connection con, String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String selectAllContactsSql = "SELECT * FROM users WHERE email LIKE '%" + email + "%';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        sb.append("<hr></hr>");
        while (results.next()) {
            sb.append("<h2>First Name: " + results.getString("first_name") + "</h2>" + "\n");
            sb.append("<h2>Last Name: " + results.getString("last_name") + "</h2>" + "\n");
            sb.append("<h2>Email: " + results.getString("email") + "</h2>" + "\n");
            sb.append("<h2>Contact: " + results.getString("contact") + "</h2>" + "\n");
            sb.append("<h2>Country: " + results.getString("country") + "</h2>" + "\n");
            sb.append("<h2>Zip: " + results.getInt("zip") + "</h2>" + "\n");
        }
        sb.append("<hr></hr>");
        return sb.toString();
    }


    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @return
     */
    public static String returnAccount(String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = returnAccount(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * This method check if the user exists in the table and return true if he does and false otherwise.
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    public static boolean isUserInTable(Connection con, String email) throws SQLException {

        String selectAllContactsSql = "SELECT * FROM users WHERE email LIKE '%" + email + "%';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            return true;
        }
        return false;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @return
     */
    public static boolean isUserInTable(String email) {
        boolean ret = false;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = isUserInTable(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return ret;
        }
        return ret;
    }

    /**
     * This method inserts new email into users table.
     *
     * @param con
     * @param email
     * @throws SQLException
     */
    public static void insertEmailtoDB(Connection con, String email) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (email) VALUES (?);";
        System.out.println(insertContactSql);
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.executeUpdate();
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     */
    public static void insertEmail(String email) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            insertEmailtoDB(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @return
     */
    public static String returnName(String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = returnName(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * This method returns first_name of the user based on email.
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    public static String returnName(Connection con, String email) throws SQLException {
        String name = "";
        String selectAllContactsSql = "SELECT * FROM users WHERE email LIKE '%" + email + "%';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            name = results.getString("first_name");
        }
        return name;
    }
}
