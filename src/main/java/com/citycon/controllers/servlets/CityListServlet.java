package com.citycon.controllers.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

/**
 * Used to show the list of users. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 *
 * @author Dima
 * @version 0.2
 */
public class CityListServlet extends AbstractHttpServlet {
    private static String CITY_LIST_PAGE = "/jsp/cities/cityList.jsp";
    private String CITY_LIST_URL = "/cities";

    public CityListServlet(){
        super();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.CityListServlet");
    }

    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res) throws ServletException, IOException {



        try {
            //If page must be normalized (negative or too large)
            if (setPaginationVariables(ORMCity.getCount(), "name", CITY_LIST_URL, req, res) != null) {
                StringBuilder redirect = new StringBuilder();
                redirect.append("?page=");
                redirect.append(req.getAttribute("currentPage")); // normalized page
                redirect.append("&itemsPerPage=");
                redirect.append(req.getParameter("itemsPerPage"));
                redirect.append("&sortBy=");
                redirect.append(req.getParameter("sortBy"));
                redirect.append("&asc=");
                redirect.append(req.getParameter("asc"));
                logger.debug("Incorrect page, redirect to the "+redirect.toString());
                res.sendRedirect(redirect.toString());
                return;
            }



            int page = (int)req.getAttribute("currentPage");
            int itemsPerPage = (int)req.getAttribute("itemsPerPage");
            boolean asc = (boolean)req.getAttribute("asc");
            String sortBy = (String)req.getAttribute("sortBy");

            logger.trace("getPage of users with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                    page, itemsPerPage, sortBy, asc);

            CityEntity[] cities = ORMCity.getPage(page, itemsPerPage, sortBy, asc);
            req.setAttribute("entityArray", cities);
            req.getRequestDispatcher(CITY_LIST_PAGE).forward(req, res);
        } catch (InvalidDataDAOException | NumberFormatException exception) {
            forwardToErrorPage("Invalid search input", req, res);
            logger.debug("Invalid getPage data", exception);
        } catch (DAOException exception) {
            forwardToErrorPage("Internal DAO exception", req, res);
        } catch (ClassCastException exception) {
            logger.warn("Cannot cast", exception);
            forwardToErrorPage("Internal server error", req, res);
        }
    }

    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}












































/*
package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;

// Created by dima.
public class CityListServlet extends AbstractHttpServlet {
    private static final long serialVersionUID = 1L;
    private static String CITY_LIST_PAGE = "/jsp/cities/cityList.jsp";
    private static String ERROR_PAGE = "/jsp/error/error.jsp";


    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        //Writer wr = response.getWriter();
        //wr.write("hello");

        CityEntity cities[] = null;
        RequestDispatcher view;
        try {

            //получение данных из БД
            cities = ORMCity.getPage(1,20,"name",true);
            request.setAttribute("entityClass", "cities");
            request.setAttribute("entityArray", cities);

            view = request.getRequestDispatcher(CITY_LIST_PAGE);
        } catch (DAOException e) {
            //TODO: logging
            HttpSession session = request.getSession(true);
            session.setAttribute("error",e.getMessage());
            view = request.getRequestDispatcher(ERROR_PAGE);
            e.printStackTrace();
        }

        view.forward(request, response);

    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
*/