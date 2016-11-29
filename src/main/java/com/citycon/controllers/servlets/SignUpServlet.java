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
import javax.servlet.http.HttpSession;
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
 * @version  1.3
 */
public class SignUpServlet extends AbstractHttpServlet {

    private static final String SIGN_UP_PAGE = "/jsp/security/signUp.jsp";
    private static final String SIGN_UP_URL = "/signup";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher signUpPage = req.getRequestDispatcher(SIGN_UP_PAGE);
        signUpPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        try {
            UserEntity user = new UserEntity();
            user.setLogin(req.getParameter("login"));
            user.setPassword(req.getParameter("password"));
            user.setEmail(req.getParameter("email"));
            user.setName(req.getParameter("name"));
            user.setGroup("guest");
            java.sql.Date timeNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
            user.setCreateDate(timeNow);

            ORMUser newUser = new ORMUser(); 
            newUser.setEntity(user); 
            try {
                newUser.create();                  
                newUser.read();
                HttpSession session = req.getSession();
                session.setAttribute("user", newUser.getEntity());
                initializePaginationData(session);
                res.sendRedirect("/");
            } catch(DublicateKeyDAOException exception) {
                res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res).toString());
            } catch(InvalidDataDAOException exception) {
                res.sendRedirect(getRedirectPathToSamePage("invalidData", req, res).toString());
            } 
        } catch (DAOException exception) {
            // InternalDAOException
            forwardToErrorPage(exception.getMessage(), req, res);             
        }       
    }
    private StringBuilder getRedirectPathToSamePage(String errorType, HttpServletRequest req, HttpServletResponse res) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String name = req.getParameter("name");

        StringBuilder redirect = new StringBuilder();
        redirect.append(SIGN_UP_URL);
        redirect.append("?errorType=");
        redirect.append(errorType);
        redirect.append("&login=");
        redirect.append(login);
        redirect.append("&password=");
        redirect.append(password);
        redirect.append("&email=");
        redirect.append(email);
        redirect.append("&name=");
        redirect.append(name);
        return redirect;
    }
}