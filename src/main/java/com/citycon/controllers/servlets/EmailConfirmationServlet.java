package com.citycon.controllers.servlets;

import com.citycon.controllers.ConfirmationHolder;
import com.citycon.model.systemunits.entities.UserEntity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EmailConfirmationServlet extends AbstractHttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String reglink = req.getParameter("reglink");
        ConfirmationHolder holder = ConfirmationHolder.getInstance();
        if (reglink != null && holder.contains(reglink)) {
            UserEntity confirmedUser = holder.pop(reglink);
            HttpSession session = req.getSession();
            session.setAttribute("user", confirmedUser);
            initializePaginationData(session);
            res.sendRedirect("/");
        } else {
            // TODO: send bad status
            forwardToErrorPage("Invalid registration link", req, res);
        }
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

       doGet(req, res);
    }

}