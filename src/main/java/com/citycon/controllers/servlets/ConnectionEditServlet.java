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
 * @version 0.2
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

					req.setAttribute("connection", connection.getEntity());
				} catch (DAOException cause) {
					logger.warn("Error occured during reading connection", cause);
					forwardToErrorPage("Error occured during reading connection", req, res);
					return;
				}
			} catch (NumberFormatException exception) {
				forwardToErrorPage("Not string id value", req, res);
				return;
			} catch (Exception exception) {
                logger.warn("Unexcepted exception");
                forwardToErrorPage("Internal servler error", req, res);
                return;
            }
		} else if (req.getParameter("SN") != null) {
			ORMRouterConnection connection = new ORMRouterConnection();
			ORMRouter router = new ORMRouter();
			router.setSN(req.getParameter("SN"));
			try {
				router.read();				
				connection.setFirstRouterSN(req.getParameter("SN"));
				connection.setFirstRouterCityName(router.getCityName());
				connection.setFirstRouterCountry(router.getCountryName());
				req.setAttribute("connection", connection.getEntity());
			} catch (DAOException cause) {
				logger.warn("Error occured during reading router to fill connection", cause);
				forwardToErrorPage("Error occured during reading connection", req, res);
				return;
			}
		} else if (req.getParameter("city") != null && req.getParameter("country") != null) {
			ORMRouterConnection connection = new ORMRouterConnection();
			connection.setFirstRouterCityName(req.getParameter("city"));
			connection.setFirstRouterCountry(req.getParameter("country"));
			req.setAttribute("connection", connection.getEntity());			
		}
		
		RequestDispatcher editView = req.getRequestDispatcher(CONNECTION_EDIT_PAGE);
		editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
    	String action = req.getParameter("action");
    	if (action == null) {
    		forwardToErrorPage("action parameter is null", req, res);
    		return;
    	}
    	switch (action) {
    		case "edit" : {
    			doPut(req, res);
    			return;
    		}
    		case "delete" : {
    			doDelete(req, res);
    			return;
    		}
    		default : {
    			try {
	    			String SN1 = req.getParameter("SN1");
	    			String SN2 = req.getParameter("SN2");

	    			ORMRouterConnection newConnection = new ORMRouterConnection();
	    			newConnection.setFirstRouterSN(SN1);
	    			newConnection.setSecondRouterSN(SN2);

	    			try {
	    				newConnection.create();
	    			} catch (DublicateKeyDAOException exception) {
	    				res.sendRedirect(getRedirectPathToSamePage("noFreePorts", req, res).toString());
	    				return;
	    			}
	    		} catch (DAOException exception) {
	    			logger.warn("DAO error during adding new connection", exception);
	    			forwardToErrorPage("Internal DAO exception", req, res);
	    		}
    			res.sendRedirect(CONNECTION_LIST_URL+"?success=add&"+req.getParameter("sameSelect"));
    		}
    	}
    }
    protected void doPut(HttpServletRequest req, HttpServletResponse res) 
    													throws ServletException, IOException {
    	try {
			int connectionId = Integer.parseInt(req.getParameter("id"));
			String SN1 = req.getParameter("SN1");
			String SN2 = req.getParameter("SN2");
			
			ORMRouterConnection updateConnection = new ORMRouterConnection();
			updateConnection.setId(connectionId);
			updateConnection.setFirstRouterSN(SN1);
			updateConnection.setSecondRouterSN(SN2);			
			try {
				updateConnection.update();
			} catch (DublicateKeyDAOException exception) {
				res.sendRedirect(getRedirectPathToSamePage("dublicate", req, res).toString());
				return;
			} catch (InvalidDataDAOException exception) {
				// No routers with such SN, redirect to add/edit page
				res.sendRedirect(getRedirectPathToSamePage("invalidSN", req, res).toString());
				return;
			}catch (DAOException cause) {
				logger.warn("Internal DAO exception", cause);
				forwardToErrorPage("Internal DAO exception", req, res);
			}
		} catch (NumberFormatException exception) {
			forwardToErrorPage("Not string id value", req, res);
			return;
		}
		res.sendRedirect(CONNECTION_LIST_URL+"?success=edit&"+req.getParameter("sameSelect"));
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
				logger.warn("Error occured during deleting connection", cause);
				forwardToErrorPage("Error occured during deleting connection", req, res);
				return;
			}
		} catch (NumberFormatException exception) {
			forwardToErrorPage("Not string id value", req, res);
		}

		res.sendRedirect(CONNECTION_LIST_URL+"?success=delete&"+req.getParameter("sameSelect"));
		return;
	}
	private StringBuilder getRedirectPathToSamePage(String errorType, HttpServletRequest req, HttpServletResponse res) {
        String action = req.getParameter("action");
        String country1 = req.getParameter("country1");
        String country2 = req.getParameter("country2");
        String city1 = req.getParameter("city1");
        String city2 = req.getParameter("city2");
        String SN1 = req.getParameter("SN1");
        String SN2 = req.getParameter("SN2");
        String sameSelect = req.getParameter("sameSelect");
        StringBuilder redirect = new StringBuilder();
        redirect.append(CONNECTION_EDIT_URL);
        redirect.append("?action=");
        redirect.append(action);
        redirect.append("&");
        redirect.append(sameSelect);
        redirect.append("&errorType=");
        redirect.append(errorType);        
        redirect.append("&firstCountry=");
        redirect.append(country1);
        redirect.append("&secondCountry=");
        redirect.append(country2);
        redirect.append("&firstCity=");
        redirect.append(city1);
        redirect.append("&secondCity=");
        redirect.append(city2);
        redirect.append("&SN1=");
        redirect.append(SN1);
        redirect.append("&SN2=");
        redirect.append(SN2);
        return redirect;
    }

}
