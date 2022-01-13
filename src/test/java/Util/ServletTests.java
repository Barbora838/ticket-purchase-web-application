package Util;

import com.google.gson.Gson;
import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Servlets.LoginServlet;
import cs601.project4.Servlets.NewEventServlet;
import cs601.project4.Servlets.SearchServlet;
import cs601.project4.Servlets.TicketServlet;
import cs601.project4.Util.Config;
import cs601.project4.Util.LoginUtilities;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static cs601.project4.MySQL.Tables.PasswordsTable.verifyDB;
import static cs601.project4.MySQL.Tables.SessionsTable.getNameDB;
import static cs601.project4.Util.PasswordUtil.hashPassword;
import static org.junit.Assert.assertEquals;

/**
 * ServletTests Class
 */
public class ServletTests {

    public static final String HASH = "202cb962ac59075b964b07152d234b70";
    public static final String EMAIL_CORRECT = "Barunka838@gmail.com";
    public static final String EMAIL_WRONG = "Barunka@gmail.com";
    public static final Gson gson = new Gson();
    public static Config config = null;

    static {
        try {
            config = gson.fromJson(new FileReader("config_test.json"), Config.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    // TicketApplication Tests
    // ----------------------------------------------------------------

    @Test
    public void testConfigClientID() {
        assertEquals("test", config.getClient_id());
    }

    @Test
    public void testConfigClientRedirectUrl() {
        assertEquals("test", config.getRedirect_url());
    }

    @Test
    public void testConfigClientSecret() {
        assertEquals("test", config.getClient_secret());
    }


    // HomepageServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testGetNameDBcorrect() {
        assertEquals("Petr", getNameDB("cin7m6jydzys1xbo1mu80cc7p"));
    }

    @Test
    public void testGetNameDBwrong() {
        assertEquals("", getNameDB("fdsgvdfg"));
    }


    // LandingServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testGenerateSlackAuthorizeURL() {
        String url = LoginUtilities.generateSlackAuthorizeURL("2464212157.2675892811617", "1kwya2vts8o61bqqlppz0ikvf", "ec7afc313d2ac59600d663fc6ff1e1cfb3e48e85f0327b152a6e2ae2fb166888", config.getRedirect_url());
        assertEquals("https://slack.com/openid/connect/authorize?response_type=code&scope=openid%20profile%20email&client_id=2464212157.2675892811617&state=1kwya2vts8o61bqqlppz0ikvf&nonce=ec7afc313d2ac59600d663fc6ff1e1cfb3e48e85f0327b152a6e2ae2fb166888&redirect_uri=test", url);
    }

    // LoginServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testHashPassword() {
        String hash = hashPassword("123");
        assertEquals(HASH, hash);
    }

    @Test
    public void testHashPasswordEmpty() {
        String hash = hashPassword("");
        assertEquals("", hash);
    }

    @Test
    public void testVerifyDBTrue() {
        assertEquals(true, verifyDB(EMAIL_CORRECT, HASH));
    }

    @Test
    public void testVerifyDBFalse() {
        assertEquals(false, verifyDB(EMAIL_WRONG, HASH));
    }

    @Test
    public void testVerifyDBEmpty() {
        assertEquals(false, verifyDB(EMAIL_WRONG, ""));
    }


    @Test
    public void testIsUserInTableTrue() {
        assertEquals(true, UsersTable.isUserInTable(EMAIL_CORRECT));
    }

    @Test
    public void testIsUserInTableFalse() {
        assertEquals(false, UsersTable.isUserInTable(EMAIL_WRONG));
    }

    @Test
    public void testCheckBodyTrue() {
        assertEquals(true, LoginServlet.checkBody("username=Barunka838%40gmail.com&pass=123"));
    }

    @Test
    public void testCheckBodyFalse() {
        assertEquals(false, LoginServlet.checkBody("dfdf"));
    }

    @Test
    public void testCheckBodyEmpty() {
        assertEquals(false, LoginServlet.checkBody(""));
    }


    // NewEventServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testCreateEventCorrect() {
        ArrayList<String> list = new ArrayList<>();
        list.add("name='Christmas Market'");
        list.add("type='christmas'");
        list.add("date='2021-12-12'");
        list.add("location='Prague'");
        list.add("price='10'");
        list.add("details='The Prague Christmas Markets are open daily from 27th November 2021 to 6th January 2022, including Christmas Eve, Christmas Day and New Years Day.'");
        list.add("picture='https://image.shutterstock.com/image-photo/beautiful-view-old-town-square-600w-1682707294.jpg'");
        list.add("num_tickets='500'");
        list.add("created_by='Barunka@gmail.com'");
        try {
            assertEquals(list, NewEventServlet.createEvent("name=Christmas+Market&type=christmas&date=2021-12-12&location=Prague&price=10&details=The+Prague+Christmas+Markets+are+open+daily+from+27th+November+2021+to+6th+January+2022%2C+including+Christmas+Eve%2C+Christmas+Day+and+New+Year%27s+Day.&picture=https%3A%2F%2Fimage.shutterstock.com%2Fimage-photo%2Fbeautiful-view-old-town-square-600w-1682707294.jpg&num_tickets=500", EMAIL_WRONG));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateEventWrong() {
        ArrayList<String> list = new ArrayList<>();
        list.add("created_by='Barunka@gmail.com'");
        try {
            assertEquals(list, NewEventServlet.createEvent("", EMAIL_WRONG));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateQueryRight() {

        ArrayList<String> list = new ArrayList<>();
        list.add("name='Christmas Market'");
        list.add("type='christmas'");
        list.add("date='2021-12-12'");
        list.add("location='Prague'");
        list.add("price='10'");
        list.add("details='The Prague Christmas Markets are open daily from 27th November 2021 to 6th January 2022, including Christmas Eve, Christmas Day and New Years Day.'");
        list.add("picture='https://image.shutterstock.com/image-photo/beautiful-view-old-town-square-600w-1682707294.jpg'");
        list.add("num_tickets='500'");
        list.add("created_by='Barunka@gmail.com'");
        String query=NewEventServlet.createQuery(list);

        assertEquals("INSERT INTO events (name, type, date, location, price, details, picture, num_tickets, created_by, sold_tickets) VALUES ('Christmas Market', 'christmas', '2021-12-12', 'Prague', '10', 'The Prague Christmas Markets are open daily from 27th November 2021 to 6th January 2022, including Christmas Eve, Christmas Day and New Years Day.', 'https://image.shutterstock.com/image-photo/beautiful-view-old-town-square-600w-1682707294.jpg', '500', 'Barunka@gmail.com', 0);",query);

    }

    @Test
    public void testCreateQueryEmpty() {

        String query=NewEventServlet.createQuery(new ArrayList<>());
        assertEquals("INSERT INTO events (, sold_tickets) VALUES (, 0);",query );

    }

    @Test
    public void testValidArrayCorrect(){
        ArrayList<String> list = new ArrayList<>();
        list.add("name='Christmas Market'");
        list.add("type='christmas'");
        list.add("date='2021-12-12'");
        list.add("location='Prague'");
        list.add("price='10'");
        list.add("details='The Prague Christmas Markets are open daily from 27th November 2021 to 6th January 2022, including Christmas Eve, Christmas Day and New Year's Day.'");
        list.add("picture='https://image.shutterstock.com/image-photo/beautiful-view-old-town-square-600w-1682707294.jpg'");
        list.add("num_tickets='500'");
        list.add("created_by='Barunka@gmail.com'");
        assertEquals(true, NewEventServlet.validateEventArray(list));
    }

    @Test
    public void testValidArrayWrong(){
        ArrayList<String> list = new ArrayList<>();
        list.add("name='Christmas Market'");
        list.add("type='christmas'");
        list.add("date='2021-12-12'");
        list.add("location='Prague'");
        list.add("price='10'");
        list.add("details='The Prague Christmas Markets are open daily from 27th November 2021 to 6th January 2022, including Christmas Eve, Christmas Day and New Year's Day.'");
        list.add("picture='https://image.shutterstock.com/image-photo/beautiful-view-old-town-square-600w-1682707294.jpg'");
        list.add("num_tickets='500'");
        assertEquals(false, NewEventServlet.validateEventArray(list));
    }

    @Test
    public void testValidArrayEmpty(){
        assertEquals(false, NewEventServlet.validateEventArray(new ArrayList<>()));
    }

    @Test
    public void testNewEventServletIsDigitWrong(){

        assertEquals(false, NewEventServlet.isDigit("one"));
    }

    @Test
    public void testNewEventServletIsDigitCorrect(){

        assertEquals(true, NewEventServlet.isDigit("2343"));
    }

    @Test
    public void testNewEventServletgetIntWrong(){

        assertEquals(0, NewEventServlet.getDigit("one"));
    }

    @Test
    public void testNewEventServletgetDigitCorrect(){

        assertEquals(2, NewEventServlet.getDigit("2"));
    }


    // SearchServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testValidateInputCorrect(){

        String body = "searchType=name&type=music";
        assertEquals(true, SearchServlet.validateInput(body));
    }

    @Test
    public void testValidateInputWrong(){
        String body = "searchType=name&type=";
        assertEquals(false, SearchServlet.validateInput(body));
    }

    @Test
    public void testgetQueryCorrect(){

        String body = "searchType=name&type=music";
        assertEquals("music", SearchServlet.getQuery(body));
    }


    @Test
    public void testgetTypeCorrect(){

        String body = "searchType=name&type=music";
        assertEquals("name", SearchServlet.getTypeSearch(body));
    }


    // TicketServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testCheckTicketAmountWrong(){

        assertEquals(false, TicketServlet.checkTicketAmount(1, 1));
    }

    @Test
    public void testCheckTicketAmountCorrect(){

        assertEquals(true, TicketServlet.checkTicketAmount(2, 1));
    }

    @Test
    public void testIsDigitWrong(){

        assertEquals(false, TicketServlet.isDigit("one"));
    }

    @Test
    public void testIsDigitCorrect(){

        assertEquals(true, TicketServlet.isDigit("2343"));
    }

    @Test
    public void testGetEventIDWrong(){

        assertEquals(0, TicketServlet.getEventID("number=500&event_id="));
    }

    @Test
    public void testGetEventIDCorrect(){

        assertEquals(7, TicketServlet.getEventID("number=500&event_id=7"));
    }

    @Test
    public void testGetNumTicketWrong(){

        assertEquals(0, TicketServlet.getNumTickets("number=&event_id=7"));
    }

    @Test
    public void testGetNumTicketCorrect(){

        assertEquals(500, TicketServlet.getNumTickets("number=500&event_id=7"));
    }

    // UpdateAccountServlet Tests
    // ----------------------------------------------------------------

    @Test
    public void testisUserInTableWrong(){

        assertEquals(false, UsersTable.isUserInTable(EMAIL_WRONG));
    }

    @Test
    public void testisUserInTableCorrect(){

        assertEquals(true, UsersTable.isUserInTable(EMAIL_CORRECT));
    }

}
