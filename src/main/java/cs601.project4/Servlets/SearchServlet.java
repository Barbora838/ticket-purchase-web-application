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

import static cs601.project4.MySQL.Tables.EventsTable.returnSearchEvent;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Server.ServerConstants.returnHeaderSearchButton;
import static cs601.project4.Util.HTMLExtra.readBody;


/**
 * Handle requests to /search
 */
public class SearchServlet extends HttpServlet {

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

        if (!validateInput(body)) {
            resp.getWriter().println(getEventPageSearchHTML("empty input", "name"));
        } else {
            resp.getWriter().println(getEventPageSearchHTML(getQuery(body), getTypeSearch(body)));
        }
    }

    /**
     * Method that returns searchType from the user's input.
     *
     * @param body
     * @return
     */
    public static String getTypeSearch(String body) {
        String[] split1 = body.split("&");
        String[] split2 = split1[0].split("=");
        return split2[1];
    }

    /**
     * Method that returns query from the user's input.
     *
     * @param body
     * @return
     */
    public static String getQuery(String body) {
        String[] split1 = body.split("&");
        String[] split2 = split1[1].split("=");
        return split2[1];
    }

    /**
     * Helper method that check the correct format of the body and return true if it is correct qnd false otherwise.
     *
     * @param body
     * @return
     */
    public static boolean validateInput(String body) {
        if (body.equals("")) {
            return false;
        }
        String[] split = body.split("&");
        if (split.length == 1) {
            return false;
        }
        String[] splitType = split[0].split("=");
        if (splitType.length == 1) {
            return false;
        }
        String[] splitQuery = split[1].split("=");
        if (splitQuery.length == 1) {
            return false;
        }
        return true;
    }

    /**
     * Helper method that returns event page HTML.
     *
     * @param query
     * @return
     */
    public static String getEventPageSearchHTML(String query, String searchType) {
        StringBuilder sb = new StringBuilder();
        sb.append(returnHeaderSearchButton("SearchType"));
        sb.append(HTMLExtra.addingPicture("https://cdn.pixabay.com/photo/2016/12/11/08/01/coming-soon-1898936_1280.jpg", 300, 230) + "<br/><br/><br/>\n" + ServerConstants.SEARCH_HTML + "<br/>\n");
        String retVal = returnSearchEvent(query, searchType);

        if (retVal.equals("null") || retVal.equals("")) {
            sb.append("<h2> Result for " + query + " not found!</h2>\n");
            sb.append("<hr></hr>");
        } else {
            sb.append(retVal);
        }
        sb.append(Buttons.HOMEPAGE_BUTTON);
        sb.append(ServerConstants.PAGE_FOOTER);
        return sb.toString();
    }
}