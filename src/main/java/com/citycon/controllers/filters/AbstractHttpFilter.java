package com.citycon.controllers.filters;

import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Common abstract filter.
 * 
 * @author Tim, Mike
 * @version 1.1
 */
public abstract class AbstractHttpFilter {
    private static final String ERROR_PAGE = "/jsp/errors/securityError.jsp";

    protected void forwardToSecurityErrorPage(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        RequestDispatcher errorPage =req.getRequestDispatcher(ERROR_PAGE);
        errorPage.forward(req, res);
    }

    protected boolean checkRights(ServletRequest req, 
            int requiredUserRights, int requiredSystemUnitsRights) throws ServletException, IOException {

        boolean access = false;

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpSession session =httpReq.getSession(false);
        if(session == null || session.getAttribute("user") == null) {
            access = false;
        } else {
            try {
                UserEntity user = (UserEntity)session.getAttribute("user");
                int userRights = user.getGrant().getUsersBranchLevel();
                int systemUnitsRights = user.getGrant().getSystemUnitsBranchLevel();
                access =  (userRights >= requiredUserRights && systemUnitsRights >= requiredSystemUnitsRights);
            } catch (ClassCastException exception) {
                access = false;
                Logger logger = LoggerFactory.getLogger("com.citycon.controllers.filters");
                logger.warn("Cannot cast user object to UserEntity", exception);
            }
        }
        return access;
    }
}