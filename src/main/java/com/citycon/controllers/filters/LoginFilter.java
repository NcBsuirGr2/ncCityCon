package com.citycon.controllers.filters;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.FilterChain;
import java.io.IOException;

/**
 * Checks if user loggined into system. After login UserEntitiy object must be
 * created in sessionScope. If there is no such object, filter should not pass 
 * request futher.
 *
 * @author Mike
 * @version 0.1
 */
public class LoginFilter implements Filter {
	public void init(FilterConfig config) throws ServletException {
		// init 
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
		try { 

			HttpServletResponse httpRes = (HttpServletResponse)res;
			HttpServletRequest httpReq = (HttpServletRequest)req;

			if (httpReq.getSession().getAttribute("user") == null) {
				//TODO: pretty error page
				httpRes.sendError(403, "You must be logged in to see this page.");
			}else {
				chain.doFilter(req, res);
			}
			
		} catch (ClassCastException e) {
			// nothing to do with no-http req
			// TODO: log no-htpp reqest
			return;
		}
	}

	public void destroy() {
		// clean up
	}
}