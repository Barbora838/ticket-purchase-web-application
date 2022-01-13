package cs601.project4.Applications;

import com.google.gson.Gson;
import cs601.project4.Server.LoginServerConstants;

import cs601.project4.Servlets.UpdateAccountServlet;
import cs601.project4.Servlets.LandingServlet;
import cs601.project4.Servlets.LoginSlackServlet;
import cs601.project4.Servlets.AccountServlet;
import cs601.project4.Servlets.LogoutServlet;
import cs601.project4.Servlets.HomepageServlet;
import cs601.project4.Servlets.TransactionsServlet;
import cs601.project4.Servlets.EventServlet;
import cs601.project4.Servlets.TicketServlet;
import cs601.project4.Servlets.NewEventServlet;
import cs601.project4.Servlets.SearchServlet;
import cs601.project4.Servlets.TransferTicket;
import cs601.project4.Servlets.LoginServlet;

import cs601.project4.Util.Config;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.FileReader;

/**
 * TicketApplication Class.
 */
public class TicketApplication {

    public static final int PORT = 8080;
    private static final String configFilename = "config.json";

    /**
     * Main logic
     * <p>
     * Expected usage:
     * <p>
     * Client can connect by pasting this ngrok link to the browser
     * ------->  https://bc88-2601-642-4c05-2a04-f516-4e00-ac3c-bab.ngrok.io
     * <p>
     * & Setting up ssh tunnel
     * ------->  ssh -L 3300:sql.cs.usfca.edu:3306 bnovakova@stargate.cs.usfca.edu
     *
     * @param args
     */
    public static void main(String[] args) throws Exception {

        try {
            startup(PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method
     *
     * @throws Exception
     */
    public static void startup(int port) throws Exception {

        Gson gson = new Gson();
        Config config = gson.fromJson(new FileReader(configFilename), Config.class);

        Server server = new Server(port);
        System.out.println("Listening on port: " + port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setAttribute(LoginServerConstants.CONFIG_KEY, config);
        context.setAttribute(LoginServerConstants.NGROG, "https://7b99-90-183-14-220.ngrok.io");

        //adding all the servlets
        context.addServlet(LandingServlet.class, "/");
        context.addServlet(LoginSlackServlet.class, "/loginSlack");
        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(HomepageServlet.class, "/homepage");
        context.addServlet(AccountServlet.class, "/account");
        context.addServlet(UpdateAccountServlet.class, "/updates");
        context.addServlet(TransactionsServlet.class, "/transactions");
        context.addServlet(LogoutServlet.class, "/logout");
        context.addServlet(EventServlet.class, "/events");
        context.addServlet(NewEventServlet.class, "/newEvent");
        context.addServlet(TicketServlet.class, "/tickets");
        context.addServlet(SearchServlet.class, "/search");
        context.addServlet(TransferTicket.class, "/transfer");

        server.setHandler(context);
        server.start();
    }
}
