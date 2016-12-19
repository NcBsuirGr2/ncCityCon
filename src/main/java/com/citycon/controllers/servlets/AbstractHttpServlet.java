package com.citycon.controllers.servlets;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;

import java.util.Set;

import java.io.IOException;
import java.util.HashMap;

/**
 * Common abstract servlet. Provides several methods for all servlets in the app.
 * 
 * @author Mike
 * @version 1.0
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static final String ERROR_PAGE = "/jsp/errors/error.jsp";

    protected Logger logger;

    public AbstractHttpServlet() {
    	logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AbstractHttpServlet");
    }

	/**
	 * Forwards to common error page with specific error message
	 *
	 * @param errorMessage			error message to show
	 * @param req					request object to forward
	 * @param res					response object to forward
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void forwardToErrorPage(String errorMessage, HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
    }

	/**
	 * Forwards to common error page with default error message
	 *
	 * @param req					request object to forward
	 * @param res					response object to forward
	 * @throws ServletException
	 * @throws IOException
	 */
    protected void forwardToErrorPage(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("errorMessage", "Internal server error");
        req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
    }

    /**
     * Validates pagination variables and updates them on success validation.
     *
     * @param req                       request object to get variables
     * @param paginationVariables       current pagination variables
     * @param validSortBy               <code>Set</code> of valid sortBy values
     * @return validPaginationVariables true on success validation
     */
    protected boolean updatePaginationVariables(HttpServletRequest req,
            		HashMap<String, String> paginationVariables, Set<String> validSortBy, int itemsCount) {

        String pageReqString = req.getParameter("page");
        String itemsPerPageReqString = req.getParameter("itemsPerPage");
        String ascReqString = req.getParameter("asc");
        String sortByReqString = req.getParameter("sortBy");

		int itemsPerPage = Integer.parseInt(paginationVariables.get("itemsPerPage"));

		if (itemsPerPageReqString != null && !itemsPerPageReqString.equals("")) {
			if (validateIntString(itemsPerPageReqString)) {
				itemsPerPage = Integer.parseInt(itemsPerPageReqString);
				if (itemsPerPage > 1) {
					paginationVariables.put("itemsPerPage", itemsPerPageReqString);
				} else {
					logger.trace("itemsPerPage is less then 1");
					return false;
				}
			} else {
				logger.trace("itemsPerPage is not a string");
				return false;
			}
		}

		if (pageReqString != null && !pageReqString.equals("")) {
			if (validateIntString(pageReqString)) {
				int page = Integer.parseInt(pageReqString);
				int totalPagesNum = 1;
				if (itemsCount > 0) {
					totalPagesNum = (int)Math.ceil(itemsCount / (double)itemsPerPage);
				}
				if (page > 0 && page <= totalPagesNum) {
					paginationVariables.put("page", pageReqString);
				} else {
					logger.trace("Invalid page num: too big(greater then {}) or less then 1", totalPagesNum);
					return false;
				}
			} else {
				logger.trace("page is not a string");
				return false;
			}
		}

        if (ascReqString != null && !ascReqString.equals("")) {
            if (ascReqString.equals("true") || ascReqString.equals("false")) {
				paginationVariables.put("asc", ascReqString);
            } else {
				logger.trace("Invalid asc: not true nor false");
            	return false;
			}
        }

        if (sortByReqString != null && !sortByReqString.equals("")) {
            if (validSortBy.contains(sortByReqString)) {
				paginationVariables.put("sortBy", sortByReqString);
            } else {
				logger.trace("Invalid sort by: {}", sortByReqString);
            	return false;
			}
        }
        return true;
    }

	/**
	 * Sets variables for jsp pagination block. Throws exception on errors.
	 *
	 * @param req						request object to set pagination data
	 * @param paginationVariables		map to get pagination data for calculations
	 * @param itemsCount				need to calculate total pages num
	 */
    public void setPaginationBlockVariables(HttpServletRequest req, HashMap<String,
												String> paginationVariables, int itemsCount) {

		String pageReqString = paginationVariables.get("page");
		String itemsPerPageReqString = paginationVariables.get("itemsPerPage");


		int page = Integer.parseInt(pageReqString);
		int itemsPerPage = Integer.parseInt(itemsPerPageReqString);
		int pagesNum = 1;
		if (itemsCount > 0) {
			pagesNum = (int)Math.ceil(itemsCount / (double)itemsPerPage);
		}
		if (page < 1) throw new IllegalArgumentException("required page is lesser then 1");
		if (page > pagesNum) throw new IllegalArgumentException("required page is greater than it can be");

		int PAGINATION_BUTTONS_NUM = 10;

		int currentPage = page;

		int beginPage = (page/PAGINATION_BUTTONS_NUM)*PAGINATION_BUTTONS_NUM;
		int endPage = beginPage+PAGINATION_BUTTONS_NUM-1;
		if(beginPage < 1) beginPage = 1;
		if(endPage > pagesNum) endPage = pagesNum;

		int previousPage = currentPage-PAGINATION_BUTTONS_NUM;
		int nextPage = currentPage+PAGINATION_BUTTONS_NUM;
		if(previousPage < 1) previousPage = 1;
		if(nextPage > pagesNum) nextPage = pagesNum;

		req.setAttribute("currentPage", currentPage);
		req.setAttribute("beginPage", beginPage);
		req.setAttribute("endPage", endPage);
		req.setAttribute("previousPage", previousPage);
		req.setAttribute("nextPage", nextPage);
	}
    /**
     * Aimed to initialize pagination data. Pagination data is represented as
     * <code>HasMap</code> of <code>HapMaps</code>. Each of nested <code>HasMap</code>
     * represents pagination data for one app html table.
     * 
     * @param session           object to set data
     */
    protected void initializePaginationData(HttpSession session) {
    	HashMap<String, HashMap<String, String>> paginationParameters = new HashMap<>();

    	HashMap<String, String> defaultUsersParameters = new HashMap<>();
    	defaultUsersParameters.put("path", "/users");
    	defaultUsersParameters.put("page", "1");
    	defaultUsersParameters.put("itemsPerPage", "10");
    	defaultUsersParameters.put("sortBy", "name");
    	defaultUsersParameters.put("asc", "true");

    	HashMap<String, String> defaultCitiesParameters = new HashMap<>();
    	defaultCitiesParameters.put("path", "/cities");
    	defaultCitiesParameters.put("page", "1");
    	defaultCitiesParameters.put("itemsPerPage", "10");
    	defaultCitiesParameters.put("sortBy", "name");
    	defaultCitiesParameters.put("asc", "true");

    	HashMap<String, String> defaultRoutersParameters = new HashMap<>();
    	defaultRoutersParameters.put("path", "/routers");
    	defaultRoutersParameters.put("page", "1");
    	defaultRoutersParameters.put("itemsPerPage", "10");
    	defaultRoutersParameters.put("sortBy", "SN");
    	defaultRoutersParameters.put("asc", "true");

    	HashMap<String, String> defaultConnectionsParameters = new HashMap<>();
    	defaultConnectionsParameters.put("path", "/connections");
    	defaultConnectionsParameters.put("page", "1");
    	defaultConnectionsParameters.put("itemsPerPage", "10");
    	defaultConnectionsParameters.put("sortBy", "SN1");
    	defaultConnectionsParameters.put("asc", "true");

    	paginationParameters.put("users", defaultUsersParameters);
    	paginationParameters.put("cities", defaultCitiesParameters);
    	paginationParameters.put("routers", defaultRoutersParameters);
    	paginationParameters.put("connections", defaultConnectionsParameters);

    	session.setAttribute("paginationParameters", paginationParameters);
    }

    /**
     * Validates object using java validation api. Returns not null constrain
     * message if validate fails.
     *
     * @param validateObj           object to validate
     * @param groups                any object groups to check
     * @return constraintMessage    not null if validation fails
     */
    protected <T> String validate(T validateObj, Class<?>... groups) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(validateObj, groups);

        String constraintMessage = null;
        for (ConstraintViolation<T> violation : violations) {
            logger.debug("Violation during validation: {}", violation.getMessage());
            if (constraintMessage == null ) {
              constraintMessage = violation.getMessage();
            }            
        }
        return constraintMessage;
    }
    protected boolean notEmpty(String stringToValidate) {
    	return (stringToValidate != null && !stringToValidate.trim().equals(""));
	}

    private boolean validateIntString(String intString) {
        if(intString != null && !intString.equals("")) {
            try {
                if (Integer.parseInt(intString) < 0) {
                    return false;
                }
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}