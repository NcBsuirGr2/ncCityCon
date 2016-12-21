package com.citycon.controllers.servlets;

import com.citycon.controllers.ConfirmationHolder;
import com.citycon.controllers.listeners.SessionHolder;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.LoggerFactory;
/**
 * Allows users to signin into the system. On GET returns html page to sign up,
 * on POST tries to create new user. On error shows sign up page again with attribute
 * errorType. If flag 'enabled' in <code>registration.properties</code> is <code>true</code>,
 * checks if user has confirmed his email. If user already has session from another device,
 * associates new session with existed.
 *
 * @author Tim, Mike
 * @version  1.3
 */
public class SignInServlet extends AbstractHttpServlet {

    private static final String SIGN_IN_PAGE = "/jsp/security/signIn.jsp";

    public SignInServlet() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SignInServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                        throws ServletException, IOException {
        RequestDispatcher signInPage = req.getRequestDispatcher(SIGN_IN_PAGE);
        signInPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        UserEntity user = new UserEntity();
        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        ORMUser enteredUser = new ORMUser(); 
        enteredUser.setEntity(user);
        logger.trace("SignIn reqest with login:{} and password:{}", user.getLogin(), user.getPassword());
        try {                 
            enteredUser.read();
            /*Check if email confirmation required*/
            try {
                Properties registrationProperties = new Properties();
                registrationProperties.load(getClass().getResourceAsStream("/registration.properties"));
                if ("true".equals(registrationProperties.getProperty("enabled"))) {
                    if (ConfirmationHolder.getInstance().contains(enteredUser.getEntity())) {
                        res.sendRedirect("/signin?errorType=notConfirmed");
                        return;
                    }
                }
            } catch (IOException e) {
                logger.warn("Cannot open registration properties file:", e);
            }

            HttpSession session = SessionHolder.getUserSession(enteredUser.getEntity().getLogin());
            if (session == null) {
                session = req.getSession();
                session.setAttribute("user", enteredUser.getEntity());
                initializePaginationData(session);
            } else {
                /*Set session cookie if there already was a session*/
                Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
                logger.trace("Added session cookie {} for user {}", session.getId(), enteredUser.getEntity().getLogin());
                sessionCookie.setMaxAge(60*30);
                res.addCookie(sessionCookie);
            }
            res.sendRedirect("/");
        } catch(InvalidDataDAOException exception) {
            res.sendRedirect("/signin?errorType=invalidData");
        } catch (DAOException exception) {
            // Internal DAOException
            forwardToErrorPage(exception.getMessage(), req, res);             
        } catch (Exception e) {
            logger.warn("Unexpected exception ", e);
            forwardToErrorPage(e.getMessage(), req, res);
        }
    }
}