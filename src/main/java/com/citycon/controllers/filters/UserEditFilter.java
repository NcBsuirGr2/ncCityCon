package com.citycon.controllers.filters;

import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Checks if user has enough rights to edit another CityCon users.
 * 
 * @author Tim, Mike
 * @version 2.0
 */
public class UserEditFilter extends AbstractHttpFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (checkRights(req, Grant.EDIT, Grant.NONE)) {
            chain.doFilter(req, res);
            return;
        } else {
        	//Allow user to modify himself even if he is not admin
        	try {
        		HttpServletRequest httpReq = (HttpServletRequest) req;
            	HttpSession session = httpReq.getSession(false);
            	if(session != null && session.getAttribute("user") != null) {
		        	UserEntity user = (UserEntity)session.getAttribute("user");
		        	if (user.getLogin().equals(req.getParameter("login")) ||
							user.getId() == Integer.valueOf(req.getParameter("id"))) {
		        		chain.doFilter(req, res);
		        		return;
		        	} 
		        }
        	} catch (Exception e) {
        		Logger logger = LoggerFactory.getLogger("com.citycon.controllers.filters.AbstractHttpFilter");
        		logger.warn("Unexpected exception ", e);
        	}
        	
            forwardToSecurityErrorPage(req,res);
            return;
        }
    }
}