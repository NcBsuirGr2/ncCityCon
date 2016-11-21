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

    protected void doGet(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
    	req.getRequestDispatcher(CONNECTION_EDIT_PAGE).forward(req, res);
		if (req.getParameter("id") != null) {
			try {
				int connectionId = Integer.parseInt(req.getParameter("id"));
				try {
					ORMRouterConnection connection = new ORMRouterConnection();
					connection.setId(connectionId);
					connection.read();
					req.setAttribute("editConnection", connection.getEntity());
				} catch (DAOException cause) {
					logger.warn("Error occur during reading connection", cause);
					forwardToErrorPage("Error occur during reading connection", req, res);
					return;
				}
			} catch (NumberFormatException exception) {
				forwardToErrorPage("Not string id value", req, res);
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
    	}
    	switch (type) {
    		case "edit" : {

    			return;
    		}
    		case "delete" : {

    			return;
    		}
    		default : {
    			String SN1 = req.getParameter("SN1");
    			String SN2 = req.getParameter("SN2");

    			ORMRouter router1 = new ORMRouter();
    			ORMRouter router2 = new ORMRouter();

    			router1.setSN(SN1);
    			router1.setSN(SN2);
    		}
    	}
    }
}
