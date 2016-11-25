package com.citycon.controllers.filters;

import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.FilterConfig;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Common abstract filter. Pvovied several methods for all filters in app.
 * 
 * @author Tim, Mike
 * @version 1.3
 */
public abstract class AbstractHttpFilter {
    private static final String ERROR_PAGE = "/jsp/errors/error.jsp";
    private static final String SECURITY_ERROR_PAGE = "/jsp/errors/securityError.jsp";
    private Logger logger;

    public void init(FilterConfig config) throws ServletException {
        //configuration
    }

    public AbstractHttpFilter() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.filters.AbstractHttpFilter");
    }

    protected void forwardToSecurityErrorPage(ServletRequest req, 
                ServletResponse res) throws ServletException, IOException {

        req.getRequestDispatcher(SECURITY_ERROR_PAGE).forward(req, res);
        return;
    }

    protected void forwardToErrorPage(String errorMessage, ServletRequest req,
                        ServletResponse res) throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
        return;
    }

    /**
     * Used to compare required and current grant levels. Current grants lelel is
     * obtaining form the user object on the session scope.
     * 
     * @param  req                       used to obtain UserEnity from Session
     * @param  requiredUserRights        constant from Grant class
     * @param  requiredSystemUnitsRights constant from Grant class
     * @return access                    if assotiated with this session user has required grats
     * @throws NullPointerException      [If 
     */
    protected boolean checkRights(ServletRequest req, int requiredUserRights, 
                                                        int requiredSystemUnitsRights) {

        boolean access = false;
        try {
            HttpServletRequest httpReq = (HttpServletRequest) req;
            HttpSession session = httpReq.getSession(false);
            if(session == null || session.getAttribute("user") == null) {
                access = false;
            } else {
                try {
                    UserEntity user = (UserEntity)session.getAttribute("user");
                    int userRights = user.getGrant().getUsersBranchLevel();
                    int systemUnitsRights = user.getGrant().getSystemUnitsBranchLevel();
                    access  =   ((userRights >= requiredUserRights) 
                        && (systemUnitsRights >= requiredSystemUnitsRights));
                } catch (ClassCastException exception) {                    
                    logger.warn("Cannot cast user object to UserEntity", exception);
                } catch (NullPointerException e) {
                    logger.warn("Grant is null", e);
                }
            }
        } catch (ClassCastException e) {
            logger.info("No-http request", e);
        } catch (Exception e) {
            logger.info("Unexcpeted exception", e); 
        }
        return access;
    }

    public void destroy() {
        //clean up
    }
}