package com.citycon.controllers.servlets;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
     *	Aimed to validate and set pagination variables. If there is no parameters
	 *	from request, sets defaults from paginationParameters.	Returns not null
	 *	StringBuilder obj that represents normalized path, if page is negative or
	 *	too large.
     * 
     * @param  itemsNum 				total number of items from DAO
     * @param  paginationParameters 	HashMap of default pagination parameters
     * @param  req 						request object to get pagination variables
     * @param  res 						response object to set correct pagination variables
     * @return redirectPath 			if page number must be normalized
     * @throws NumberFormatException 	if cannot parse page or itemsPerPage strings for int
     */
    protected StringBuilder setPaginationVariables(int itemsNum, HashMap<String, String> paginationParameters,
    					HttpServletRequest req, HttpServletResponse res) throws NumberFormatException {
    	
    	// Request parameters
    	String pageReqString = req.getParameter("page");
		String itemsPerPageReqString = req.getParameter("itemsPerPage");
		String ascReqString = req.getParameter("asc");
		String sortByReqString = req.getParameter("sortBy");
        
		// Default parameters
		int page = Integer.parseInt(paginationParameters.get("page"));
		int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));
        
		// Parsing req parameters, updating pagination variables if
		// req parameters are valid.
		
		if(pageReqString != null && !pageReqString.equals("")) {
			page = Integer.parseInt(pageReqString);
			paginationParameters.put("page", pageReqString);
		}
		if(itemsPerPageReqString != null && !itemsPerPageReqString.equals("")) {
			if (Integer.parseInt(itemsPerPageReqString) != 0) {
				itemsPerPage = Integer.parseInt(itemsPerPageReqString);
				paginationParameters.put("itemsPerPage", itemsPerPageReqString);
			}			
		} 
		if(ascReqString != null && !ascReqString.equals("")) {
			paginationParameters.put("asc", ascReqString);
		}		
		if(sortByReqString != null && !sortByReqString.equals("")) {
            paginationParameters.put("sortBy", sortByReqString);
        }

        // Set default itemsPerPage and redirect
		if (itemsNum <= 0) {
            paginationParameters.put("itemsPerPage", "10");
            StringBuilder redirect = new StringBuilder();
            redirect.append(paginationParameters.get("path"));
            redirect.append("?page="); // normalized page
            redirect.append(paginationParameters.get("page"));
            redirect.append("&sortBy=");
            redirect.append(paginationParameters.get("sortBy"));
            redirect.append("&asc=");
            redirect.append(paginationParameters.get("asc"));
            return redirect;
        }

		// Set default page and redirect
		if(page < 1) {
			paginationParameters.put("page", "1");
			StringBuilder redirect = new StringBuilder();
	        redirect.append(paginationParameters.get("path"));
	        redirect.append("&itemsPerPage=");
	        redirect.append(paginationParameters.get("itemsPerPage"));
	        redirect.append("&sortBy=");
	        redirect.append(paginationParameters.get("sortBy"));
	        redirect.append("&asc=");
	        redirect.append(paginationParameters.get("asc"));
			return redirect;
		}

		int pagesNum = (int)Math.ceil((double)itemsNum / (double)itemsPerPage);	
		// If req page is too big, redirect to the last page
		if (itemsNum <= (page-1)*itemsPerPage) {
			page = itemsNum/itemsPerPage;
			if(itemsNum != page*itemsPerPage) {
				page += 1;
			}
			paginationParameters.put("page", String.valueOf(page));

			StringBuilder redirect = new StringBuilder();
	        redirect.append("/connections?page=");
	        redirect.append(page); // normalized page
	        redirect.append("&itemsPerPage=");
	        redirect.append(paginationParameters.get("itemsPerPage"));
	        redirect.append("&sortBy=");
	        redirect.append(paginationParameters.get("sortBy"));
	        redirect.append("&asc=");
	        redirect.append(paginationParameters.get("asc"));
			return redirect;
		}

		// Setting pagination attributes

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

		return null;
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
            HashMap<String, String> paginationVariables, Set<String> validSortBy) {

        String pageReqString = req.getParameter("page");
        String itemsPerPageReqString = req.getParameter("itemsPerPage");
        String ascReqString = req.getParameter("asc");
        String sortByReqString = req.getParameter("sortBy");

        if (!validateIntString(pageReqString)) {
            return false;
        }

        if (validateIntString(itemsPerPageReqString)) {
            return false;
        }

        if (ascReqString != null && !ascReqString.equals("")) {
            if (!ascReqString.equals("asc") && !ascReqString.equals("desc")) {
                return false;
            }
        }

        if (sortByReqString != null && !sortByReqString.equals("")) {
            if (!validSortBy.contains(sortByReqString)) {
                return false;
            }
        }

        paginationVariables.put("page", pageReqString);
        paginationVariables.put("itemsPerPage", itemsPerPageReqString);
        paginationVariables.put("asc", ascReqString);
        paginationVariables.put("sortBy", sortByReqString);
        return true;
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