package cs601.project4.Servlets;

import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import cs601.project4.Util.HTMLExtra;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.SessionsTable.validateCookieDB;
import static cs601.project4.MySQL.Tables.TransactionsTable.returnTransactions;
import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;

/**
 * Handle requests to /transactions
 */
public class TransactionsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            String userEmail = returnClientEmail(req);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(getUserTransactions(userEmail));
        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Transactions"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }

    /**
     * Returns Helper method that returns the HTML for POST request.
     *
     * @param userEmail
     * @return
     */
    public static String getUserTransactions(String userEmail) {

        StringBuilder sb = new StringBuilder();
        sb.append(returnHeader("Transactions"));
        sb.append(HTMLExtra.addingPicture("https://thumbs.dreamstime.com/z/concept-pos-terminal-payments-systems-financial-transactions-pos-terminal-payments-systems-financial-transactions-hand-100278454.jpg", 200, 150));
        sb.append("<h1>Transactions</h1><br/>\n");
        sb.append(returnTransactions(userEmail));
        sb.append(Buttons.HOMEPAGE_BUTTON);
        sb.append(ServerConstants.PAGE_FOOTER);
        return sb.toString();
    }


}
