package cs601.project4.Servlets;

import cs601.project4.MySQL.Tables.TransactionsTable;
import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.EventsTable.*;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.MySQL.Tables.TicketsTable.returnTickets;
import static cs601.project4.MySQL.Tables.TicketsTable.updateTicketDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.readBody;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;

/**
 * Handle requests to /tickets
 */
public class TicketServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (!validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("SearchServlet"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpStatus.OK_200);
        String body = readBody(req);
        System.out.println("BODY====>" + body);
        String userEmail = returnClientEmail(req);

        if (getNumTickets(body) == 0) {
            System.out.println("wrong input");
            resp.getWriter().println(ServerConstants.TICKET_NOT_PURCHASED);

        } else {
            if (checkTicketAmount(getEventID(body), getNumTickets(body))) {
                updateTickets(getEventID(body), getNumTickets(body));
                resp.getWriter().println(ServerConstants.TICKET_PURCHASED);
                updateTransactions(userEmail, getNumTickets(body), getEventID(body));
                resp.getWriter().println(returnTickets(userEmail));
            } else {
                resp.getWriter().println(ServerConstants.TICKET_NOT_PURCHASED);
            }
        }
        resp.getWriter().println("<br/><br/>");
        resp.getWriter().println(Buttons.EVENTS_BUTTON);
        resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);
    }

    /**
     * This method returns event_id fromt the body.
     *
     * @param body
     * @return
     */
    public static int getEventID(String body) {

        String[] split = body.split("&");
        String[] split2 = split[1].split("=");
        if (split2.length == 1) {
            return 0;
        }
        System.out.println("event ID: " + split2[1]);
        return Integer.parseInt(split2[1]);
    }

    /**
     * This method returns number of tickets from the body.
     *
     * @param body
     * @return
     */
    public static int getNumTickets(String body) {

        String[] split = body.split("&");
        String[] split2 = split[0].split("=");
        if (split2.length == 1) {
            return 0;
        }
        System.out.println("number of tickets: " + split2[1]);

        //check if the input is digit
        if (!isDigit(split2[1])) {
            return 0;
        }

        return Integer.parseInt(split2[1]);
    }

    /**
     * Helper method that check and validate if input is digit or not.
     *
     * @param input
     * @return
     */
    public static boolean isDigit(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This method calculates and updates number of tickets left & sold.
     *
     * @param eventID
     * @param numTickets
     */
    public static void updateTickets(int eventID, int numTickets) {
        int ticketsLeft = Integer.parseInt(returnNumLeftTickets(eventID));
        int ticketsLeftUpdated = ticketsLeft - numTickets;

        int ticketsSold = Integer.parseInt(returnNumSoldTickets(eventID));
        int ticketsSoldUpdated = ticketsSold + numTickets;
        updateTicketsDB(eventID, ticketsLeftUpdated, ticketsSoldUpdated);
    }

    /**
     * This method update Transactions & Tickets table after user buy new tickets.
     *
     * @param email
     * @param numTickets
     * @param event_id
     */
    public static void updateTransactions(String email, int numTickets, int event_id) {
        updateTicketDB(email, numTickets, event_id);
        TransactionsTable.insertNewTransaction(email, numTickets, event_id);
    }

    /**
     * This method check how many ticket are left for particular event.
     *
     * @param eventID
     * @param numTickets
     * @return
     */
    public static boolean checkTicketAmount(int eventID, int numTickets) {
        String ticketsLeft = returnNumLeftTickets(eventID);
        System.out.println("Tickets left ==> " + ticketsLeft);

        if (Integer.parseInt(ticketsLeft) >= numTickets && numTickets > 0) {
            return true;
        }
        return false;
    }
}
