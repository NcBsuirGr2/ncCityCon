package com.citycon.controllers.servlets;

import com.citycon.dao.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMException;
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

/**
 * Allows users to signup into the system. On GET returns html page to sign up,
 * on POST try to create new user. On error shows sign up page again with attribute
 * errorType. 
 *
 * @author Tim, Mike
 * @version  1.1
 */
public class SignUpServlet extends AbstractHttpServlet {

    private static final String SIGN_IN_PAGE = "/signIn.jsp";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher loginPage = req.getRequestDispatcher(SIGN_IN_PAGE);
        loginPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        ORMUser user = null;
        try {
            user = new ORMUser();   //TODO: logging
                                    //TODO: error page
        } catch (ORMException e) {
            forwardToErrorPage(e.getMessage(), req, res);
        }
        Grant grant = new Grant();
        grant.setUsersBranchLevel(1); //может что-то напутано с правами
        java.sql.Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());

        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("E-mail"));
        user.setName(req.getParameter("name"));
        user.setGroup("user");
        user.setCreateDate(timeNow);
        user.setGrant(grant);
        try {
            user.create();
            req.getSession().setAttribute("user", user.getEntity());
            res.sendRedirect("/");
        } catch(ORMException e) {
            forwardToErrorPage(e.getMessage(), req, res);
        }  
        
    }
}