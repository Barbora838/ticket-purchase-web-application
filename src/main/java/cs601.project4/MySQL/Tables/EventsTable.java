package cs601.project4.MySQL.Tables;

import cs601.project4.MySQL.DBCPDataSource;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static cs601.project4.Util.HTMLExtra.addingPicture;

/**
 * EventsTable Class - methods that operates on events table in mySQL.
 */
public class EventsTable {

    private static String tableName = "events";

    /**
     * Query that creates events table.
     *
     * @param con
     * @throws SQLException
     */
    public static void createEventTable(Connection con) throws SQLException {
        String query = "CREATE TABLE " + tableName + " (name VARCHAR(256) NOT NULL, date VARCHAR(256), location VARCHAR(256), price INTEGER, event_id INTEGER NOT NULL);";
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        insertContactStmt.executeUpdate();
    }

    /**
     * Query that inserts into events table.
     *
     * @param con
     * @param name
     * @param date
     * @param location
     * @param price
     * @param eventID
     * @throws SQLException
     */
    public static void executeEventTable(Connection con, String name, String date, String location, int price, int eventID) throws SQLException {
        String insertContactSql = "INSERT INTO " + tableName + " (name, date, location, price, event_id) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement insertContactStmt = con.prepareStatement(insertContactSql);

        insertContactStmt.setString(1, name);
        insertContactStmt.setString(2, date);
        insertContactStmt.setString(3, location);
        insertContactStmt.setInt(4, price);
        insertContactStmt.setInt(5, eventID);
        insertContactStmt.executeUpdate();
    }

    /**
     * Method that returns event info baased on event id.
     *
     * @param con
     * @param id
     * @return
     * @throws SQLException
     */
    public static String getEventDetailsHTML(Connection con, int id) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE event_id=" + id + ";";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();

