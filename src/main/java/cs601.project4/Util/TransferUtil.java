package cs601.project4.Util;

import cs601.project4.MySQL.Tables.TransactionsTable;
import cs601.project4.MySQL.Tables.UsersTable;
import cs601.project4.Server.ServerConstants;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.SessionsTable.getEmailDB;
import static cs601.project4.MySQL.Tables.TicketsTable.*;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Servlets.TicketServlet.isDigit;
import static cs601.project4.Util.HTMLExtra.readBody;

/**
 * TransferUtil Class - helper method for TransferServlet Class/
 */
public class TransferUtil {

    /**
     * this method handleTransferTickets and returns apropriate action that was taken.
     *
     * @param req
     * @return
     * @throws IOException
     */
    public static String handleTransferTickets(HttpServletRequest req) throws IOException {

        StringBuilder sb = new StringBuilder();
        String sessionId = req.getSession(true).getId();
        sb.append(returnHeader("Transfer"));
        String body = readBody(req);
        System.out.println("BODY: " + body);

        if (validateInput(body)) {
            System.out.println("validated!!");
            sb.append(distributeTickets(body, sessionId));

        } else {
            sb.append(ticketsNotTransfered("Not Enough Input!"));
        }
        return sb.toString();
    }

    /**
     * Main logic for Ticket Transfer Servlet. It handles all the cases
     * <p>
     * - first filter if the inputed information by user are correct and the tickets can be transfered
     * <p>
     * - check if the user already has some tickets for the same event then it just approprietly adjusts
     * - check if the user don't have any ticket and user are sending all the ticket they own it just adjust the name in the transaction table
     * - check if the user don't have any ticket and user are sending just some of the tickets then it needs to approprietly adjust
     * - also checks if the user has zero ticket left in the table the row gets deleted.
     * <p>
     * This method returns the String HTML that is display baased on what case was used.
     *
     * @param body
     * @param sessionId
     * @return
     */
    public static String distributeTickets(String body, String sessionId) {

        StringBuilder sb = new StringBuilder();
        String emailToTransfer = getUser(body).replace("%40", "@");
        System.out.println("USER to Transfer Tickets to: " + getUser(body));
        System.out.println("How Many Tickets: " + getTicketNum(body));
        System.out.println("FOR EVENT: " + getEventID(body));

        String emailFromTransfer = getEmailDB(sessionId);
        System.out.println("EmailToTransfer: " + emailToTransfer);
        System.out.println("EmailFromTransfer: " + emailFromTransfer);

        int numTickets = getNumTicketsOwned(emailFromTransfer, Integer.parseInt(getEventID(body)));
        System.out.println("Number Tickets owned " + numTickets);

        if (getUser(body).equals("null") || getTicketNum(body).equals("null") || numTickets < Integer.parseInt(getTicketNum(body))) {
            sb.append(ticketsNotTransfered("Not Valid Input"));
        } else if (!UsersTable.isUserInTable(emailToTransfer)) {
            sb.append(ticketsNotTransfered("Not Valid User Email!"));

        } else if (emailFromTransfer.equals(emailToTransfer)) {
            sb.append(ticketsNotTransfered("Cannot send tickets to myself!"));

        } else if (numTickets == Integer.parseInt(getTicketNum(body))) {
            System.out.println("Same number of Tickets Left & Requested");

            //check if user already has some tickets
            int haveSoFar1 = getNumTicketsOwned(emailToTransfer, Integer.parseInt(getEventID(body)));
            System.out.println("HAVE SO FAR: " + haveSoFar1);
            if (haveSoFar1 > 0) {
                int haveSoFar2 = getNumTicketsOwned(emailFromTransfer, Integer.parseInt(getEventID(body)));
                modifyTickets(emailToTransfer, (haveSoFar1 + Integer.parseInt(getTicketNum(body))), Integer.parseInt(getEventID(body)));
                modifyTickets(emailFromTransfer, (haveSoFar2 - Integer.parseInt(getTicketNum(body))), Integer.parseInt(getEventID(body)));
                deleteTicket();
            } else {
                updateOwnerTicketTable(emailToTransfer, Integer.parseInt(getEventID(body)));
            }
            TransactionsTable.insertNewTransaction(emailToTransfer, Integer.parseInt(getTicketNum(body)), Integer.parseInt(getEventID(body)));
            sb.append(ServerConstants.TICKET_TRANSFERED);

        } else if (numTickets > Integer.parseInt(getTicketNum(body))) {

            System.out.println("Tikets & User Email VALID!!\n HAVE TO UPDATE IN TRANSACTION TABLE");

            //insert new owner
            int haveSoFar = getNumTicketsOwned(emailToTransfer, Integer.parseInt(getEventID(body)));
            System.out.println("haveSoFar" + haveSoFar);
            if (haveSoFar > 0) {
                modifyTickets(emailToTransfer, (haveSoFar + Integer.parseInt(getTicketNum(body))), Integer.parseInt(getEventID(body)));
                modifyTickets(emailFromTransfer, (numTickets - Integer.parseInt(getTicketNum(body))), Integer.parseInt(getEventID(body)));
            } else {
                //insert new Owner
                insertNewOwner(emailToTransfer, Integer.parseInt(getTicketNum(body)), Integer.parseInt(getEventID(body)));
                System.out.println("Insert user " + emailToTransfer + " with " + Integer.parseInt(getTicketNum(body)));

                //modify current one
                System.out.println("Modify user " + emailFromTransfer + " from " + numTickets + " to: " + (numTickets - Integer.parseInt(getTicketNum(body))));
                modifyTickets(emailFromTransfer, (numTickets - Integer.parseInt(getTicketNum(body))), Integer.parseInt(getEventID(body)));
            }
            System.out.println("Ticket insert to Transaction Table");
            TransactionsTable.insertNewTransaction(emailToTransfer, Integer.parseInt(getTicketNum(body)), Integer.parseInt(getEventID(body)));
            sb.append(ServerConstants.TICKET_TRANSFERED);
        }

        return sb.toString();
    }


