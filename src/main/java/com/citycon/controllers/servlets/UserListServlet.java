package com.citycon.controllers.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import com.citycon.model.systemunits.orm.ORMException;

public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String LIST = "/list.jsp";
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UserEntity users[] = null;
		try {
			users = ORMUser.getPage(1,3,"name",true);
		} catch (ORMException e) {
			//TODO: logging
		} catch (DAOException e) {
			e.printStackTrace();
			//TODO: error page
		}

		request.setAttribute("entityClass", "users");
		request.setAttribute("entityArray", users);
		
		RequestDispatcher view = request.getRequestDispatcher(LIST);

		view.forward(request, response);
	}
}
