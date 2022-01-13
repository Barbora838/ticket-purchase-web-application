package cs601.project4.Servlets;

import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.HTMLExtra;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.EventsTable.returnEventDetail;
import static cs601.project4.MySQL.Tables.EventsTable.returnEvents;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.*;
import static cs601.project4.Util.HTMLExtra.readBody;

/**
 * Handle requests to /events
 */
public class EventServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(getEventsHTMLGet());
        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Landing"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }

    /**
     * Helper method that returns the HTML for GET request.
     *
     * @return
     */
    public static String getEventsHTMLGet() {

        StringBuilder sb = new StringBuilder();
        sb.append(returnHeaderSearchButton("Event"));
        sb.append(HTMLExtra.addingPicture("https://rchumanesociety.org/wp-content/uploads/2019/08/calendar.jpg", 200, 150));
        sb.append("<h1>Upcoming Events</h1><br/>\n");
        sb.append("<h2>SEARCH FEATURE</h2>\n" );
        sb.append(SEARCH_HTML);
        sb.append(returnEvents());
        sb.append(Buttons.CREATE_EVENT_BUTTON);
        sb.append(Buttons.HOMEPAGE_BUTTON);
        sb.append(ServerConstants.PAGE_FOOTER);
        return sb.toString();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String body = readBody(req);
        String[] split = body.split("=");
        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(getEventPageHTMLPost(Integer.parseInt(split[1])));
    }

    /**
     * Helper method that returns the HTML for POST request.
     *
     * @return
     */
    public static String getEventPageHTMLPost(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append(returnHeader("ViewEvent"));
        sb.append(HTMLExtra.addingPicture("https://cdn.pixabay.com/photo/2016/12/11/08/01/coming-soon-1898936_1280.jpg", 300, 230));
        sb.append("<h1>Further Details: </h1>");
        sb.append(returnEventDetail(id));
        sb.append(Buttons.BACK_BUTTON);
        sb.append(Buttons.HOMEPAGE_BUTTON);
        sb.append(ServerConstants.PAGE_FOOTER);
        return sb.toString();
    }

}
