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
public class CityListFilter extends AbstractHttpFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        // init
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        checkRights(req, res, 0, 1);
        chain.doFilter(req, res);

    }

    public void destroy() {
        // clean up
    }
}