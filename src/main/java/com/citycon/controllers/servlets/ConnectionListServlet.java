package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.orm.ORMRouterConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import org.slf4j.LoggerFactory;

/**
 * Used to show the list of connections. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 *  
 * @author Mike
 * @version 1.0
 */
public class ConnectionListServlet extends AbstractHttpServlet {
    private static String CONNECTION_LIST_PAGE = "/jsp/connections/connectionList.jsp";
    private static String CONNECTION_LIST_URL = "/connections";

    public ConnectionListServlet(){
        super();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.ConnectionListServlet");
    }

    protected void doGet(HttpServletRequest req, 
        HttpServletResponse res) throws ServletException, IOException {
        
        RouterConnectionEntity[] connections;
        String search;
        HashMap<String, String> paginationParameters;

        try {
            paginationParameters = ((HashMap<String, HashMap<String, String>>)(req
                                            .getSession().getAttribute("paginationParameters"))).get("connections");
        } catch (ClassCastException e) {
            logger.warn("Cannot cast paginationParameters to HashMap: ", e);
            forwardToErrorPage(req, res);
            return;
        }

        try {
            if(req.getParameter("search") == null){
                search = "";
            }
            else {
                search = req.getParameter("search");
            }

            // Getting page for concrete router
            if (req.getParameter("SN") != null && !req.getParameter("SN").equals("")) {
                RouterEntity router = new RouterEntity();
                router.setSN(req.getParameter("SN"));

                if (updatePaginationVariables(req, paginationParameters,
                        ORMRouterConnection.getSortingParameters(), ORMRouterConnection.getCount(search, router))) {
                    setPaginationBlockVariables(req, paginationParameters, ORMRouterConnection.getCount(search, router));
                } else {
                    forwardToErrorPage("Invalid search input", req, res);
                    return;
                }

                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc, search, router);
                
            // Getting page for concrete city
            } else if (req.getParameter("country") != null && req.getParameter("city") != null 
                     && !req.getParameter("country").equals("")  && !req.getParameter("city").equals("")) {
                
                CityEntity city = new CityEntity();
                city.setCountryName(req.getParameter("country"));
                city.setName(req.getParameter("city"));

                if (updatePaginationVariables(req, paginationParameters,
                        ORMRouterConnection.getSortingParameters(), ORMRouterConnection.getCount(search, city))) {
                    setPaginationBlockVariables(req, paginationParameters, ORMRouterConnection.getCount(search, city));
                } else {
                    forwardToErrorPage("Invalid search input", req, res);
                    return;
                }

                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc, search, city);

            // Getting all connections
            } else {
                if (updatePaginationVariables(req, paginationParameters,
                        ORMRouterConnection.getSortingParameters(), ORMRouterConnection.getCount(search))) {
                    setPaginationBlockVariables(req, paginationParameters, ORMRouterConnection.getCount(search));
                } else {
                    forwardToErrorPage("Invalid search input", req, res);
                    return;
                }

                int page = Integer.parseInt(paginationParameters.get("page"));
                int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
                boolean asc = paginationParameters.get("asc").equals("true");
                String sortBy = paginationParameters.get("sortBy");

                logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

                connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc, search);
            }   

            req.setAttribute("entityArray", connections);
            req.getRequestDispatcher(CONNECTION_LIST_PAGE).forward(req, res);

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
    
    protected void doPost(HttpServletRequest req,
            HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
