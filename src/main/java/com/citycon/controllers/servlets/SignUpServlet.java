package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;


/**
 * Allows users to signup into the system. On GET returns html page to sign up,
 * on POST try to create new user. On error shows sign up page again with attribute
 * errorType. 
 *
 * @author Tim, Mike
 * @version  1.1
 */
public class SignUpServlet extends AbstractHttpServlet {

    private static final String SIGN_UP_PAGE = "/jsp/security/signUp.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher signUpPage = req.getRequestDispatcher(SIGN_UP_PAGE);
        signUpPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        try {
            ORMUser user = new ORMUser();             

            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("e-mail"));
            user.setName(req.getParameter("name"));
            user.setGroup("guest");
            java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
            user.setCreateDate(timeNow);

            try {
                user.create();                  
                user.read();
                req.getSession().setAttribute("user", user.getEntity());
                res.sendRedirect("/");
            } catch(DublicateKeyDAOException exception) {
                res.sendRedirect("/signin?errorType=dublicate");
            } catch(InvalidDataDAOException exception) {
                res.sendRedirect("/signin?errorType=invalidData");
            } 
        } catch (DAOException exception) {
            // InternalDAOException
            forwardToErrorPage(exception.getMessage(), req, res);             
        }       
    }
}