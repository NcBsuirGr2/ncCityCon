package com.citycon.controllers.filters;

import com.citycon.model.Grant;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import java.io.IOException;

/**
 * Checks if user has enought rights to edit such system 
 * enities as cities, routers and connections.
 * 
 * @author Tim, Mike
 * @version 2.0
 */
public class SystemEditFilter extends AbstractHttpFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        if (checkRights(req, Grant.NONE, Grant.EDIT)) {
            chain.doFilter(req, res);
        } else {
            forwardToSecurityErrorPage(req,res);
            return;
        }
    }
}