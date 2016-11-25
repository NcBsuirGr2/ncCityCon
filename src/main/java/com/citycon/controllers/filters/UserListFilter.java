package com.citycon.controllers.filters;

import com.citycon.model.Grant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import java.io.IOException;

/**
 * Checks if user has enough rights to watch another CityCon users
 * 
 * @author Tim, Mike
 * @version 1,1
 */
public class UserListFilter extends AbstractHttpFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, 
    			FilterChain chain) throws ServletException, IOException {

        if (checkRights(req, Grant.READ, Grant.NONE)) {
            chain.doFilter(req, res);
        } else {
            forwardToSecurityErrorPage(req,res);
            return;
        }
    }
}