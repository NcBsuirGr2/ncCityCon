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
 * created in sessionScope. If there is no such object, filter should not pass 
 * request futher.
 *
 * @author Mike
 * @version 0.1
 */
public class LoginFilter extends AbstractHttpFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
		// init 
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws ServletException, IOException {		

				Logger dd = LoggerFactory.getLogger("com.citycon.controllers.filters.LoginFilter");
		try {
			HttpServletResponse httpRes = (HttpServletResponse)res;
			HttpServletRequest httpReq=(HttpServletRequest)req;

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
				if (user.getGrant().getSystemUnitsBranchLevel() > Grant.NONE) {
					req.setAttribute("showConnectionsBtn", true);
					req.setAttribute("showRoutersBtn", true);
					req.setAttribute("showCitiesBtn", true);
				}				
				chain.doFilter(req, res);		
			}
		} catch (ClassCastException e) {
			Logger logger = LoggerFactory.getLogger("com.citycon.controllers.filters.LoginFilter");
			logger.info("Cannot cast: ", e);
			return;
		}
	}

	public void destroy() {
		// clean up
	}
}