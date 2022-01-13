package cs601.project4.Util;

import cs601.project4.Server.LoginServerConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Random;

import static cs601.project4.MySQL.Tables.SessionsTable.*;

/**
 * A Cookies class.
 */
public class Cookies {

    /**
     * Method that either insert new session ID and cookie if the user is in not the DB otherwise it updates those elements.
     * (when user log in with slack)
     *
     * @param req
     * @param clientInfo
     * @param sessionId
     */
    public static void handleCookies(HttpServletRequest req, ClientInfo clientInfo, String sessionId) {

        String cookie = Cookies.generateRandomString();
        req.getSession().setAttribute(LoginServerConstants.COOKIE, cookie);

        if (validateEmailDB(clientInfo.getFirstName(), clientInfo.getEmail())) {
            updateSessionDB(sessionId, cookie, clientInfo.getEmail());
            System.out.println("cookie updated!!");
        } else {
            insertsSessionDB(clientInfo.getFirstName(), clientInfo.getEmail(), sessionId, cookie);
        }
        System.out.println("Hello from handleCookies: sessionID:" + sessionId + " , Cookie: " + cookie);
    }

    /**
     * Method that either insert new session ID and cookie if the user is in not the DB otherwise it updates those elements.
     * (when user log in without slack)
     *
     * @param req
     * @param sessionId
     * @param email
     */
    public static void handleCookies2(HttpServletRequest req, String sessionId, String email) {

        String cookie = Cookies.generateRandomString();
        req.getSession().setAttribute(LoginServerConstants.COOKIE, cookie);

        if (validateEmailDB2(email)) {
            updateSessionDB(sessionId, cookie, email);
            System.out.println("cookie updated!!");
        } else {
            insertsSessionDB2(email, sessionId, cookie);
        }
        System.out.println("Hello from handleCookies2: sessionID:" + sessionId + " , Cookie: " + cookie);
    }

    /**
     * Helper method that generates cookies.
     *
     * @return
     */
    // used from this website: https://www.baeldung.com/java-random-string
    public static String generateRandomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}
