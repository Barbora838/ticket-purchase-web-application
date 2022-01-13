package cs601.project4.Servlets;

import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.HTMLExtra;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;


/**
 * Handle requests to /account
 */
public class AccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.OK_200);
            String userEmail = returnClientEmail(req);
            resp.getWriter().println(getUserAccount(userEmail));
        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Landing"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }

    /**
     * Helper method that returns correctly formatted HTML Response.
     *
     * @param userEmail
     * @return
     */
    public static String getUserAccount(String userEmail) {

        StringBuilder sb = new StringBuilder();
        sb.append(returnHeader("UserAccount"));
        sb.append(HTMLExtra.addingPicture("https://images.all-free-download.com/images/graphiclarge/windows_7_user_102352.jpg", 200, 150));
        sb.append("<h1>User Account Page</h1><br/><br/>\n");
        sb.append(UsersTable.returnAccount(userEmail));
        sb.append(Buttons.UPDATE_ACCOUNT_BUTTON);
        sb.append(Buttons.HOMEPAGE_BUTTON);
        sb.append(ServerConstants.PAGE_FOOTER);
        return sb.toString();
    }
}
