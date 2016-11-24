package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
 * @version  1.2
 */
public class SignInServlet extends AbstractHttpServlet {

    private static final String SIGN_IN_PAGE = "/jsp/security/signIn.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher signInPage = req.getRequestDispatcher(SIGN_IN_PAGE);
        signInPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SignInServlet");
        try {
            ORMUser user = new ORMUser();             
            
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));            
            logger.debug("SignIn reqest with login:{} and password:{}", user.getLogin(), user.getPassword());
            try {                 
                user.read();
                HttpSession session = req.getSession();
                session.setAttribute("user", user.getEntity());
                initializePaginData(session);
                res.sendRedirect("/");
            } catch(InvalidDataDAOException exception) {
                res.sendRedirect("/signin?errorType=invalidData");
            } 
        } catch (DAOException exception) {
            // Internal DAOException
            forwardToErrorPage(exception.getMessage(), req, res);             
        }       
    }
}