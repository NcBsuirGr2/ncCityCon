package com.citycon.controllers.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

import com.citycon.model.systemunits.orm.ORMUser;
import com.citycon.model.systemunits.orm.ORMException;


/**
 * Represents REST interface for CRUD operations with users. Assumes, 
 * that user has successfully logged into system.
 * 
 * @author  Mike
 * @version  0.1
 */
public class UserEditServlet extends HttpServlet {

	private String ERROR_PAGE = "/error.jsp";
	private String LIST_USERS_PAGE = "/userEdit.jsp";
	private String LIST_USERS_URL = "/cityCon/app/users";

	public void doGet(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String userName = req.getParameter("name");
		if (userName != null) {
			try {
				ORMUser user = new ORMUser();
				user.setName(userName);
				user.read();
				req.setAttribute("user", user.getEntity());
			} catch (ORMException cause) {
				//TODO: logging
				showErrorPage("Error occur during reading user", req, res);
				return;
			}
		}
		RequestDispatcher editView = req.getRequestDispatcher(LIST_USERS_PAGE);
		editView.forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String group = req.getParameter("name");
		Date createDate = new Date(Calendar.getInstance().getTimeInMillis());

		if (name == null || login == null || password == null || group == null) {
			showErrorPage("Not enough info to create new user", req, res);
			return;
		}

		try {
			ORMUser newUser = new ORMUser();
			newUser.setName(name);
			newUser.setLogin(login);
			newUser.setPassword(password);
			newUser.setEmail(email);
			newUser.setGroup(group);
			newUser.setCreateDate(createDate);

			newUser.create();
		} catch(ORMException cause) {
			//TODO: logging
			showErrorPage("Cannot create new user", req, res);
			return;
		}

		res.sendRedirect(LIST_USERS_URL);
	}

	public void doPut(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String idString = req.getParameter("id");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String group = req.getParameter("name");

		if (idString == null) {
			showErrorPage("Cannot update user cause id field is empty", req, res);
			return;
		}

		try {
			ORMUser updateUser = new ORMUser();
			updateUser.setId(Integer.parseInt(idString));
			updateUser.setName(name);
			updateUser.setLogin(login);
			updateUser.setPassword(password);
			updateUser.setEmail(email);
			updateUser.setGroup(group);

			updateUser.update();
		} catch (ORMException cause) {
			//TODO: logging
			showErrorPage("Cannot update user", req, res);
			return;
		} catch (NumberFormatException cause) {
			showErrorPage("Invalid id string", req, res);
			return;
		}
		
		res.sendRedirect(LIST_USERS_URL);
	}

	public void doDelete(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String idString = req.getParameter("id");
		String login = req.getParameter("login");
		if (idString == null && login == null) {
			showErrorPage("Cannot delete user cause the id & login fields are empty", req, res);
			return;
		}
		try {
			ORMUser deleteUser = new ORMUser();
			deleteUser.setId(Integer.parseInt(idString));
			deleteUser.setLogin(login);

			deleteUser.delete();
		} catch (ORMException cause) {
			showErrorPage("Cannot delete user", req, res);
			return;
		} catch (NumberFormatException cause) {
			showErrorPage("Infalid id string", req, res);
			return;
		}
		res.sendRedirect(LIST_USERS_URL);
	}

	private void showErrorPage(String message, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setAttribute("errorMessage", message);
		RequestDispatcher errorPage = req.getRequestDispatcher(ERROR_PAGE);
		errorPage.forward(req, res);
	}
}