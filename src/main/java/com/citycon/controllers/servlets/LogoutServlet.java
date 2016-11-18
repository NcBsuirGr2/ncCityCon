package com.citycon.controllers.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Aimed to kill current HttpSession.
 * 
 * @author Mike
 * @veriosn 1.0
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