package com.citycon.controllers.filters;

import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 
 */
public abstract class AbstractHttpFilter {
    private static final String ERROR_PAGE = "/jsp/errors/securityError.jsp";

    protected void forwardToSecurityErrorPage(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        RequestDispatcher errorPage =req.getRequestDispatcher(ERROR_PAGE);
        errorPage.forward(req, res);
    }

    protected boolean checkRights(ServletRequest req, ServletResponse res, int userRights, int systemUnitsRights) throws ServletException, IOException {
        HttpServletResponse httpRes = (HttpServletResponse) res;
        HttpServletRequest httpReq = (HttpServletRequest) req;
        UserEntity user = (UserEntity)httpReq.getSession().getAttribute("user");
        boolean acces =  ((user.getGrant().getSystemUnitsBranchLevel() >= systemUnitsRights) ||
                (user.getGrant().getUsersBranchLevel()>=userRights));
        return acces;
    }
}