    /**
     * This method validates if the body has all 3 neccessary parts and returns true if it does and false otherwise.
     *
     * @param body
     * @return
     */
    public static boolean validateInput(String body) {
        String[] split = body.split("&");

        if (split.length == 3) {

            for (int i = 0; i < split.length; i++) {
                String[] split2 = split[1].split("=");
                if (split2.length != 2 || (i == 1 && !isDigit(split2[1]))) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }

    /**
     * This method split body and returns user info from it.
     *
     * @param body
     * @return
     */
    public static String getUser(String body) {
        String[] split = body.split("&");
        String[] split2 = split[0].split("=");
        if (split2.length > 1) {
            return split2[1];
        }
        return "null";
    }

    /**
     * This method split body and returns numTickets from it.
     *
     * @param body
     * @return
     */
    public static String getTicketNum(String body) {
        String[] split = body.split("&");
        String[] split2 = split[1].split("=");
        if (split2.length > 1) {
            return split2[1];
        }
        return "null";
    }


    /**
     * This method split body and returns eventID from it.
     *
     * @param body
     * @return
     */
    public static String getEventID(String body) {
        String[] split = body.split("&");
        String[] split2 = split[2].split("=");
        if (split2.length > 1) {
            return split2[1];
        }
        return "null";
    }

    /**
     * This method returns HTML formated text displaed in the doGet method.
     *
     * @param userEmail
     * @return
     */
    public static String returnHTML(String userEmail) {
        return returnHeader("TransferTickets") +
                ServerConstants.STYLE +
                HTMLExtra.addingPicture("https://www.qualityformationsblog.co.uk/wp-content/uploads/2020/08/Transfer.png", 350, 200) +
                "<h1> Who do you want to transfer ticket to?</h1>" +
                returnTicketsTransfer(userEmail);
    }

    /**
     * This method returns HTML formated text displayed when the tickets are not transfered.
     *
     * @param reason
     * @return
     */
    public static String ticketsNotTransfered(String reason) {
        return returnHeader("TicketsNotPurchased") +
                ServerConstants.STYLE +
                "<h1>Ticket NOT Transfered!</h1>\n" +
                "<h2>" + reason + "</h2>\n" +
                HTMLExtra.addingPicture("https://www.incimages.com/uploaded_files/image/1920x1080/getty_506910700_258966.jpg", 350, 250) + Buttons.TRANSFER_BUTTON;
    }

}
