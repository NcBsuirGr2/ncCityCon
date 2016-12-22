package com.citycon.controllers.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Allows user to logout.
 * 
 * @author Mike
 * @veriosn 2.0
 */
public class LogoutServlet extends AbstractHttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {

		HttpSession currentSession = req.getSession(false);
		if(currentSession != null) {
			currentSession.invalidate();
		}
		res.sendRedirect("/");
		return;
	}
}