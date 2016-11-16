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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Checks if user loggined into system. After login UserEntitiy object must be
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
		HttpServletResponse httpRes = (HttpServletResponse)res;
		HttpServletRequest httpReq=(HttpServletRequest)req;
		Logger logger = LoggerFactory.getLogger("com.citycon.controllers.filters");

		try {
			HttpSession session= httpReq.getSession(false);
			if ((session != null)||(httpReq.getSession().getAttribute("user") == null)) {
				logger.info("Access to the secret page");
				forwardToSecurityErrorPage(httpReq, httpRes);
			}
			chain.doFilter(req, res);

			
		} catch (ClassCastException e) {
			logger.info("Access to the secret page");
			forwardToSecurityErrorPage(httpReq, httpRes);
			chain.doFilter(req, res);
			return;
		}
	}

	public void destroy() {
		// clean up
	}
}