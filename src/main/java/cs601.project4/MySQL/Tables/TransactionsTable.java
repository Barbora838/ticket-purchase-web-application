package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionsTable {

    private static String tableName = "transactions";

    /**
     * Query that creates transactions table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createTransactionTable(Connection con) throws SQLException {
        String query = "    CREATE TABLE " + tableName + " (\n" +
                "    email VARCHAR(256) NOT NULL,\n" +
                "    num_tickets INTEGER,\n" +
                "    event_id INTEGER NOT NULL\n" +
                "    );";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }


    /**
     * Query that returns elements from database based on email.
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    public static String getTransactions(Connection con, String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<h2>Passed events that you have Tickets for:</h2>\n");
        sb2.append("<hr></hr><hr></hr>\n");
        sb.append("<h2>Upcoming events that you have Tickets for:</h2>\n");
        sb.append("<hr></hr><hr></hr><br/>\n");
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE email LIKE '%" + email + "%' ORDER BY DATE(date) DESC;";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        int counter = 1;
        while (results.next()) {
            String numTickets = results.getString("num_tickets");
            String eventID = results.getString("event_id");
            String selectAllContactsSql2 = "SELECT * FROM events WHERE event_id='" + eventID + "';";
            PreparedStatement selectAllContactsStmt2 = con.prepareStatement(selectAllContactsSql2);
            ResultSet results2 = selectAllContactsStmt2.executeQuery();


            while (results2.next()) {

                String dateEvent = results2.getString("date");
                if (validateDate(dateEvent)) {
                    sb.append("<h4><i>Transaction from " + results.getString("date") + "</i></h4>");
                    sb.append("<h3> " +numTickets + " tickets for " + results2.getString("name") + ", on " + results2.getString("date") + ", at " + results2.getString("location") + "</h3>\n");
                    sb.append("<hr></hr>");
                    counter = counter + 1;
                } else {
                    sb2.append("<h3>" + numTickets + " tickets for " + results2.getString("name") + ", on " + results2.getString("date") + ", at " + results2.getString("location") + "</h3>\n");

                }
            }

        }
        sb.append("<br/>\n");

        if (counter == 1) {
            sb.append("No purchased tickets!\n");
        }
        return sb.toString() + "<br/>" + sb2.toString() + "<br/><br/><br/>";
    }

    public static boolean validateDate(String dateEvent) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String dateToday = sdf.format(new Date());
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-M-dd");
            Date date1 = new SimpleDateFormat("yyyy-M-dd").parse(dateEvent);
            String dateEvent2 = sdf2.format(date1);
            if (dateToday.compareTo(dateEvent2) > 0) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Query that returns elements from database based on email.
     *
     * @param con
     * @param email
     * @return
     * @throws SQLException
     */
    public static String getTransactionsForTransfer(Connection con, String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<h2>Missed events that you have Tickets for:</h2>\n");
        sb.append("<br/><h2>Upcoming events that you have Tickets for:</h2>\n");
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE email LIKE '%" + email + "%' ORDER BY num_tickets DESC;";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        int counter = 1;
        while (results.next()) {
            String numTickets = results.getString("num_tickets");
            String eventID = results.getString("event_id");
            String selectAllContactsSql2 = "SELECT * FROM events WHERE event_id='" + eventID + "';";
            PreparedStatement selectAllContactsStmt2 = con.prepareStatement(selectAllContactsSql2);
            ResultSet results2 = selectAllContactsStmt2.executeQuery();
            while (results2.next()) {

                if (validateDate(results2.getString("date"))) {
                    sb.append("<hr>");
                    sb.append("<h3>" + counter + ". You have " + numTickets + " tickets for " + results2.getString("name") + ", on " + results2.getString("date") + ", at " + results2.getString("location") + "</h3>\n");
                    sb.append("<form action=\"transfer\" method=\"post\">\n");
                    sb.append("<label for=\"user\">User's email:</label>\n<input type=\"text\" id=\"user\" name=\"user\"/><br/>\n" +
                            "<label for=\"tickets\">How many tickets?</label>\n<input type=\"text\" id=\"tickets\" name=\"tickets\"/><br/>\n" +
                            "<input type=\"submit\" value=\"TransferTicket\"></input>\n" +
                            "<input type=\"hidden\" value=\"" + eventID + "\" name=\"event_id\" id=\"id\"></input>\n");
                    sb.append("</form>\n");
                    sb.append("</hr>");
                    counter = counter + 1;
                } else {
                    sb2.append("<h3>" + numTickets + " tickets for " + results2.getString("name") + ", on " + results2.getString("date") + ", at " + results2.getString("location") + "</h3>\n");
                }
            }

        }
        sb.append("<br/>\n");

        if (counter == 1) {
            sb.append("No purchased tickets!\n");
        }
        return sb.toString() + "<br/><br/><hr></hr>" + sb2.toString() + "<br/><br/><br/>";
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @return
     */
    public static String returnTransactions(String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getTransactions(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @return
     */
    public static void insertNewTransaction(String email, int tickets, int eventID) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            insertNewTransaction(connection, email, tickets, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * This method inserts new transactions into the transactions table.
     *
     * @param con
     * @param email
     * @param tickets
     * @param eventID
     * @throws SQLException
     */
    public static void insertNewTransaction(Connection con, String email, int tickets, int eventID) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (email, num_tickets, event_id, date) VALUES (?, ?, ?, ?);";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String date = sdf.format(new Date());
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.setInt(2, tickets);
        insertContactStmt.setInt(3, eventID);
        insertContactStmt.setString(4, date);
        insertContactStmt.executeUpdate();
    }
}
