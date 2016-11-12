package com.citycon.controllers.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.citycon.dao.mysql.UserDAO;
import com.citycon.model.systemunits.entities.UserEntity;

public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String LIST = "/list.jsp";
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	//	String array[] = {name,login,email};
		
		request.setAttribute("entityClass", "users");
	//	request.setAttribute("entityArray", array);
		
		RequestDispatcher view = request.getRequestDispatcher(LIST);

		view.forward(request, response);
	}
}
