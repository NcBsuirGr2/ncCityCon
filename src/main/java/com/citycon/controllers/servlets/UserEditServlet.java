package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;


/**
 * Represents REST interface for CRUD operations with users. Assumes, 
 * that user has successfully logged into system.
 * 
 * @author  Mike
 * @version  0.1
 */
public class UserEditServlet extends AbstractHttpServlet {

	private String ERROR_PAGE = "/jsp/error.jsp";
	private String LIST_USERS_PAGE = "/jsp/users/userEdit.jsp";
	private String LIST_USERS_URL = "/users";

	public UserEditServlet(){
		super();
		logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.UserEditServlet");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String userName = req.getParameter("name");
		if (userName != null) {
			try {
				ORMUser user = new ORMUser();
				user.setName(userName);
				user.read();
				req.setAttribute("user", user.getEntity());
			} catch (DAOException cause) {
				logger.warn("Error occur during reading user", cause);
				forwardToErrorPage("Error occur during reading user", req, res);
				return;
			}
		}
		RequestDispatcher editView = req.getRequestDispatcher(LIST_USERS_PAGE);
		editView.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String group = req.getParameter("name");
		Date createDate = new Date(Calendar.getInstance().getTimeInMillis());

		if (name == null || login == null || password == null || group == null) {
			forwardToErrorPage("Not enough info to create new user", req, res);
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
		} catch(DAOException cause) {
			logger.warn("Cannot create new user", cause);
			forwardToErrorPage("Cannot create new user", req, res);
			return;
		}

		res.sendRedirect(LIST_USERS_URL);
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String idString = req.getParameter("id");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String group = req.getParameter("name");

		if (idString == null) {
			forwardToErrorPage("Cannot update user cause id field is empty", req, res);
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
		} catch (DAOException cause) {
			logger.warn("Cannot update user", cause);
			forwardToErrorPage("Cannot update user", req, res);
			return;
		} catch (NumberFormatException cause) {
			forwardToErrorPage("Invalid id string", req, res);
			return;
		}
		
		res.sendRedirect(LIST_USERS_URL);
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String idString = req.getParameter("id");
		String login = req.getParameter("login");
		if (idString == null && login == null) {
			forwardToErrorPage("Cannot delete user cause the id & login fields are empty", req, res);
			return;
		}
		try {
			ORMUser deleteUser = new ORMUser();
			deleteUser.setId(Integer.parseInt(idString));
			deleteUser.setLogin(login);

			deleteUser.delete();
		} catch (DAOException cause) {
			logger.warn("Cannot delete user");
			forwardToErrorPage("Cannot delete user", req, res);
			return;
		} catch (NumberFormatException cause) {
			logger.warn("Invalid id string");
			forwardToErrorPage("Invalid id string", req, res);
			return;
		}
		res.sendRedirect(LIST_USERS_URL);
	}
}