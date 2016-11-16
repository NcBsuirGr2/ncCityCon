package com.citycon.controllers.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import com.citycon.model.systemunits.orm.ORMException;

public class UserListServlet extends AbstractHttpServlet {
	private static final long serialVersionUID = 1L;
	private static String LIST = "/admin.jsp";
	private static String ERROR = "/error.jsp";

	protected void doGet(HttpServletRequest request, 
		HttpServletResponse response) throws ServletException, IOException {
		
		UserEntity users[] = null;
		RequestDispatcher view;
		try {
			users = ORMUser.getPage(1,20,"name",true);
			request.setAttribute("entityClass", "users");
			request.setAttribute("entityArray", users);

			view = request.getRequestDispatcher(LIST);
		} catch (ORMException e) {
			//TODO: logging
			HttpSession session = request.getSession(true);
			session.setAttribute("error",e.getMessage());
			view = request.getRequestDispatcher(ERROR);
			e.printStackTrace();
		}
		/////delete me



		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
