package cs601.project4.Servlets;


import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;

import cs601.project4.Util.HTTPFetcher;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.ClientInfo;
import cs601.project4.Util.Config;
import cs601.project4.Util.LoginUtilities;
import cs601.project4.Util.HTMLExtra;

import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.Cookies.handleCookies;
import static cs601.project4.Util.HTMLExtra.*;

/**
 * Handle requests to /loginSlack
 */
public class LoginSlackServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);


        if (clientInfoObj != null) {
            // already authed, no need to log in
            resp.getWriter().println(returnHeader("Login"));
            resp.getWriter().println(ServerConstants.STYLE);
            resp.getWriter().println("<h1>" + HTMLExtra.returnClientName(req) + ", You have already been authenticated</h1>\n");
            resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
            return;
        }

        // retrieve the config info from the context
        Config config = (Config) req.getServletContext().getAttribute(LoginServerConstants.CONFIG_KEY);


        // retrieve the code provided by Slack
        String code = req.getParameter(LoginServerConstants.CODE_KEY);

        // generate the url to use to exchange the code for a token:
        // After the user successfully grants your app permission to access their Slack profile,
        // they'll be redirected back to your service along with the typical code that signifies
        // a temporary access code. Exchange that code for a real access token using the
        // /openid.connect.token method.
        String url = LoginUtilities.generateSlackTokenURL(config.getClient_id(), config.getClient_secret(), code, config.getRedirect_url());

        // Make the request to the token API
        String responseString = HTTPFetcher.doGet(url, null);
        Map<String, Object> response = LoginUtilities.jsonStrToMap(responseString);

        ClientInfo clientInfo = LoginUtilities.verifyTokenResponse(response, sessionId);

        if (clientInfo == null) {
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(returnHeader("Login"));
            resp.getWriter().println("<h1>Oops, login unsuccessful</h1>\n");
            resp.getWriter().println("<h2>Try Again!</h2>\n");
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        } else {

            req.getSession().setAttribute(LoginServerConstants.CLIENT_INFO_KEY, clientInfo);
            resp.setStatus(HttpStatus.OK_200);

            //if email in user table
            if (UsersTable.isUserInTable(clientInfo.getEmail())) {
                System.out.println("user in DB");

                if (clientInfo.getFirstName() != null) {
                    resp.getWriter().println(getLoginHTML(clientInfo.getFirstName()));
                } else {
                    resp.getWriter().println(getFirstTimeUserHTML());
                }
            } else{
                System.out.println("user not in DB");
                UsersTable.insertEmail(clientInfo.getEmail());
                resp.getWriter().println(getFirstTimeUserHTML());
            }
            handleCookies(req, clientInfo, sessionId);
        }
    }
}


