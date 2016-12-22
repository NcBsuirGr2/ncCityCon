package com.citycon.controllers.servlets;

import com.citycon.controllers.ConfirmationHolder;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import javax.mail.*;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Properties;
import java.io.IOException;
import java.util.Calendar;


/**
 * Allows users to signup into the system. On GET returns html page to sign up,
 * on POST try to create new user. On error shows sign up page again with attribute
 * errorType. Default user group is <code>guest</code>. If flag 'enabled' in
 * <code>registration.properties</code> is <code>true</code>, sends confirmation email.
 *
 * @author Tim, Mike
 * @version  2.0
 */
public class SignUpServlet extends AbstractHttpServlet {

    private static final String SIGN_UP_PAGE = "/jsp/security/signUp.jsp";
    private static final String SIGN_UP_URL = "/signup";

    public SignUpServlet() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SignUpServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)  throws ServletException, IOException {
        RequestDispatcher signUpPage = req.getRequestDispatcher(SIGN_UP_PAGE);
        signUpPage.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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

            /*Validation*/
            String validationMessage = validate(user);
            if (validationMessage != null) {
                forwardToErrorPage(validationMessage, req, res);
                return;
            }

            newUser.setEntity(user); 
            try {
                newUser.create();                  
                newUser.read();
                /*If email confirmation required, send confirmation email*/
                try {
                    Properties registrationProperties = new Properties();
                    registrationProperties.load(getClass().getResourceAsStream("/registration.properties"));
                    if ("true".equals(registrationProperties.getProperty("enabled"))) {
                        ConfirmationHolder holder = ConfirmationHolder.getInstance();
                        holder.add(sendConfirmationEmail(newUser.getEntity()), newUser.getEntity());
                        res.sendRedirect("/signup?success=registration");
                        return;
                    }
                } catch (IOException e) {
                    logger.warn("Cannot open registration properties file:", e);
                }

                HttpSession session = req.getSession();
                session.setAttribute("user", newUser.getEntity());
                initializePaginationData(session);
                res.sendRedirect("/");
            } catch(DublicateKeyDAOException exception) {
                res.sendRedirect(getRedirectPathToSamePage("dublicate", req));
            } catch(InvalidDataDAOException exception) {
                res.sendRedirect(getRedirectPathToSamePage("invalidData", req));
            } 
        } catch (DAOException exception) {
            forwardToErrorPage(exception.getMessage(), req, res);             
        } catch (Exception e) {
            logger.warn("Unexpected exception", e);
            forwardToErrorPage(e.getMessage(), req, res);
        }
    }
    private String getRedirectPathToSamePage(String errorType, HttpServletRequest req) {
        StringBuilder redirect = new StringBuilder();
        redirect.append(SIGN_UP_URL);
        redirect.append("?errorType=");
        redirect.append(errorType);
        redirect.append("&login=");
        redirect.append(req.getParameter("login"));
        redirect.append("&email=");
        try {
            redirect.append(URLEncoder.encode(req.getParameter("email"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            logger.warn("The user email has unexpected encoding", e);
            redirect.append(req.getParameter("&email"));
        }
        redirect.append("&name=");
        redirect.append(req.getParameter("name"));
        return redirect.toString();
    }

    /**
     * Sends confirmation email to user. Gets account credentials from <code>registration.properties</code>
     * file. Uses the gmail SMTP service.
     *
     * @param user          user to send email to
     * @return  reglink     null if any error occurs during sending
     */
    private String sendConfirmationEmail(UserEntity user) {
        try {
            Properties emailAccountProperties = new Properties();
            try {
                emailAccountProperties.load(getClass().getResourceAsStream("/registration.properties"));
            } catch (IOException e) {
                logger.warn("Cannot open registration properties file to send email:", e);
                return null;
            }

            final String username = emailAccountProperties.getProperty("username");
            final String password = emailAccountProperties.getProperty("password");

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");

            Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("cityconteam@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
                message.setSubject("Welcome to CityCon!");
                String reglink = RandomStringUtils.random(30, true, true);
                message.setContent("You received this email because you tried to register at <a href=http://citycon.ml>citycon.ml</a>." +
                                " Go <a href=http://citycon.ml/registration?reglink=" + reglink + ">here</a>" +
                                //" or <a href=http://localhost:8080/registration?reglink=" + reglink + "> here </a>" +
                                " to complete your registration." +
                                "<br><br>If you didn't ask for registration, just ignore this message." +
                                "<br><br><hr>With love,<br>your CityCon team.",
                        "text/html; charest=utf-8");
                Transport.send(message);
                logger.trace("Sent reglink {}", reglink);
                return reglink;
            } catch (MessagingException e) {
                logger.warn("Exception during sending email", e);
                return null;
            }
        } catch (Exception e) {
            logger.warn("Unexpected exception", e);
            return null;
        }
    }
}