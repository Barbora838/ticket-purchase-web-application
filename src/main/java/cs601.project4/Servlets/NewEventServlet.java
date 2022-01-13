package cs601.project4.Servlets;

import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static cs601.project4.MySQL.Tables.EventsTable.createEventDB;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.readBody;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;

/**
 * Handle requests to /newEvent
 */
public class NewEventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(ServerConstants.NEW_EVENT_HTML);
            resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Landing"));
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
        ArrayList<String> list = createEvent(body, userEmail);

        boolean flag = true;
        // 6 input variables + 3 internal variables
        if (validateEventArray(list)) {
            String query = createQuery(list);
            System.out.println("QUERY: " + query);

            if (createEventDB(query)) {
                resp.getWriter().println(ServerConstants.NEW_EVENT_HTML);
                resp.getWriter().println(ServerConstants.EVENT_CREATED);
                flag = false;
            }
        }
        if (flag) {
            resp.getWriter().println(ServerConstants.NEW_EVENT_HTML);
            resp.getWriter().println(ServerConstants.EVENT_NOT_CREATED);
        }
        resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);
    }

    /**
     * Helper method that validates input Array.
     *
     * @param list
     * @return
     */
    public static boolean validateEventArray(ArrayList<String> list) {
        return list.size() == 9;
    }

    /**
     * Helper method that gets the event parts that user wants to add to the database.
     *
     * @param body
     * @param email
     * @return
     * @throws UnsupportedEncodingException
     */
    public static ArrayList<String> createEvent(String body, String email) throws UnsupportedEncodingException {

        String[] parts = body.split("&");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {

            String[] parts2 = parts[i].split("=");

            if (i == 0 && parts2.length > 1) {
                list.add("name='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 1 && parts2.length > 1) {
                list.add("type='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 2 && parts2.length > 1) {
                list.add("date='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 3 && parts2.length > 1) {
                list.add("location='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 4 && parts2.length > 1) {
                if (isDigit(parts2[1])) {
                    list.add("price='" + parts2[1] + "'");
                }
            } else if (i == 5 && parts2.length > 1) {
                list.add("details='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 6 && parts2.length > 1) {
                list.add("picture='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()).replace("'", "") + "'");

            } else if (i == 7 && parts2.length > 1) {
                if (isDigit(parts2[1]) && getDigit(parts2[1]) >= 1) {
                    list.add("num_tickets='" + java.net.URLDecoder.decode(parts2[1], StandardCharsets.UTF_8.toString()) + "'");
                }
            }
        }

        //add the user who created this event
        list.add("created_by='" + email + "'");
        return list;
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
     * Helper method that check and validate if input is digit or not.
     *
     * @param input
     * @return
     */
    public static int getDigit(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return 0;
        }
    }


    /**
     * Helper method that creates query.
     *
     * @param list
     * @return
     */
    public static String createQuery(ArrayList<String> list) {

        String query1 = "INSERT INTO events (";
        String query2 = "VALUES (";

        for (int j = 0; j < list.size(); j++) {
            String[] split = list.get(j).split("=");

            if (split[0].equals("name")) {
                query1 += split[0];

            } else if (split[0].equals("date")) {
                query1 += split[0];

            } else if (split[0].equals("location")) {
                query1 += split[0];

            } else if (split[0].equals("price")) {
                query1 += split[0];

            } else if (split[0].equals("details")) {
                query1 += split[0];

            } else if (split[0].equals("picture")) {
                query1 += split[0];

            } else if (split[0].equals("created_by")) {
                query1 += split[0];

            } else if (split[0].equals("type")) {
                query1 += split[0];

            } else if (split[0].equals("num_tickets")) {
                query1 += split[0];

            }
            query2 += split[1];

            if (j < list.size() - 1) {
                query1 += ", ";
                query2 += ", ";
            }
        }

        query1 += ", sold_tickets) ";
        query2 += ", 0);";
        return query1 + query2;
    }
}