        while (results.next()) {
            sb.append("<hr></hr>");
            sb.append(addingPicture(results.getString("picture"), 200, 130));
            sb.append("<h2>Details about " + results.getString("name") + " </h2>" + "<br/>\n");
            sb.append("<h3><i>" + results.getString("details") + "</i></h3>\n");
        }
        sb.append("<br/>");
        return sb.toString();
    }

    /**
     * Method that returns HTML formatted Event information
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static String getEventsHTML(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String date = sdf.format(new Date());
        System.out.println("TODAY IS: " + date);
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE DATE(date) > '" + date + "' ORDER BY DATE(date) ASC;";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        int counter = 1;
        sb.append("\n");

        while (results.next()) {
            sb.append("<div>\n");
            sb.append("<hr>");
            sb.append("<h3>" + counter + ". " + results.getString("name") + "<form action=\"events\" method=\"post\"><a href=\"https://bc88-2601-642-4c05-2a04-f516-4e00-ac3c-bab.ngrok.io/viewEvent\">\n" +
                    "<input type=\"submit\" value=\"Details\"></input>\n" +
                    "<input type=\"hidden\" value=\"" + results.getString("event_id") + "\" name=\"id\" id=\"id\"></input>\n" +
                    "</a>\n" + "</form>\n" + "</h3>\n");
            sb.append("<h4>Type: " + results.getString("type") + ", Date: " + results.getString("date") + ", Location: " + results.getString("location") + ", Price: " + results.getInt("price") + " $, <i>Tickets sold: " + results.getInt("sold_tickets") + " Tickets left: " + results.getInt("num_tickets") + "</i></h4>\n");

            sb.append("<form action=\"tickets\" method=\"post\">\n");
            sb.append("<label for=\"number\">How many tickets?</label>\n" +
                    "<input type=\"text\" id=\"number\" name=\"number\"/>\n" +
                    "<input type=\"submit\" value=\"BuyTickets\"></input>\n" +
                    "<input type=\"hidden\" value=\"" + results.getString("event_id") + "\" name=\"event_id\" id=\"id\"></input>\n");
            sb.append("</form>\n");
            sb.append("</hr>");
            sb.append("</div>\n");

            counter = counter + 1;
        }
        //ADDED PAST EVENTS
        sb.append(getPastEvents());
        sb.append("<br/><br/><br/>\n");
        return sb.toString();
    }


    /**
     * This method filters Events based ond today's date.
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static String getPastEvents(Connection con) throws SQLException {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String date = sdf.format(new Date());
        String selectAllContactsSql = "SELECT * FROM events WHERE DATE(date) < '" + date + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();

        sb.append("<br/><br/><br/>\n");
        sb.append("<hr></hr><hr></hr>");
        sb.append("<h1>PAST Events: </h1>\n<br/>");
        int counter = 1;
        while (results.next()) {
            sb.append("<h2>" + results.getString("name") + "</h2>\n");
            sb.append("<h4>Type: " + results.getString("type") + ", Date: " + results.getString("date") + ", Location: " + results.getString("location") + ", Price: " + results.getInt("price") + " $, <i>Tickets sold: " + results.getInt("sold_tickets") + " Tickets left: " + results.getInt("num_tickets") + "</i></h4>\n");
            sb.append("<hr></hr>");
            counter = counter + 1;
        }
        return sb.toString();
    }


    /**
     * Method that returns HTML formatted Event information
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static String getEventSearchHTML(Connection con, String query, String searchType) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String selectAllContactsSql;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        String date = sdf.format(new Date());
        System.out.println("TODAY IS: " + date);
        if (searchType.equals("type")) {
            selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE type LIKE '%" + query + "%' AND DATE(date) > '" + date + "';";
        } else if (searchType.equals("name")) {
            selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE name LIKE '%" + query + "%' AND DATE(date) > '" + date + "';";
        } else {
            selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE location LIKE '%" + query + "%' AND DATE(date) > '" + date + "';";
        }
        System.out.println(selectAllContactsSql);
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();
        int counter = 1;

        while (results.next()) {
            if (counter == 1) {
                sb.append("<h2><i>Results for Search of type " + query + "</i></h2><br/>\n");
            }
            sb.append("<div>\n");
            sb.append("<hr>");
            sb.append("<h3>" + counter + ". " + results.getString("name") + "<form action=\"events\" method=\"post\"><a href=\"https://bc88-2601-642-4c05-2a04-f516-4e00-ac3c-bab.ngrok.io/viewEvent\">\n" +
                    "<input type=\"submit\" value=\"Details\"></input>\n" +
                    "<input type=\"hidden\" value=\"" + results.getString("event_id") + "\" name=\"id\" id=\"id\"></input>\n" +
                    "</a>\n" + "</form>\n" + "</h3>\n");
            sb.append("<h4>Type: " + results.getString("type") + ", Date: " + results.getString("date") + ", Location: " + results.getString("location") + ", Price: " + results.getInt("price") + " $, <i>Tickets sold: " + results.getInt("sold_tickets") + " Tickets left: " + results.getInt("num_tickets") + "</i></h4>\n");

            sb.append("<form action=\"tickets\" method=\"post\">\n");
            sb.append("<label for=\"number\">How many tickets?</label>\n" +
                    "<input type=\"text\" id=\"number\" name=\"number\"/>\n" +
                    "<input type=\"submit\" value=\"BuyTickets\"></input>\n" +
                    "<input type=\"hidden\" value=\"" + results.getString("event_id") + "\" name=\"event_id\" id=\"id\"></input>\n");
            sb.append("</form>\n");
            sb.append("</hr>");
            sb.append("</div>\n");
            counter = counter + 1;
        }

        if (counter == 1) {
            sb.append("null");
        } else {
            sb.append("<br/>\n");
        }
        return sb.toString();
    }

    /**
     * Method that returns HTML formatted Event information
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static String getNumLeftTicket(Connection con, int query) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE event_id='" + query + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();

        while (results.next()) {
            sb.append(results.getString("num_tickets"));
        }
        return sb.toString();
    }

    /**
     * Method that returns HTML formatted Event information
     *
     * @param con
     * @return
     * @throws SQLException
     */
    public static String getNumSoldTicket(Connection con, int query) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String selectAllContactsSql = "SELECT * FROM " + tableName + " WHERE event_id='" + query + "';";
        PreparedStatement selectAllContactsStmt = con.prepareStatement(selectAllContactsSql);
        ResultSet results = selectAllContactsStmt.executeQuery();

        while (results.next()) {
            sb.append(results.getString("sold_tickets"));
        }
        return sb.toString();
    }

    /**
     * this method updates number of tickets left and sold for particular event.
     *
     * @param con
     * @param eventID
     * @param ticketsLeftUpdated
     * @param ticketsSoldUpdated
     * @throws SQLException
     */
    public static void updateTickets(Connection con, int eventID, int ticketsLeftUpdated, int ticketsSoldUpdated) throws SQLException {
        String query1 = "UPDATE " + tableName + " SET num_tickets='" + ticketsLeftUpdated + "' WHERE event_id='" + eventID + "';";
        PreparedStatement insertContactStmt = con.prepareStatement(query1);
        insertContactStmt.executeUpdate();

        String query2 = "UPDATE " + tableName + " SET sold_tickets='" + ticketsSoldUpdated + "' WHERE event_id='" + eventID + "';";
        PreparedStatement insertContactStmt2 = con.prepareStatement(query2);
        insertContactStmt2.executeUpdate();
    }


    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param id
     * @return
     */
    public static String returnEventDetail(int id) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getEventDetailsHTML(connection, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @return
     */
    public static String getPastEvents() {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getPastEvents(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param query
     * @return
     */
    public static String returnSearchEvent(String query, String searchType) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getEventSearchHTML(connection, query, searchType);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }


    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param query
     * @return
     */
    public static String returnNumLeftTickets(int query) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getNumLeftTicket(connection, query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param query
     * @return
     */
    public static String returnNumSoldTickets(int query) {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getNumSoldTicket(connection, query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    public static void updateTicketsDB(int eventID, int ticketsLeftUpdated, int ticketsSoldUpdated) {
        try (Connection connection = DBCPDataSource.getConnection()) {
            updateTickets(connection, eventID, ticketsLeftUpdated, ticketsSoldUpdated);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @return
     */
    public static String returnEvents() {
        String ret = "";
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = getEventsHTML(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }


    /**
     * Method that connect to the database using a PreparedStatement.
     *
     * @param query
     */
    public static boolean createEventDB(String query) {
        boolean ret = false;
        try (Connection connection = DBCPDataSource.getConnection()) {
            ret = executeInsertEvent(connection, query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return ret;
    }

    /**
     * A method to demonstrate using a PreparedStatement to execute a database insert.
     */
    public static boolean executeInsertEvent(Connection con, String query) throws SQLException {
        PreparedStatement insertContactStmt = con.prepareStatement(query);
        int flag = insertContactStmt.executeUpdate();
        return flag > 0;
    }
}
