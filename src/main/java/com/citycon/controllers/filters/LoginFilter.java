package com.citycon.controllers.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpSession;
import java.io.IOException;


import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks if user loggined into system. After login UserEntity object must be
 * created in session scope. If there is no user object on the session scope, 
 * filter should not pass request futher. 
 *
 * @author Mike
 * @version 0.2
 */
public class LoginFilter extends AbstractHttpFilter implements Filter {

	private Logger logger;

	public LoginFilter() {
		logger = LoggerFactory.getLogger("com.citycon.controllers.filters.LoginFilter");
	}

	public void doFilter(ServletRequest req, ServletResponse res, 
					FilterChain chain) throws ServletException, IOException {		

		try {
			HttpServletResponse httpRes = (HttpServletResponse)res;
			HttpServletRequest httpReq = (HttpServletRequest)req;

			HttpSession session = httpReq.getSession(false);
			if ((session == null) || (session.getAttribute("user") == null)) {
				forwardToSecurityErrorPage(httpReq, httpRes);
				return;
			} else {
				UserEntity user = (UserEntity)session.getAttribute("user");

				req.setAttribute("showLogoutBtn", true);

				if (user.getGrant().getUsersBranchLevel() > Grant.NONE) {
					req.setAttribute("showUsersBtn", true);
					
				}
				if (user.getGrant().getUsersBranchLevel() > Grant.READ) {
					req.setAttribute("showUsersOperationBtns", true);					
				}

				if (user.getGrant().getSystemUnitsBranchLevel() > Grant.NONE) {
					req.setAttribute("showConnectionsBtn", true);
					req.setAttribute("showRoutersBtn", true);
					req.setAttribute("showCitiesBtn", true);
				}
				if (user.getGrant().getSystemUnitsBranchLevel() > Grant.READ) {
					req.setAttribute("showSystemUnitsOperationBtns", true);					
				}	

				chain.doFilter(req, res);		
			}
		} catch (ClassCastException e) {
			logger.info("Cannot cast: ", e);
			forwardToErrorPage("Internal server error", req, res);
			return;
		} catch (Exception e) {
			logger.info("Cannot cast: ", e);
			forwardToErrorPage("Internal server error", req, res);
		}
	}
}