package com.citycon.controllers.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

import com.citycon.model.systemunits.orm.ORMRouterConnection;
import com.citycon.model.systemunits.orm.ORMRouter;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;

import org.slf4j.LoggerFactory;

/**
 * Used to perform CRUD operations with connections. On GET returns page for
 * editing or adding new servlet. POST must contain 'type' parameter with values
 * 'add', 'delete' or 'edit'. Redirects to the main connections page on success 
 * POST.
 * 
 * @author Mike
 * @version 0.1
 */
public class ConnectionEditServlet extends AbstractHttpServlet {
	 private static String CONNECTION_LIST_PAGE = "/jsp/connections/connectionList.jsp";	 
    private static String CONNECTION_EDIT_PAGE = "/jsp/connections/connectionEdit.jsp";
    private static String CONNECTION_LIST_URL = "/connections";
    private static String CONNECTION_EDIT_URL = "/connection";

    public ConnectionEditServlet() {
    	logger = LoggerFactory.getLogger("com.citycon.controllers.servlets");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
		if (req.getParameter("id") != null) {
			try {
				int connectionId = Integer.parseInt(req.getParameter("id"));
				try {
					ORMRouterConnection connection = new ORMRouterConnection();
					connection.setId(connectionId);
					connection.read();


					// Temporary hack, waiting for DAO
	    			ORMRouter router1 = new ORMRouter();
	    			ORMRouter router2 = new ORMRouter();

	    			router1.setId(connection.getFirstRouterId());
	    			router2.setId(connection.getSecondRouterId());
	    			try {
	    				router1.read();
	    				router2.read();
	    			} catch (InvalidDataDAOException exception) {
	    				// No routers with such SN, redirect to add/edit page
	    				res.sendRedirect(CONNECTION_EDIT_URL+"?errorType=invalidRouterId");
	    				return;
	    			}
		    		connection.setFirstRouterSN(router1.getSN());
		    		connection.setSecondRouterSN(router2.getSN());
		    		//-------------------


					req.setAttribute("connection", connection.getEntity());
					req.setAttribute("dd", "dd");
					req.getRequestDispatcher(CONNECTION_EDIT_PAGE).forward(req, res);
				} catch (DAOException cause) {
					logger.warn("Error occur during reading connection", cause);
					forwardToErrorPage("Error occur during reading connection", req, res);
					return;
				}
			} catch (NumberFormatException exception) {
				forwardToErrorPage("Not string id value", req, res);
				return;
			}
		}
		
		RequestDispatcher editView = req.getRequestDispatcher(CONNECTION_EDIT_PAGE);
		editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
    	String type = req.getParameter("type");
    	if (type == null) {
    		forwardToErrorPage("type parameter is null", req, res);
    		return;
    	}
    	switch (type) {
    		case "edit" : {
    			doPut(req, res);
    			return;
    		}
    		case "delete" : {
    			doDelete(req, res);
    			return;
    		}
    		default : {
    			String SN1 = req.getParameter("SN1");
    			String SN2 = req.getParameter("SN2");
    			// Temporary hack
    			ORMRouter router1 = new ORMRouter();
    			ORMRouter router2 = new ORMRouter();
    			logger.debug(SN1+"-----"+SN2);
    			router1.setSN(SN1);
    			router2.setSN(SN2);
    			try {
	    			try {
	    				router1.read();
	    				router2.read();
	    			} catch (InvalidDataDAOException exception) {
	    				// No routers with such SN, redirect to add/edit page
	    				res.sendRedirect(CONNECTION_EDIT_URL+"?errorType=invalidSN&SN1="+SN1+"&SN2="+SN2);
	    				return;
	    			} 
	    			ORMRouterConnection newConnection = new ORMRouterConnection();
	    			newConnection.setFirstRouterId(router1.getId());
	    			newConnection.setSecondRouterId(router2.getId());
	    			try {
	    				newConnection.create();
	    			} catch (DublicateKeyDAOException exception) {
	    				res.sendRedirect(CONNECTION_EDIT_URL+"?errorType=noFreePorts&SN1="+SN1+"&SN2="+SN2);
	    				return;
	    			}
	    		} catch (DAOException exception) {
	    			logger.warn("DAO error during adding new connection", exception);
	    			forwardToErrorPage("Internal DAO exception", req, res);
	    		}
    			res.sendRedirect(CONNECTION_LIST_URL+"?success=add");
    		}
    	}
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
    	try {
			int connectionId = Integer.parseInt(req.getParameter("id"));
			String SN1 = req.getParameter("SN1");
			String SN2 = req.getParameter("SN2");
			// Temporary hack
			ORMRouter router1 = new ORMRouter();
			ORMRouter router2 = new ORMRouter();

			router1.setSN(SN1);
			router1.setSN(SN2);
			try {
				try {
					router1.read();
					router2.read();
				} catch (InvalidDataDAOException exception) {
					// No routers with such SN, redirect to add/edit page
					res.sendRedirect(CONNECTION_EDIT_URL+"?action=edit?id="+connectionId+"&errorType=invalidSN");
					return;
				}

				ORMRouterConnection updateConnection = new ORMRouterConnection();
				updateConnection.setId(connectionId);
				updateConnection.setFirstRouterId(router1.getId());
				updateConnection.setSecondRouterId(router2.getId());
			
				try {
					updateConnection.update();
				} catch (DublicateKeyDAOException exception) {
					res.sendRedirect(CONNECTION_EDIT_URL+"?action=edit?id="+connectionId+"?errorType=noFreePorts");
					return;
				}
			}catch (DAOException cause) {
				logger.warn("Internal DAO exception", cause);
				forwardToErrorPage("Internal DAO exception", req, res);
			}
		} catch (NumberFormatException exception) {
			forwardToErrorPage("Not string id value", req, res);
			return;
		}
		res.sendRedirect(CONNECTION_LIST_URL+"?success=add");
	}
	protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
	    try {
			int connectionId = Integer.parseInt(req.getParameter("id"));
			try {
				ORMRouterConnection connection = new ORMRouterConnection();
				connection.setId(connectionId);
				connection.delete();
			} catch (DAOException cause) {
				logger.warn("Error occur during deleting connection", cause);
				forwardToErrorPage("Error occur during deleting connection", req, res);
				return;
			}
		} catch (NumberFormatException exception) {
			forwardToErrorPage("Not string id value", req, res);
		}

		res.sendRedirect(CONNECTION_LIST_URL+"?success=delete");
		return;
		}

}
