package com.citycon.controllers.filters;

import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 16.11.16.
 */
public class SystemListFilter extends AbstractHttpFilter implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

         if (checkRights(req, Grant.NONE, Grant.READ)) {
            chain.doFilter(req, res);
        } else {
            forwardToSecurityErrorPage(req,res);
        }

    }
}