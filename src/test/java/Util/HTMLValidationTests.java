package Util;

import cs601.project4.Server.ServerConstants;
import cs601.project4.Servlets.TransactionsServlet;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.HTMLExtra;
import cs601.project4.Util.HTMLValidator;
import org.junit.Test;

import static cs601.project4.MySQL.Tables.TicketsTable.returnTickets;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Server.ServerConstants.returnLogoutPage;
import static cs601.project4.Servlets.AccountServlet.getUserAccount;
import static cs601.project4.Servlets.EventServlet.getEventPageHTMLPost;
import static cs601.project4.Servlets.EventServlet.getEventsHTMLGet;
import static cs601.project4.Servlets.SearchServlet.getEventPageSearchHTML;
import static cs601.project4.Servlets.TransactionsServlet.getUserTransactions;
import static cs601.project4.Servlets.UpdateAccountServlet.getAccountUpdatedHTML;
import static cs601.project4.Util.HTMLExtra.addingPicture;
import static cs601.project4.Util.HTMLExtra.getLoginHTML;
import static org.junit.Assert.assertEquals;


/**
 * HTMLValidationTests Class
 */
public class HTMLValidationTests {


    @Test
    public void testHTMLLandingPage() {

        String landingPage = returnHeader("LogSlack") + addingPicture("https://msmt.org/wp-content/uploads/2021/05/1478813947-tickets.png", 300, 200);
        landingPage += ("<h1>Welcome to TICKETMASTER</h1>\n") + ("<h3><i>Ticket Purchase Web Application</i></h3><br/><br/>\n");
        landingPage += ("<h2>Login with Slack Demo Application</h2>\n");
        landingPage += (ServerConstants.PAGE_FOOTER);
        assertEquals(true, HTMLValidator.isValid(landingPage));
    }

    @Test
    public void testHTMLLoginPage() {
        String login = (returnHeader("Login") + ServerConstants.STYLE + "<h1>You have already been authenticated</h1>" + ServerConstants.PAGE_FOOTER);
        assertEquals(true, HTMLValidator.isValid(login));
    }


    @Test
    public void testHTMLLoginPage2() {
        String login2 = (returnHeader("Login") + ServerConstants.STYLE + "<h1>Oops, login unsuccessful</h1>" + ServerConstants.PAGE_FOOTER);
        assertEquals(true, HTMLValidator.isValid(login2));
    }

    @Test
    public void testHTMLLoginPage3() {
        String login3 = getLoginHTML("barbora");
        assertEquals(true, HTMLValidator.isValid(login3));
    }

    @Test
    public void testHTMLHomepage() {
        String homepage = returnHeader("Homepage") + HTMLExtra.addingPicture("https://blog.hubspot.com/hubfs/homepage-web-design.jpg", 400, 300) + "<h1>" + "barbora" + "</h1>" + Buttons.ACCOUNT_BUTTON + Buttons.LOGOUT_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(homepage));
    }

    @Test
    public void testHTMLAccount() {
        String account = getUserAccount("bnovakova@donc.usfca.edu");
        assertEquals(true, HTMLValidator.isValid(account));
    }

    @Test
    public void testHTMLAccountUpdateGET() {
        String accountButton = Buttons.UPDATE_ACCOUNT_BUTTON;
        assertEquals(true, HTMLValidator.isValid(accountButton));
    }

    @Test
    public void testHTMLAccountUpdatePOST() {
        String account = getAccountUpdatedHTML();
        assertEquals(true, HTMLValidator.isValid(account));
    }

    @Test
    public void testHTMLogout() {

        String logout = returnLogoutPage("barbora");
        assertEquals(true, HTMLValidator.isValid(logout));
    }

    @Test
    public void testHTMLEventGET() {
        String event = getEventsHTMLGet();
        assertEquals(true, HTMLValidator.isValid(event));
    }

    @Test
    public void testHTMLEventPOSTvalid() {
        String event = getEventPageHTMLPost(1);
        assertEquals(true, HTMLValidator.isValid(event));
    }

    @Test
    public void testHTMLEventPOSTnotValid() {
        String event = getEventPageHTMLPost(10000);
        assertEquals(true, HTMLValidator.isValid(event));
    }

    @Test
    public void testHTMLNewEventGET() {
        String newEvent = ServerConstants.NEW_EVENT_HTML + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(newEvent));
    }

    @Test
    public void testHTMLNewEventPOST() {
        String newEvent = ServerConstants.NEW_EVENT_HTML + ServerConstants.EVENT_CREATED + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(newEvent));
    }


    @Test
    public void testHTMLSearch() {
        String search = getEventPageSearchHTML("music", "type");
        System.out.println(search);
        assertEquals(true, HTMLValidator.isValid(search));
    }

    @Test
    public void testHTMLTicket() {
        String ticket = ServerConstants.TICKET_PURCHASED + returnTickets("bnovakova@donc.usfca.edu") + "<br/><br/>" + Buttons.EVENTS_BUTTON + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(ticket));
    }

    @Test
    public void testHTMLTicket2() {
        String ticket = ServerConstants.TICKET_NOT_PURCHASED + "<br/><br/>" + Buttons.EVENTS_BUTTON + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(ticket));
    }

    @Test
    public void testHTMLTransactions() {
        String transactions = getUserTransactions("bnovakova@donc.usfca.edu");
        assertEquals(true, HTMLValidator.isValid(transactions));
    }

    @Test
    public void testHTMLTransfer1() {
        String transfer = TransactionsServlet.getUserTransactions("bnovakova@donc.usfca.edu");
        System.out.println(transfer);
        assertEquals(true, HTMLValidator.isValid(transfer));
    }

    @Test
    public void testHTMLTransfer2() {
        String transfer = returnHeader("Transfer") + Buttons.LANDING_BUTTON + ServerConstants.PAGE_FOOTER;
        assertEquals(true, HTMLValidator.isValid(transfer));
    }

    @Test
    public void testHTMLTransfer3() {
        assertEquals(true, HTMLValidator.isValid(ServerConstants.TICKET_TRANSFERED + Buttons.HOMEPAGE_BUTTON + ServerConstants.PAGE_FOOTER));
    }
}