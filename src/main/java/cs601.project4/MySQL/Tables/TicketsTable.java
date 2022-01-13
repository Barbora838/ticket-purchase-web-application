package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TransactionsTable Class - methods that operates on transactions table in mySQL.
 */
public class TicketsTable {

    private static String tableName = "tickets";

    /**
     * Query that creates transactions table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createTicketTable(Connection con) throws SQLException {
        String query = "CREATE TABLE " + tableName + " (email VARCHAR(256) NOT NULL, name VARCHAR(256) NOT NULL, date VARCHAR(256), location VARCHAR(256), price INTEGER, event_id INTEGER NOT NULL, PRIMARY KEY (email));";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }

    /**
     * Query that inserts element into the table.
     *
     * @param con
     * @param email
     * @param name
     * @param date
     * @param location
     * @param price
     * @param eventID
     * @throws SQLException
     */
    public static void executeTicketTable(Connection con, String email, String name, String date, String location, int price, int eventID) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (email, name, date, location, price, event_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.setString(2, name);
        insertContactStmt.setString(3, date);
        insertContactStmt.setString(4, location);
        insertContactStmt.setInt(5, price);
        insertContactStmt.setInt(6, eventID);
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
    public static String getTickets(Connection con, String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<h2>Passed events that you have Tickets for:</h2>\n");
        sb2.append("<hr></hr>\n");
        sb.append("<h2>Upcoming events that you have Tickets for:</h2>\n");
        sb.append("<hr></hr><br/>\n");
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

                String dateEvent = results2.getString("date");
                if (validateDate(dateEvent)) {
                    sb.append("<h3>" + counter + ". You have " + numTickets + " tickets for " + results2.getString("name") + ", on " + results2.getString("date") + ", at " + results2.getString("location") + "</h3>\n");
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
    public static String getTicketsForTransfer(Connection con, String email) throws SQLException {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<h2>Missed events that you have Tickets for:</h2>\n");
        sb.append("<br/><h2>Upcoming events that you have Tickets for:</h2>\n<hr></hr>");
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
    public static String returnTickets(String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getTickets(connection, email);
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
    public static String returnTicketsTransfer(String email) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getTicketsForTransfer(connection, email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }


    /**
     * Inserts new transaction after purchasing tickets.
     *
     * @param con
     * @param email
     * @param tickets
     * @param eventID
     * @throws SQLException
     */
    public static void updateTickets(Connection con, String email, int tickets, int eventID) throws SQLException {

        // user does not own any tickets for this event then insert
        if (!checkTicketsAndUpdate(con, email, tickets, eventID)) {

            String insertContactSql = "INSERT INTO " + tableName + " (email, num_tickets, event_id) VALUES (?, ?, ?);";
            PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);

            insertContactStmt.setString(1, email);
            insertContactStmt.setInt(2, tickets);
            insertContactStmt.setInt(3, eventID);
            insertContactStmt.executeUpdate();
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
    public static void insertNewTicket(Connection con, String email, int tickets, int eventID) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (email, num_tickets, event_id) VALUES (?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);
        insertContactStmt.setString(1, email);
        insertContactStmt.setInt(2, tickets);
        insertContactStmt.setInt(3, eventID);
        insertContactStmt.executeUpdate();
    }

    /**
     * This method updates owner of the tickets.
     *
     * @param con
     * @param email
     * @param eventID
     * @throws SQLException
     */
    public static void updateTicketsOwner(Connection con, String email, int eventID) throws SQLException {
        String updateContactSql = "UPDATE " + tableName + " SET email='" + email + "' WHERE event_id=" + eventID + ";";
        PreparedStatement insertContactStmt = con.prepareStatement(updateContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * This method updates number of own tickets.
     *
     * @param con
     * @param email
     * @param ticketNum
     * @param eventID
     * @throws SQLException
     */
    public static void modifyTicketsNumber(Connection con, String email, int ticketNum, int eventID) throws SQLException {
        String updateContactSql = "UPDATE " + tableName + " SET num_tickets='" + ticketNum + "' WHERE email='" + email + "' AND event_id=" + eventID + ";";
        PreparedStatement insertContactStmt = con.prepareStatement(updateContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * This tickets returns number of ticket hat particular user has for specific event.
     *
     * @param con
     * @param email
     * @param eventID
     * @return
     * @throws SQLException
     */
    public static int getNumTickets(Connection con, String email, int eventID) throws SQLException {
        int ticketsDB = 0;
        String select = "SELECT * FROM " + tableName + " WHERE event_id=" + eventID + " AND email='" + email + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(select);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            ticketsDB = results.getInt("num_tickets");
        }
        return ticketsDB;
    }

    /**
     * This method check if theuser already has some tickets and if does then updates the number and return if it was successfull and false otherwise.
     *
     * @param con
     * @param email
     * @param tickets
     * @param eventID
     * @return
     * @throws SQLException
     */
    public static boolean checkTicketsAndUpdate(Connection con, String email, int tickets, int eventID) throws SQLException {
        String select = "SELECT num_tickets FROM " + tableName + " WHERE event_id=" + eventID + " AND email='" + email + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(select);
        ResultSet results = selectAllContactsStmt.executeQuery();
        while (results.next()) {
            int ticketsDB = getNumTickets(con, email, eventID);
            String updateQuery = "UPDATE " + tableName + " SET num_tickets=" + (ticketsDB + tickets) + " WHERE event_id=" + eventID + " AND email='" + email + "';";
            PreparedStatement insertContactStmt = con.prepareStatement(updateQuery);
            insertContactStmt.executeUpdate();
            System.out.println(ticketsDB + " Tickets found! and UPDATED TO " + (ticketsDB + tickets) + " TICKETS");
            return true;
        }
        System.out.println("Ticket not found!");
        return false;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @param tickets
     * @param eventID
     */
    public static void updateTicketDB(String email, int tickets, int eventID) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            updateTickets(connection, email, tickets, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @param eventID
     */
    public static void updateOwnerTicketTable(String email, int eventID) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            updateTicketsOwner(connection, email, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @param numTickets
     * @param eventID
     */
    public static void modifyTickets(String email, int numTickets, int eventID) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            modifyTicketsNumber(connection, email, numTickets, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     */
    public static void deleteTicket() {
        try (Connection connection = DBCPDataSource.getConnection()) {
            deleteTicket(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param con
     * @throws SQLException
     */
    public static void deleteTicket(Connection con) throws SQLException {
        String updateContactSql = "DELETE FROM "+tableName+" WHERE num_tickets=0;";
        System.out.println("Tickets Deleted!!!");
        PreparedStatement insertContactStmt = con.prepareStatement(updateContactSql);
        insertContactStmt.executeUpdate();
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @param numTickets
     * @param eventID
     */
    public static void insertNewOwner(String email, int numTickets, int eventID) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            insertNewTicket(connection, email, numTickets, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param email
     * @param eventID
     * @return
     */
    public static int getNumTicketsOwned(String email, int eventID) {
        int ret = 0;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getNumTickets(connection, email, eventID);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }
}
