package cs601.project4.Servlets;

import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static cs601.project4.MySQL.Tables.SessionsTable.getEmailDB;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.readBody;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;

/**
 * Handle requests to /updates
 */
public class UpdateAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.OK_200);

            //check if user email in user TAble
            String userEmail = getEmailDB(req.getSession(true).getId());
            System.out.println("USER EMAAIL: " + userEmail);
            if (!UsersTable.isUserInTable(userEmail)) {
                UsersTable.insertEmail(userEmail);
                System.out.println("User inserted in users Table!!!");
            }
            resp.getWriter().println(ServerConstants.UPDATE_HTML);
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
        System.out.println(body);
        String query = updateUserInfo(body);
        String userEmail = returnClientEmail(req);
        if (!query.equals("")) {
            UsersTable.updateElements(query, userEmail);
        }
        resp.getWriter().println(getAccountUpdatedHTML());
    }

    /**
     * helper method that return html formatted response.
     *
     * @return
     */
    public static String getAccountUpdatedHTML() {
        return ServerConstants.UPDATE_HTML + ServerConstants.ACCOUNT_UPDATED + Buttons.ACCOUNT_BUTTON + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER;
    }

    /**
     * Helper Method that updats user Info in DB.
     *
     * @param body
     * @return
     */
    public static String updateUserInfo(String body) {

        String[] parts = body.split("&");
        ArrayList<String> list = new ArrayList<>();

        for (int i = 0; i < parts.length; i++) {

            String[] parts2 = parts[i].split("=");

            if (i == 0 && parts2.length > 1) {
                System.out.println("updated first name");
                list.add("first_name = '" + parts2[1].replace("+", " ") + "'");

            } else if (i == 1 && parts2.length > 1) {
                System.out.println("updated last name");
                list.add("last_name = '" + parts2[1].replace("+", " ") + "'");


            } else if (i == 2 && parts2.length > 1) {
                System.out.println("updated country");
                list.add("country = '" + parts2[1].replace("+", " ") + "'");

            } else if (i == 3 && parts2.length > 1) {
                System.out.println("updated contact");
                list.add("contact = '" + parts2[1].replace("+", " ") + "'");

            } else if (i == 4 && parts2.length > 1) {
                System.out.println("updated zip");
                list.add("zip = " + parts2[1].replace("+", " "));
            }
        }
        String query = "";
        for (int j = 0; j < list.size(); j++) {
            query += list.get(j);
            if (j < list.size() - 1) {
                query += ", ";
            }
        }
        return query;
    }
}
