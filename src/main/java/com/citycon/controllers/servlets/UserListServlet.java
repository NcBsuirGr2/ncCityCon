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
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

/**
 * Used to show the list of users. Support pagination. Redirects to the last available
 * page if some pagination data is invlaid and redirects to the error page if DAOException occurs.
 * 	
 * @author Mike
 * @version 0.2
 */
public class UserListServlet extends AbstractHttpServlet {
	private static String USER_LIST_PAGE = "/jsp/users/userList.jsp";
	private static String ERROR_PAGE = "/jsp/errors/error.jsp";

	public UserListServlet(){
		super();
		logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.UserListServlet");
	}

	protected void doGet(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {

		try {
			//If page must be normalized (negative or too large)
			if (!setPaginationVariables(ORMUser.getCount(), req, res)) {
				StringBuilder redirect = new StringBuilder();
					redirect.append("/users?page=");
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
			String sortBy = "name";
			if(sortByReq != null && !sortByReq.equals("")) {
				sortBy = sortByReq;
			}

			int page = (int)req.getAttribute("currentPage");
			int itemsPerPage = (int)req.getAttribute("itemsPerPage");
			boolean asc = (boolean)req.getAttribute("asc");

			logger.trace("getPage of users with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
																page, itemsPerPage, sortBy, asc);

			UserEntity[] users = ORMUser.getPage(page, itemsPerPage, sortBy, asc);
			req.setAttribute("entityArray", users);
			req.getRequestDispatcher(USER_LIST_PAGE).forward(req, res);
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
