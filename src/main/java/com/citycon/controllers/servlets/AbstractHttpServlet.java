package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMException;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static final String ERROR_PAGE = "/error.jsp";

    protected void forwardToErrorPage(String errorMessage, HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher errorPage = req.getRequestDispatcher(ERROR_PAGE);
        req.setAttribute("errorMessage", errorMessage);
        errorPage.forward(req, res);
    }
}