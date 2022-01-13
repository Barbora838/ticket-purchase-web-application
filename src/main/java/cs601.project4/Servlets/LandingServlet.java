package cs601.project4.Servlets;

import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.Config;
import cs601.project4.Util.LoginUtilities;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.addingPicture;

/**
 * Landing page that allows a user to request to login with Slack.
 */
public class LandingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // retrieve the ID of this session
        String sessionId = req.getSession(true).getId();

        // determine whether the user is already authenticated
        Object clientInfoObj = req.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);
        if (clientInfoObj != null) {
            // already authed, no need to log in
            resp.getWriter().println(returnHeader("LogSlack"));
            resp.getWriter().println("<h1>You have already been authenticated</h1>");
            resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
            return;
        }

        // retrieve the config info from the context
        Config config = (Config) req.getServletContext().getAttribute(LoginServerConstants.CONFIG_KEY);

        /** From the OpenID spec:
         * state
         * RECOMMENDED. Opaque value used to maintain state between the request and the callback.
         * Typically, Cross-Site Request Forgery (CSRF, XSRF) mitigation is done by cryptographically
         * binding the value of this parameter with a browser cookie.
         *
         * Use the session ID for this purpose.
         */
        String state = sessionId;

        /** From the Open ID spec:
         * nonce
         * OPTIONAL. String value used to associate a Client session with an ID Token, and to mitigate
         * replay attacks. The value is passed through unmodified from the Authentication Request to
         * the ID Token. Sufficient entropy MUST be present in the nonce values used to prevent attackers
         * from guessing values. For implementation notes, see Section 15.5.2.
         */
        String nonce = LoginUtilities.generateNonce(state);

        // Generate url for request to Slack
        String url = LoginUtilities.generateSlackAuthorizeURL(config.getClient_id(),
                state,
                nonce,
                config.getRedirect_url());

        resp.setStatus(HttpStatus.OK_200);
        PrintWriter writer = resp.getWriter();
        writer.println(returnLandingHTML(url));

    }

    /**
     * Helper method that return correct HTML format for response
     *
     * @return
     */
    public static String returnLandingHTML(String url) {
        return returnHeader("LogSlack") + addingPicture("https://msmt.org/wp-content/uploads/2021/05/1478813947-tickets.png", 300, 200) + "<h1>Welcome to TICKETMASTER</h1><hr></hr><hr></hr>\n" + "<h3><i>Ticket Purchase Web Application</i></h3><br/><br/>\n" + "<h2>Login with Slack Demo Application</h2><hr></hr>\n" + "<a href=\"" + url + "\">\n<img src=\"" + LoginServerConstants.BUTTON_URL + "\"/></a>\n" + Buttons.LOGIN_BUTTON + ServerConstants.PAGE_FOOTER;
    }
}