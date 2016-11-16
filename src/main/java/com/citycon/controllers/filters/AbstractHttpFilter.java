package com.citycon.controllers.filters;

import javax.servlet.*;
import java.io.IOException;


/**
 * 
 */
public abstract class AbstractHttpFilter {
    private static final String ERROR_PAGE = "/jsp/errors/securityError.jsp";

    protected void forwardToSecurityErrorPage(ServletRequest req, ServletResponse res) {
        RequestDispatcher errorPage =req.getRequestDispatcher(ERROR_PAGE);
        try {
            errorPage.forward(req, res);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}