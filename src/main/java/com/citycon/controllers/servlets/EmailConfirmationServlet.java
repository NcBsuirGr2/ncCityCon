package com.citycon.controllers.servlets;

import com.citycon.controllers.ConfirmationHolder;
import com.citycon.model.systemunits.entities.UserEntity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Aimed to delete user from <code>ConfirmationHolder</code> if user provides
 * correct registration link.
 *
 * @author Mike
 * @version 2.0
 */
public class EmailConfirmationServlet extends AbstractHttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String reglink = req.getParameter("reglink");
        ConfirmationHolder holder = ConfirmationHolder.getInstance();
        if (notEmpty(reglink) && holder.contains(reglink)) {
            UserEntity confirmedUser = holder.pop(reglink);
            HttpSession session = req.getSession();
            session.setAttribute("user", confirmedUser);
            initializePaginationData(session);
            res.sendRedirect("/");
        } else {
            forwardToErrorPage("Sorry, your registration link isn't correct", req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
       doGet(req, res);
    }

}