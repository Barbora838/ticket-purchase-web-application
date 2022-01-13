package cs601.project4.Servlets;

import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.ClientInfo;
import cs601.project4.Util.Cookies;
import cs601.project4.Util.HTMLExtra;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.PasswordsTable.verifyDB;
import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.MySQL.Tables.UsersTable.insertEmail;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.*;
import static cs601.project4.Util.PasswordUtil.hashPassword;


/**
 * Handle requests to /login
 */
public class LoginServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(returnHeader("Login"));
        resp.getWriter().println(returnLoginGetHTML());
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sessionId = req.getSession(true).getId();
        resp.setStatus(HttpStatus.OK_200);
        String body = readBody(req);
        System.out.println("Body from Login Servlet2: " + body);

        if (checkBody(body)) {

            String email = getEmail(body).replace("%40", "@");
            String hashPass = hashPassword(getPass(body));

            if (verifyDB(email, hashPass)) {

                Cookies.handleCookies2(req, sessionId, email);
                ClientInfo clientInfo = new ClientInfo(email);
                req.getSession().setAttribute(LoginServerConstants.CLIENT_INFO_KEY, clientInfo);

                if (UsersTable.isUserInTable(email)) {
                    resp.getWriter().println(getLoginHTML(UsersTable.returnName(email)));
                } else {
                    insertEmail(email);
                    resp.getWriter().println(getFirstTimeUserHTML());
                }

            } else {
                resp.getWriter().println(returnHeader("Login") + HTMLExtra.addingPicture("https://www.incimages.com/uploaded_files/image/1920x1080/getty_506910700_258966.jpg", 350, 250) + "<h1>Wrong Credentials!</h1>");
                resp.getWriter().println(Buttons.LANDING_BUTTON);
                resp.getWriter().println(ServerConstants.PAGE_FOOTER);
            }

        } else {
            resp.getWriter().println(returnHeader("Login") + "<h1>Wrong Input!</h1>");
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }

    /**
     * This method checks if the input is in correct format.
     *
     * @param body
     * @return
     */
    public static boolean checkBody(String body) {
        String[] split = body.split("&");
        for (int i = 0; i < split.length; i++) {
            String[] split2 = split[i].split("=");

            if (split2.length == 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Helper method that returns Email.
     *
     * @param body
     * @return
     */
    public static String getEmail(String body) {
        String[] split = body.split("&");
        String[] split2 = split[0].split("=");
        return split2[1];
    }

    /**
     * Helper method that returns Password.
     *
     * @param body
     * @return
     */
    public static String getPass(String body) {
        String[] split = body.split("&");
        String[] split2 = split[1].split("=");
        return split2[1];
    }
}
