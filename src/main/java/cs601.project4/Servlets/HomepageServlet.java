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

import static cs601.project4.MySQL.Tables.SessionsTable.getNameDB;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.returnClientName;

/**
 * Handle requests to /home
 */
public class HomepageServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession(true).getId();
        Object clientInfoObj = req.getSession().getAttribute(LoginServerConstants.COOKIE);

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(returnHeader("Homepage"));
            resp.getWriter().println(HTMLExtra.addingPicture("https://blog.hubspot.com/hubfs/homepage-web-design.jpg", 600, 400));
            resp.getWriter().println("<h1>Hello " + getNameDB(sessionId) + ", </h1>");
            resp.getWriter().println("<h1>What do you wanna explore Today?</h1><hr></hr><hr></hr>");
            resp.getWriter().println(Buttons.ACCOUNT_BUTTON);
            resp.getWriter().println(Buttons.TRANSACTIONS_BUTTON);
            resp.getWriter().println(Buttons.EVENTS_BUTTON);
            resp.getWriter().println(Buttons.TRANSFER_BUTTON);
            resp.getWriter().println(Buttons.LOGOUT_BUTTON);

        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Homepage"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
        }
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);
    }


}