package com.citycon.controllers.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.citycon.dao.mysql.MySQLDAOConnection;
import com.citycon.dao.exceptions.DAOException;

/**
 * 
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static final String ERROR_PAGE = "/jsp/errors/error.jsp";

    protected Logger logger;

    protected void forwardToErrorPage(String errorMessage, HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher errorPage = req.getRequestDispatcher(ERROR_PAGE);
        req.setAttribute("errorMessage", errorMessage);
        errorPage.forward(req, res);
    }
    public void destroy() {
    	logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AbstractHttpServlet");
    	logger.info("Called AbstractHttpServlet destroy method. Trying to close MySQLDAOConnection");
    	try {
    		MySQLDAOConnection.close();
    	} catch (DAOException e) {
    		logger.warn("Cannot close MySQLDAOConnection in AbstractHttpServlet method", e);
    	}
    }
}