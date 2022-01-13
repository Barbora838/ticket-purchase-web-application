package cs601.project4.Server;

import com.google.gson.Gson;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.Config;
import cs601.project4.Util.HTMLExtra;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * HttpConstants Class.
 * A helper class to store various constants used for the HTTP server.
 */
public class ServerConstants {

    private static final String configFilename = "config.json";
    Gson gson = new Gson();
    static Config config;

    {
        try {
            config = gson.fromJson(new FileReader(configFilename), Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Helper method that returns modified titled header.
     *
     * @param name
     * @return
     */
    public static String returnHeader(String name) {
        return "\n<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "  <title>" + name + "</title>\n" +

                STYLE_BACKGROUND + "\n" +

                "</head>\n" + "\n" +
                "<body>\n"
                + "\n";
    }

    /**
     * Helper method that returns the correctly formatted html for Search button.
     *
     * @param name
     * @return
     */
    public static String returnHeaderSearchButton(String name) {
        return "<html>\n" +
                "<head>\n" +
                "  <title>" + name + "</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></meta>\n" +
                "<link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\"></link>\n" +
                "<style>\n" +
                "  body {\n" +
                "    background: linear-gradient(120deg, #f76d9d, #ECE3E6);\n" +
                "    width: 100%;\n" +
                "    height: 100%;\n" +
                "  }\n" +
                "  \n" +
                "  #para1 {\n" +
                "    text-align: center;\n" +
                "    font-size: 600%;\n" +
                "    color: black;\n" +
                "  }\n" +
                "  \n" +
                "  div.absolute {\n" +
                "    position: absolute;\n" +
                "    bottom: 10px;\n" +
                "    left: 10px;\n" +
                "  }\n" +
                "  body {\n" +
                "       font-family: \"Times New Roman\", Georgia, Serif;}\n" +
                "       h1, h2, h3, h4, h5, h6 {\n" +
                "       font-family: \"Playfair Display\";\n" +
                "       letter-spacing: 2px;\n" +
                "  }\n" +
                "body {\n" +
                "  font-family: Arial;\n" +
                "}\n" +
                "\n" +
                "* {\n" +
                "  box-sizing: border-box;\n" +
                "}\n" +
                "\n" +
                "form.example input[type=text] {\n" +
                "  padding: 10px;\n" +
                "  font-size: 17px;\n" +
                "  border: 1px solid grey;\n" +
                "  float: left;\n" +
                "  width: 80%;\n" +
                "  background: #f1f1f1;\n" +
                "}\n" +
                "\n" +
                "form.example button {\n" +
                "  float: left;\n" +
                "  width: 20%;\n" +
                "  padding: 10px;\n" +
                "  background: #f7e059;\n" +
                "  color: white;\n" +
                "  font-size: 17px;\n" +
                "  border: 1px solid grey;\n" +
                "  border-left: none;\n" +
                "  cursor: pointer;\n" +
                "}\n" +
                "\n" +
                "form.example button:hover {\n" +
                "  background: #0b7dda;\n" +
                "}\n" +
                "\n" +
                "form.example::after {\n" +
                "  content: \"\";\n" +
                "  clear: both;\n" +
                "  display: table;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n"
                + "\n";
    }

    public static final String PAGE_FOOTER =
            "<br/>\n" +
                    "<img src=\"https://www.pngkey.com/png/detail/268-2682488_by-ticketmaster-logo-logo-ticketmaster-es-png.png\" alt=\"LOGO\" align=\"right\" width=\"140\" height=\"50\"></img>\n" +
                    "</body>\n" +
                    "</html>\n";

    public static final String PAGE_FOOTER2 =
            "<br/>\n" +
                    "</body>\n" +
                    "</html>\n";

    public static final String STYLE_BACKGROUND = "<style>\n" +
            "  body {\n" +
            "    background: linear-gradient(120deg, #f76d9d, #ECE3E6);\n" +
            "    width: 100%;\n" +
            "    height: 100%;\n" +
            "  }\n" +
            "  \n" +
            "  #para1 {\n" +
            "    text-align: center;\n" +
            "    font-size: 600%;\n" +
            "    color: black;\n" +
            "  }\n" +
            "  \n" +
            "  div.absolute {\n" +
            "    position: absolute;\n" +
            "    bottom: 10px;\n" +
            "    left: 10px;\n" +
            "  }\n" +
            "  body {\n" +
            "       font-family: \"Times New Roman\", Georgia, Serif;}\n" +
            "       h1, h2, h3, h4, h5, h6 {\n" +
            "       font-family: \"Playfair Display\";\n" +
            "       letter-spacing: 2px;\n" +
            "  }\n" +
            "<br/>\n" +
            "</style>\n";


    public static final String NOT_FOUND_USER = returnHeader("UserNotFound") +
            ServerConstants.STYLE +
            "<h3>User Not Found. Wrong credentials!</h3>\n" +
            "\n" +
            Buttons.LOGIN_SLACK_BUTTON +
            ServerConstants.PAGE_FOOTER;

    public static final String LOGOUT_PAGE = returnHeader("LogoutPage") +
            ServerConstants.STYLE +
            "<h3>Thanks for visiting!</h3>\n" +
            "\n" +
            HTMLExtra.addingPicture("https://image.posterlounge.com/img/products/660000/651738/651738_poster_l.jpg", 330, 450) +
            ServerConstants.PAGE_FOOTER;

    public static String returnLogoutPage(String name) {
        return returnHeader("LogoutPage") +
                ServerConstants.STYLE +
                "<h3>Thanks for visiting " + name + "!</h3>\n" +
                "\n" +
                HTMLExtra.addingPicture("https://image.posterlounge.com/img/products/660000/651738/651738_poster_l.jpg", 330, 450) +
                ServerConstants.PAGE_FOOTER;
    }

    public static String returnBuyTickets() {
        return returnHeader("Purchase") +
                HTMLExtra.addingPicture("https://blogmedia.evbstatic.com/wp-content/uploads/wpmulti/sites/8/2016/09/06105011/ticket-giveaways.jpg", 400, 300) +
                "<h3>How many Tickets?!</h3>\n" +

                "<form action=\"/tickets\" method=\"post\">\n" +
                "   <input type=\"text\" id=\"number\" name=\"number\"/><br/>\n" +
                "   <input type=\"submit\" value=\"Submit\"/>\n" +
                "</form>" +

                "<br/><br/>\n" +
                Buttons.HOMEPAGE_BUTTON +
                ServerConstants.PAGE_FOOTER;
    }

    public static final String TICKET_PURCHASED = returnHeader("TicketsPurchased") +
            ServerConstants.STYLE +
            "<h1>Ticket Successfully Purchased! </h1>\n" +
            HTMLExtra.addingPicture("https://api.time.com/wp-content/uploads/2014/05/160611383.jpg", 350, 250);


    public static final String TICKET_NOT_PURCHASED = returnHeader("TicketsNotPurchased") +
            ServerConstants.STYLE +
            "<h1>Ticket NOT Purchased! </h1>\n" +
            HTMLExtra.addingPicture("https://www.incimages.com/uploaded_files/image/1920x1080/getty_506910700_258966.jpg", 350, 250);

    public static final String TICKET_TRANSFERED = returnHeader("TicketsPurchased") +
            ServerConstants.STYLE +
            "<h1>Ticket Successfully Transfered! </h1>\n" +
            HTMLExtra.addingPicture("https://api.time.com/wp-content/uploads/2014/05/160611383.jpg", 350, 250) + Buttons.TRANSFER_BUTTON;


    public static final String ACCOUNT_UPDATED =
            "\n<h3>Account Successfully Updated! </h3>\n" +
                    HTMLExtra.addingPicture("https://api.time.com/wp-content/uploads/2014/05/160611383.jpg", 350, 250);


    public static final String EVENT_CREATED =
            "\n<h3>Event Successfully Created! </h3>\n" +
                    HTMLExtra.addingPicture("https://api.time.com/wp-content/uploads/2014/05/160611383.jpg", 350, 250);

    public static final String EVENT_NOT_CREATED =
            "\n<h3>Event NOT Successfully Created! </h3>\n" +
                    HTMLExtra.addingPicture("https://www.incimages.com/uploaded_files/image/1920x1080/getty_506910700_258966.jpg", 350, 250);


    public static final String UPDATE_HTML =
            returnHeader("UpdateAccount") +
                    ServerConstants.STYLE +
                    HTMLExtra.addingPicture("https://www.pinpng.com/pngs/m/172-1724473_business-users-lock-1-image-business-user-icon.png", 150, 100) +
                    "<h1>Update Account Info</h1><br/><br/>\n" +
                    "<hr></hr>\n" +
                    "<form action=\"/updates\" method=\"post\">\n" +
                    "   <label for=\"firstName\">First Name:</label><br/>\n" +
                    "   <input type=\"text\" id=\"firstName\" name=\"fistName\"/><br/>\n" +
                    "   <label for=\"lastName\">Last Name:</label><br/>\n" +
                    "   <input type=\"text\" id=\"lastName\" name=\"lastName\"/><br/>\n" +
                    "   <label for=\"country\">Country:</label><br/>\n" +
                    "   <input type=\"text\" id=\"country\" name=\"country\"/><br/>\n" +
                    "   <label for=\"contact\">Contact:</label><br/>\n" +
                    "   <input type=\"text\" id=\"contact\" name=\"contact\"/><br/>\n" +
                    "   <label for=\"zipCode\">Zip Code:</label><br/>\n" +
                    "   <input type=\"text\" id=\"zipCode\" name=\"zipCode\"/><br/>\n" +
                    "   <input type=\"submit\" value=\"Submit\"/>\n" +
                    "</form>";


    public static final String SEARCH_HTML =
                            "<br/>\n"+
                            "<form class=\"example\" action=\"/search\" method=\"post\" style=\"max-width:300px\">\n" +
                            "  <label for=\"searchType\"><b>Pick from:</b></label>\n" +
                                    "  <select name=\"searchType\" id=\"searchType\">\n" +
                                    "    <option value=\"name\">Search by Name</option>\n" +
                                    "    <option value=\"type\">Search by Type</option>\n" +
                                    "    <option value=\"location\">Search by Location</option>\n" +
                                    "  </select>"+
                            "  <input type=\"text\" id=\"type\" placeholder=\"\" name=\"type\"></input>\n" +
                            "  <button type=\"submit\"><i class=\"fa fa-search\"></i></button>\n" +
                            "</form>\n";



    public static final String NEW_EVENT_HTML =
            returnHeader("NewEvent") +
                    ServerConstants.STYLE +
                    HTMLExtra.addingPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTlj3knBQOn5rGgyeXq-wVecpWAfuInLV4teQ", 350, 200) +
                    "<h1>CreateEvent</h1><br/><br/>" +
                    "<form action=\"/newEvent\" method=\"post\">\n" +
                    "   <label for=\"name\">Name:</label><br/>\n" +
                    "   <input type=\"text\" id=\"name\" name=\"name\"/><br/>\n" +
                    "   <label for=\"type\">Type:</label><br/>\n" +
                    "   <input type=\"text\" id=\"type\" name=\"type\"/><br/>\n" +
                    "   <label for=\"date\">Date:</label><br/>\n" +
                    "   <input type=\"text\" id=\"date\" name=\"date\" value=\"2021-12-13\"" +
                    "       min=\"2021-12-13\" max=\"2025-12-31\"/><br/>\n" +

                    "   <label for=\"location\">Location:</label><br/>\n" +
                    "   <input type=\"text\" id=\"location\" name=\"location\"/><br/>\n" +
                    "   <label for=\"price\">Price:</label><br/>\n" +
                    "   <input type=\"text\" id=\"price\" name=\"price\"/><br/>\n" +
                    "   <label for=\"details\">Details:</label><br/>\n" +
                    "   <input type=\"text\" id=\"details\" name=\"details\"/><br/>\n" +
                    "   <label for=\"picture\">Picture:</label><br/>\n" +
                    "   <input type=\"text\" id=\"picture\" name=\"picture\"/><br/>\n" +
                    "   <label for=\"num_tickets\">Number of Tickets:</label><br/>\n" +
                    "   <input type=\"text\" id=\"num_tickets\" name=\"num_tickets\"/><br/>\n" +
                    "   <input type=\"submit\" value=\"Submit\"/>\n" +
                    "</form>\n";


    public static final String STYLE =
            "<style>\n" +
                    "body {font-family: \"Times New Roman\", Georgia, Serif;}\n" +
                    "h1, h2, h3, h4, h5, h6 {\n" +
                    "  font-family: \"Playfair Display\";\n" +
                    "  letter-spacing: 3px;\n" +
                    "}\n" +
                    "</style>\n";
}