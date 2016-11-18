package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listens the root path of application. Can forward to the index.jsp if user has 
 * not singed in and to the user working page if user has already singed in.
 *
 * @author Mike
 * @version  1.1
 */
public class RootServlet extends AbstractHttpServlet {

    private static final String INDEX_PAGE = "/index.jsp";
    private static final String ADMIN_HOME = "/users";
    private static final String GUEST_OPERATOR_HOME = "/cities";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {

        HttpSession session = req.getSession(false);      

        if(session == null || session.getAttribute("user") == null) {
            req.getRequestDispatcher(INDEX_PAGE).forward(req, res);
        } else {          
            try {
                UserEntity user = (UserEntity)session.getAttribute("user");
                if (user.getGroup().equals("admin")) {
                    res.sendRedirect(ADMIN_HOME);
                } else {
                    res.sendRedirect(GUEST_OPERATOR_HOME);
                }
            } catch (NullPointerException | ClassCastException e) {          
                Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.RootServlet");      
                logger.warn("Error during getting user from session ", e);
                forwardToErrorPage("Internal server error", req, res);
            }
            
        }
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
       this.doGet(req, res);
    }
}