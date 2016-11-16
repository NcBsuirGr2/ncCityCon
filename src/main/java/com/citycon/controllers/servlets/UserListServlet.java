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


public class UserListServlet extends AbstractHttpServlet {
	private static String USER_LIST_PAGE = "/jsp/users/userList.jsp";
	private static String ERROR_PAGE = "/jsp/errors/error.jsp";

	protected void doGet(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {

		String pageString = req.getParameter("page");
		String itemsPerPageString = req.getParameter("itemsPerPage");
		String sortByString = req.getParameter("sortBy");
		String ascString = req.getParameter("asc");

		int page = 1;
		int itemsPerPage = 15;
		String sortBy = "name";
		boolean asc = true;

		if(pageString != null) {
			page = Integer.parseInt(pageString);
		}
		if(itemsPerPageString != null) {
			itemsPerPage = Integer.parseInt(itemsPerPageString);
		}
		if(sortByString != null) {
			sortBy = sortByString;
		}
		if(ascString != null) {
			asc = ascString.equals("true");
		}
		try {
			UserEntity[] users = ORMUser.getPage(page, itemsPerPage, sortBy, asc);
			req.setAttribute("entityArray", users);
			req.getRequestDispatcher(USER_LIST_PAGE).forward(req, res);
		} catch (InvalidDataDAOException exception) {
			res.sendRedirect("/cityCon/users?errorType=invalidData");
		} catch (DAOException e) {
			//TODO: logging
			req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
		}
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
