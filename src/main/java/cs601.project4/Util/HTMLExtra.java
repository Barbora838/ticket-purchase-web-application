package cs601.project4.Util;

import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

import static cs601.project4.Server.ServerConstants.returnHeader;

/**
 * HTMLExtra Class.
 */
public class HTMLExtra {

    /**
     * Helper method that adds picture int Html Page.
     *
     * @param name
     * @param width
     * @param height
     * @return
     */
    public static String addingPicture(String name, int width, int height) {
        return "<img src=\"" + name + "\" alt=\"picture\" width=\"" + width + "\" height=\"" + height + "\"></img>\n";
    }


    /**
     * Helper method that returns the Clients Email based on the session.
     *
     * @param req
     * @return
     */
    public static String returnClientEmail(HttpServletRequest req) {
        Object clientInfoObj = req.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);
        ClientInfo client = (ClientInfo) clientInfoObj;
        return client.getEmail();
    }

    /**
     * Helper method that returns the Clients Name based on the session.
     *
     * @param req
     * @return
     */
    public static String returnClientName(HttpServletRequest req) {
        Object clientInfoObj = req.getSession().getAttribute(LoginServerConstants.CLIENT_INFO_KEY);
        ClientInfo client = (ClientInfo) clientInfoObj;
        return client.getFirstName();
    }

    /**
     * Method that reads & returns Body of the request.
     *
     * @param req
     * @return
     * @throws IOException
     */
    public static String readBody(HttpServletRequest req) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        String content = "";

        try {
            bufferedReader = req.getReader();
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                sb.append(charBuffer, 0, bytesRead);
            }

        } catch (IOException ex) {
            throw ex;

        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }
        return sb.toString();
    }

    /**
     * Helper method that returns html for /login & /loginSlack POST method.
     *
     * @param firstname
     * @return
     */
    public static String getLoginHTML(String firstname) {
        return returnHeader("Login") +
                addingPicture("https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/8/2016/09/06105011/ticket-giveaways.jpg", 400, 300) +
                "<h1>Hello " + firstname + "</h1>\n\n" +
                Buttons.HOMEPAGE_BUTTON +
                ServerConstants.PAGE_FOOTER;
    }

    /**
     * Helper method that returns html for /login GET method.
     *
     * @return
     */
    public static String returnLoginGetHTML() {
        return returnHeader("Login") +
                addingPicture("https://www.techrepublic.com/a/hub/i/r/2021/05/05/99b7f203-5ab7-4c99-9562-c19d0ae0f932/resize/770x/1e89c96095536f64227df3f281e3d70b/password-security.jpg", 400, 300) +
                "<h1>Login Page</h1><hr></hr><hr></hr><br/>\n" +
                "<form action=\"/login\" method=\"post\">\n" +
                "  <label for=\"username\"><b>Username:</b></label>\n" +
                "  <input type=\"text\" id=\"username\" name=\"username\"><br><br>\n" +
                "  <label for=\"password\"><b>Password:</b></label>\n" +
                "  <input type=\"text\" id=\"pass\" name=\"pass\"><br><br>\n" +

                "  <input type=\"checkbox\" onclick=\"myFunction()\">Show Password<br/><br/>"+
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form>\n"

                +"<script>\n" +
                "function myFunction() {\n" +
                "  var x = document.getElementById(\"pass\");\n" +
                "  if (x.type === \"password\") {\n" +
                "    x.type = \"text\";\n" +
                "  } else {\n" +
                "    x.type = \"password\";\n" +
                "  }\n" +
                "}\n" +
                "</script>";
    }

    /**
     * Helper method that returns html.
     *
     * @return
     */
    public static String getFirstTimeUserHTML() {
        return returnHeader("Login") + addingPicture("https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/8/2016/09/06105011/ticket-giveaways.jpg", 400, 300) + Buttons.UPDATE_ACCOUNT_BUTTON + ServerConstants.PAGE_FOOTER;
    }
}
