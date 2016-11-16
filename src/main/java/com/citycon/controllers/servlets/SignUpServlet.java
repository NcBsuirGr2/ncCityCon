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
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Allows users to signup into the system. On GET returns html page to sign up,
 * on POST try to create new user. On error shows sign up page again with attribute
 * errorType. 
 *
 * @author Tim, Mike
 * @version  1.1
 */
public class SignUpServlet extends AbstractHttpServlet {

    private static final String SIGN_IN_PAGE = "/jsp/security/signIn.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher loginPage = req.getRequestDispatcher(SIGN_IN_PAGE);
        loginPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        try {
            ORMUser user = new ORMUser();             

            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("E-mail"));
            user.setName(req.getParameter("name"));
            user.setGroup("guest");
            java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
            user.setCreateDate(timeNow);
            try {
                user.create();
                try {                    
                    user.read();
                    req.getSession().setAttribute("user", user.getEntity());
                    res.sendRedirect("/");
                } catch (DAOException exception) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
                    logger.error("Exception during reading user: {}", exception);
                    forwardToErrorPage(exception.getMessage(), req, res);      
                }
            } catch(DAOException exception) {
                Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
                logger.error("Exception during creating user: {}", exception);
                forwardToErrorPage(exception.getMessage(), req, res);
            } 
        } catch (DAOException exception) {
            Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
            logger.error("Exception during instantiating ORMUser: {}", exception);
            forwardToErrorPage(exception.getMessage(), req, res);             
        }       
    }
}