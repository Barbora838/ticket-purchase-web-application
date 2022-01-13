package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SessionsTable Class - methods that operates on sessions table in mySQL.
 */
public class SessionsTable {

    private static String tableName = "sessions";

    /**
     * Query that creates sessions table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createSessionsTable(Connection con) throws SQLException {
        String query = "CREATE TABLE " + tableName + " (name VARCHAR(256) NOT NULL, email VARCHAR(256) NOT NULL, session VARCHAR(256) NOT NULL, cookie VARCHAR(256) NOT NULL, PRIMARY KEY (email));";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }

    /**
     * Insert new username, email, session and cookie into session table.
     *
     * @param con
     * @param name
     * @param email
     * @param session
     * @param cookie
     * @throws SQLException
     */
    public static void executeSessionsTable(Connection con, String name, String email, String session, String cookie) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (name, email, session, cookie) VALUES (?, ?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);

        insertContactStmt.setString(1, name);
        insertContactStmt.setString(2, email);
        insertContactStmt.setString(3, session);
        insertContactStmt.setString(4, cookie);
        insertContactStmt.executeUpdate();
    }

    public static void executeSessionsTable2(Connection con, String email, String session, String cookie) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (email, session, cookie) VALUES (?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);

        insertContactStmt.setString(1, email);
        insertContactStmt.setString(2, session);
        insertContactStmt.setString(3, cookie);
        insertContactStmt.executeUpdate();
    }

    /**
     * Updates Session in the table.
     *
     * @param con
     * @param session
     * @param cookie
     * @param email
     * @throws SQLException
     */
    public static void executeUpdateSessionsTable(Connection con, String session, String cookie, String email) throws SQLException {
        String insertContactSql = "UPDATE " + tableName + " SET session='" + session + "', cookie='" + cookie + "' WHERE email='" + email + "';";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * method that check if the cookies is present in the table and return true, and false otherwise.
     */
    public static boolean executeValidation(Connection con, String name, String email) throws SQLException {
        String query = "SELECT name FROM sessions WHERE email='" + email + "';";

        PreparedStatement selectAllContactsStmt = con.prepareStatement(query);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            String nameDB = results.getString("name");

            if (name.equals(nameDB)) {
                return true;
            }
        }
        System.out.println("Cookie NOT found");
        return false;
    }

    public static boolean executeValidation2(Connection con, String email) throws SQLException {
        String query = "SELECT name FROM sessions WHERE email='" + email + "';";

        PreparedStatement selectAllContactsStmt = con.prepareStatement(query);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            return true;
        }
        System.out.println("Cookie NOT found");
        return false;
    }

    /**
     * Method that check if the session id is present in the database, and check if the value of cookies is same as a passed
     * cookie and return true is they are same, false otherwise.
     *
     * @param con
     * @param sesssion
     * @param cookie
     * @return
     * @throws SQLException
     */
    public static boolean executeValidationSession(Connection con, String sesssion, String cookie) throws SQLException {
        String query = "SELECT cookie FROM sessions WHERE session='" + sesssion + "';";

        PreparedStatement selectAllContactsStmt = con.prepareStatement(query);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            String cookieDB = results.getString("cookie");

            if (cookie.equals(cookieDB)) {
                return true;
            }
        }
        System.out.println("Cookie NOT found");
        return false;
    }

    /**
     * Method that connect to the database using a PreparedStatement
     *
     * @param name
     * @param email
     * @return
     */
    public static boolean validateEmailDB(String name, String email) {
        boolean ret = false;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = executeValidation(connection, name, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static boolean validateEmailDB2(String email) {
        boolean ret = false;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = executeValidation2(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement
     *
     * @param session
     * @param cookie
     * @return
     */
    public static boolean validateCookieDB(String session, String cookie) {
        boolean ret = false;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = executeValidationSession(connection, session, cookie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }


    /**
     * Method that connect to the database using a PreparedStatement
     *
     * @param name
     * @param email
     * @param session
     * @param cookie
     * @return
     */
    public static String insertsSessionDB(String name, String email, String session, String cookie) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            executeSessionsTable(connection, name, email, session, cookie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connects to DB.
     *
     * @param email
     * @param session
     * @param cookie
     * @return
     */
    public static String insertsSessionDB2(String email, String session, String cookie) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            executeSessionsTable2(connection, email, session, cookie);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement
     *
     * @param session
     * @param cookie
     * @param email
     * @return
     */
    public static String updateSessionDB(String session, String cookie, String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            executeUpdateSessionsTable(connection, session, cookie, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static String getFirstName(Connection con, String sesssion) throws SQLException {
        String query = "SELECT email FROM sessions WHERE session='" + sesssion + "';";

        PreparedStatement selectAllContactsStmt = con.prepareStatement(query);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            String email = results.getString("email");

            String query2 = "SELECT first_name FROM users WHERE email='" + email + "';";

            PreparedStatement selectAllContactsStmt2 = con.prepareStatement(query2);
            ResultSet results2 = selectAllContactsStmt2.executeQuery();
            while (results2.next()) {
                return results2.getString("first_name");
            }
        }
        System.out.println("NOT found");
        return "";
    }

    public static String getEmail(Connection con, String sesssion) throws SQLException {
        String query = "SELECT email FROM sessions WHERE session='" + sesssion + "';";

        PreparedStatement selectAllContactsStmt = con.prepareStatement(query);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            return results.getString("email");
        }
        System.out.println("NOT found");
        return "";
    }

    /**
     * Method that connects to DB.
     *
     * @param session
     * @return
     */
    public static String getNameDB(String session) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getFirstName(connection, session);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connects to DB.
     *
     * @param session
     * @return
     */
    public static String getEmailDB(String session) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getEmail(connection, session);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }
}
