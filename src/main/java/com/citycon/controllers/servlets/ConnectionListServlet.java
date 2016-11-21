package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.orm.ORMRouterConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

import org.slf4j.LoggerFactory;

/**
 * Used to show the list of connections. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 *  
 * @author Mike
 * @version 0.1
 */
public class ConnectionListServlet extends AbstractHttpServlet {
    private static String CONNECTION_LIST_PAGE = "/jsp/connections/connectionList.jsp";

    public ConnectionListServlet(){
        super();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.ConnectionListServlet");
    }

    protected void doGet(HttpServletRequest req, 
        HttpServletResponse res) throws ServletException, IOException {
            try {
            //If page must be normalized (negative or too large)
            if (!setPaginationVariables(ORMRouterConnection.getCount(), req, res)) {
                StringBuilder redirect = new StringBuilder();
                    redirect.append("/connecitons?page=");
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

            String sortByReq = req.getParameter("sortBy");
            // TODO: FIX when DAO will completed
            String sortBy = "";
            if(sortByReq != null && !sortByReq.equals("")) {
                sortBy = sortByReq;
            }

            int page = (int)req.getAttribute("currentPage");
            int itemsPerPage = (int)req.getAttribute("itemsPerPage");
            boolean asc = (boolean)req.getAttribute("asc");

            logger.trace("getPage of connections with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
                                                                page, itemsPerPage, sortBy, asc);

            RouterConnectionEntity[] connections = ORMRouterConnection.getPage(page, itemsPerPage, sortBy, asc);
            req.setAttribute("entityArray", connections);
            req.getRequestDispatcher(CONNECTION_LIST_PAGE).forward(req, res);
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
