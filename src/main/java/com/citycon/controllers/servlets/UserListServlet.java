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

import java.util.HashMap;

/**
 * Used to show the list of users. Updates pagination variables if need.
 * 	
 * @author Mike, Alex
 * @version 2.0
 */
public class UserListServlet extends AbstractHttpServlet {
	private static String USER_LIST_PAGE = "/jsp/users/userList.jsp";

	public UserListServlet(){
		logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.UserListServlet");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HashMap<String, String> paginationParameters = null;
		UserEntity[] users;
		String search;

		try {
			paginationParameters = ((HashMap<String, HashMap<String, String>>)req
					.getSession().getAttribute("paginationParameters")).get("users");

		} catch (ClassCastException e) {
			logger.warn("Cannot cast paginationParameters to HashMap: ", e);
			forwardToErrorPage(req, res);
		}

		try {
			search = req.getParameter("search");
			if(search == null){
				search = "";
			}

			search = search.trim();

			if (updatePaginationVariables(req, paginationParameters, ORMUser.getSortingParameters(), ORMUser.getCount(search))) {
				setPaginationBlockVariables(req, paginationParameters, ORMUser.getCount(search));
			} else {
				forwardToErrorPage("Invalid search input", req, res);
				return;
			}

			int page = Integer.parseInt(paginationParameters.get("page"));
			int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
			boolean asc = paginationParameters.get("asc").equals("true");
			String sortBy = paginationParameters.get("sortBy");

			logger.trace("getPage of users with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}",
					page, itemsPerPage, sortBy, asc);

			users = ORMUser.getPage(page, itemsPerPage, sortBy, asc, search);

			req.setAttribute("entityArray", users);
			req.getRequestDispatcher(USER_LIST_PAGE).forward(req, res);
		} catch (InvalidDataDAOException | IllegalArgumentException exception) {
			forwardToErrorPage("Invalid search input", req, res);
			logger.trace("Invalid getPage data", exception);
		} catch (DAOException exception) {
			forwardToErrorPage("Internal DAO exception", req, res);
		} catch (ClassCastException exception) {
			logger.warn("Cannot cast", exception);
			forwardToErrorPage("Internal server error", req, res);
		} catch (Exception exception) {
			logger.warn("Unexpected exception", exception);
		}
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
