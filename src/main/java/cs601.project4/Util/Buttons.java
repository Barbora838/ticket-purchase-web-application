package cs601.project4.Util;

/**
 * Buttons Class.
 */
public class Buttons {

    public static final String url = "https://7b99-90-183-14-220.ngrok.io";

    public static final String ACCOUNT_BUTTON =
            "<div>\n" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Go to your User Account</h3>\n" +
                    "        <button onclick=\"account()\">USER ACCOUNT</button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function account() {\n" +
                    "            location.replace(\"" + url + "/account\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";

    public static final String HOMEPAGE_BUTTON =
            "<div>\n" +
                    "<br/><br/>\n" +
                    "        <h3 class=\"w3-center\">Want to go to Homepage</h3>\n" +
                    "        <button onclick=\"home()\"><img id=\"myImg\" src=\"https://i.pinimg.com/564x/28/da/5a/28da5aa7f2e38b6428d584b37ddf237b.jpg\" alt=\"home\" width=\"80\" height=\"80\"></img></button>\n" +
                    "        <script>\n" +
                    "        function home() {\n" +
                    "            location.replace(\"" + url + "/homepage\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";

    public static final String BACK_BUTTON =
            "<div>\n" +
                    "<br/>\n" +
                    "        <button onclick=\"back()\">BACK</button>\n" +
                    "        <script>\n" +
                    "        function back() {\n" +
                    "            location.replace(\"" + url + "/events\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";

    public static final String CREATE_EVENT_BUTTON =
            "\n<div>\n" +
                    "<br/>\n" +
                    "        <h2 class=\"w3-center\">CREATE NEW EVENT FEATURE</h2>\n" +
                    "        <button onclick=\"create()\">NEW EVENT</button>\n" +
                    "        <script>\n" +
                    "        function create() {\n" +
                    "            location.replace(\"" + url + "/newEvent\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";


    public static final String TRANSACTIONS_BUTTON =
            "<div>\n" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Show previously purchased Tickets</h3>\n" +
                    "        <button onclick=\"transactions()\">SHOW TRANSACTIONS</button>\n" +
                    "        <script>\n" +
                    "        function transactions() {\n" +
                    "            location.replace(\"" + url + "/transactions\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";

    public static final String TRANSFER_BUTTON =
            "<div>\n" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Transfer Tickets</h3>\n" +
                    "        <button onclick=\"transfer()\">TRANSFER TICKETS</button>\n" +
                    "        <script>\n" +
                    "        function transfer() {\n" +
                    "            location.replace(\"" + url + "/transfer\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";

    public static final String LOGOUT_BUTTON =
            "<div>\n" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Want to Logout</h3>\n" +
                    "        <button onclick=\"logout()\"><img id=\"myImg\" src=\"https://as2.ftcdn.net/v2/jpg/04/54/10/21/1000_F_454102129_3Zcqm432RDYm4VxqojypqVHKjHl6gBpH.jpg\" width=\"150\" height=\"100\"></img></button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function logout() {\n" +
                    "            location.replace(\"" + url + "/logout\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";


    public static final String LANDING_BUTTON=
            "<div>\n" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">You are not Logged In!!</h3>\n" +
                    "        <button onclick=\"landing()\"><img id=\"myImg\" src=\"https://www.pinclipart.com/picdir/big/564-5641704_orange-login-clip-art-at-clker-login-button.png\" width=\"200\" height=\"200\"></img></button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function landing() {\n" +
                    "            location.replace(\"" + url + "/\")\n" +
                    "        }\n" +
                    "        </script>\n" +
                    "</div>\n";


    public static final String LOGIN_SLACK_BUTTON =
            "<div>" +
                    "<br/><br/><br/>\n" +
                    "        <h3 class=\"w3-center\">Go back to you Login Page</h3>\n" +
                    "        <button onclick=\"login()\">LOGIN</button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function login() {\n" +
                    "            location.replace(\"" + url + "/loginSlack\")\n" +
                    "        }\n" +
                    "        </script>" +
                    "</div>";

    public static final String LOGIN_BUTTON =
            "<div>" +
                    "<br/><br/><br/>\n" +
                    "        <h2 class=\"w3-center\">Login Without Slack</h2><hr></hr>\n" +
                    "        <button onclick=\"login()\"><img id=\"myImg\" src=\"https://st.depositphotos.com/1842549/3357/i/950/depositphotos_33573731-stock-photo-login-icon.jpg\" width=\"200\" height=\"200\"></img></button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function login() {\n" +
                    "            location.replace(\"" + url + "/login\")\n" +
                    "        }\n" +
                    "        </script>" +
                    "</div>";

    public static final String UPDATE_ACCOUNT_BUTTON =
            "<div>" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Update your account information</h3>\n" +
                    "        <button onclick=\"update()\">UPDATE ACCOUNT</button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function update() {\n" +
                    "            location.replace(\"" + url + "/updates\")\n" +
                    "        }\n" +
                    "        </script>" +
                    "</div>";

    public static final String EVENTS_BUTTON =
            "<div>" +
                    "<br/>\n" +
                    "        <h3 class=\"w3-center\">Display all the Events</h3>\n" +
                    "        <button onclick=\"events()\">VIEW EVENTS</button>\n" +
                    "\n" +
                    "        <script>\n" +
                    "        function events() {\n" +
                    "            location.replace(\"" + url + "/events\")\n" +
                    "        }\n" +
                    "        </script>" +
                    "</div>";


}
