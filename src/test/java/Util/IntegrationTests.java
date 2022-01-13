package Util;

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

import org.eclipse.jetty.http.HttpStatus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * IntegrationTests Class
 */
public class IntegrationTests {
    
    int PORT = 8088;
    Server server = new Server(PORT);

    @Before
    public void init() throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setAttribute(LoginServerConstants.NGROG, "https://bc88-2601-642-4c05-2a04-f516-4e00-ac3c-bab.ngrok.io/");
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


    @Test
    public void test() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/test").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.INTERNAL_SERVER_ERROR_500));
    }


    @Test
    public void testHomepage() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/homepage").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));

    }

    @Test
    public void testLogin() throws Exception {

        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/login").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.OK_200));
    }

    @Test
    public void testLoginSlack() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/loginSlack").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.INTERNAL_SERVER_ERROR_500));
    }

    @Test
    public void testAccount() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/account").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testEvent() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/events").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testNewEvent() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/newEvent").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }


    @Test
    public void testSearch() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/search").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testTicket() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/tickets").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testTransaction() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/transactions").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testUpdates() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/updates").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }


    @Test
    public void testTransfer() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/transfer").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @Test
    public void testLogout() throws Exception {
        HttpURLConnection http = (HttpURLConnection) new URL("http://localhost:" + PORT + "/logout").openConnection();
        http.connect();
        assertThat("Response Code", http.getResponseCode(), is(HttpStatus.BAD_REQUEST_400));
    }

    @After
    public void teardown() throws Exception {
        server.stop();
    }
}
