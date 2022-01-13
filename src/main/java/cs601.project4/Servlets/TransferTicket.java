package cs601.project4.Servlets;


import cs601.project4.Server.LoginServerConstants;
import cs601.project4.Server.ServerConstants;
import cs601.project4.Util.Buttons;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static cs601.project4.MySQL.Tables.SessionsTable.*;

import static cs601.project4.Server.ServerConstants.returnHeader;
import static cs601.project4.Util.HTMLExtra.returnClientEmail;
import static cs601.project4.Util.TransferUtil.handleTransferTickets;
import static cs601.project4.Util.TransferUtil.returnHTML;

/**
 * Handle requests to /transfer
 */
public class TransferTicket extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (validateCookieDB(req.getSession(true).getId(), (String) req.getSession().getAttribute(LoginServerConstants.COOKIE))) {
            String userEmail = returnClientEmail(req);
            resp.setStatus(HttpStatus.OK_200);
            resp.getWriter().println(returnHTML(userEmail));
            resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        } else {
            resp.setStatus(HttpStatus.BAD_REQUEST_400);
            resp.getWriter().println(returnHeader("Transfer"));
            resp.getWriter().println(Buttons.LANDING_BUTTON);
            resp.getWriter().println(ServerConstants.PAGE_FOOTER);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setStatus(HttpStatus.OK_200);
        resp.getWriter().println(handleTransferTickets(req));
        resp.getWriter().println("<br/><br/><br/><br/>");
        resp.getWriter().println(Buttons.HOMEPAGE_BUTTON);
        resp.getWriter().println(ServerConstants.PAGE_FOOTER);
    }
}
