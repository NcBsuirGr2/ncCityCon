package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.mysql.RouterDAO;

import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.orm.ORMRouter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;

/**
 * Provides interface to get list of routers. Can return list of all routers
 * or routers for specific city.
 * 
 * @author Mike
 * @version 0.3
 */
public class RouterListServlet  extends AbstractHttpServlet {
    private static String ROUTER_LIST_PAGE = "/jsp/routers/routerList.jsp";
    private String ROUTER_LIST_URL = "/routers";

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
                                                    throws ServletException, IOException {

        RouterEntity[] routers;
        HashMap<String, String> paginationParameters = ((HashMap<String, HashMap<String, String>>)req
                                            .getSession().getAttribute("paginationParameters")).get("routers");

        try {
            if (updatePaginationVariables(req, paginationParameters, ORMRouter.getSortingParameters(), ORMRouter.getCount())) {
                setPaginationBlockVariables(req, paginationParameters, ORMRouter.getCount());
            } else {
                forwardToErrorPage("Invalid search input", req, res);
                return;
            }
            // Getting page for concrete city
            if (req.getParameter("country") != null && req.getParameter("city") != null 
                     && !req.getParameter("country").equals("")  && !req.getParameter("city").equals("")) {
                
                CityEntity city = new CityEntity();
                city.setCountryName(req.getParameter("country"));
                city.setName(req.getParameter("city"));

                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of routers with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);
                routers = ORMRouter.getPage(page, itemsPerPage, sortBy, asc, city);

            // Getting all routers
            } else {
                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of routers with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);
                routers = ORMRouter.getPage(page, itemsPerPage, sortBy, asc);
            }   

            req.setAttribute("entityArray", routers);
            req.getRequestDispatcher(ROUTER_LIST_PAGE).forward(req, res);
        } catch (InvalidDataDAOException | IllegalArgumentException exception) {
            forwardToErrorPage("Invalid search input", req, res);
            logger.debug("Invalid getPage data", exception);
        } catch (DAOException exception) {
            forwardToErrorPage("Internal DAO exception", req, res);
        } catch (Exception exception) {
            logger.warn("Exception", exception);
            forwardToErrorPage("Internal server error", req, res);
        }
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}