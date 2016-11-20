package com.citycon.controllers.servlets;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
}