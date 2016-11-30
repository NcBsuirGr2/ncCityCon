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
 * Common abstract servlet. Provides several methods for all servlets in app
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

    protected void forwardToErrorPage(String errorMessage, HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("errorMessage", errorMessage);
        req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
    }
    protected void forwardToErrorPage(HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        req.setAttribute("errorMessage", "Internal server error");
        req.getRequestDispatcher(ERROR_PAGE).forward(req, res);
    }
    /**
     *	Aimed to validate and set default pagination variables. Return not null StringBuilder 
     *	for represents new path, if page is negative or too lagre (and sets normalized page 
     *	value in req resope). Also set default asc and itemsPerPage values if they don't exists.
     * 
     * @param  itemsNum 				number of itmes from DAO
     * @param  parinationParameters 	HashMap of pagination parametes
     * @param  req 						request object to get pagination variables
     * @param  res 						response object to set correct pagination variables
     * @return redirectPath 			if page number must be normalized
     * @throws InternalDAOException 	if any internal DAO error occurs
     */
    protected StringBuilder setPaginationVariables(int itemsNum, HashMap<String, String> paginationParameters, 
    					HttpServletRequest req, HttpServletResponse res) throws NumberFormatException {
    	
    	// Req parametes
    	String pageReq = req.getParameter("page");
		String itemsPerPageReq = req.getParameter("itemsPerPage");
		String ascReq = req.getParameter("asc");
		String sortByReq = req.getParameter("sortBy");
        
		// Default parametes
		int page = Integer.parseInt(paginationParameters.get("page"));
		int itemsPerPage = Integer.parseInt(paginationParameters.get("itemsPerPage"));

        
		// Parsing req parameters, updating pagination variables if
		// req parameters are valid.
		
		if(pageReq != null && !pageReq.equals("")) {
			page = Integer.parseInt(pageReq);
			paginationParameters.put("page", String.valueOf(page));
		}
		if(itemsPerPageReq != null && !itemsPerPageReq.equals("")) {
			if (Integer.parseInt(itemsPerPageReq) != 0) {
				itemsPerPage = Integer.parseInt(itemsPerPageReq);
				paginationParameters.put("itemsPerPage", String.valueOf(itemsPerPage));
			}			
		} 
		if(ascReq != null && !ascReq.equals("")) {
			paginationParameters.put("asc", ascReq);
		}		
		if(sortByReq != null && !sortByReq.equals("")) {
            paginationParameters.put("sortBy", sortByReq);
        }

        //No need redirect, can not do anything
		if (itemsNum <= 0) return null;

		// Asc redirect to the first page if req page is negative
		if(page < 1) {
			paginationParameters.put("page", "1");
			StringBuilder redirect = new StringBuilder();
	        redirect.append(paginationParameters.get("path"));
	        redirect.append("?page=1"); // normalized page
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
     * Aimed to initialize pagination data. Pagination data is represented as
     * <code>HasMap</code> of <code>HapMaps</code>. Each of nested <code>HasMap</code>
     * represents pagination data for one app table.
     * 
     * @param session [description]
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
    	defaultRoutersParameters.put("path", "routers");
    	defaultRoutersParameters.put("page", "1");
    	defaultRoutersParameters.put("itemsPerPage", "10");
    	defaultRoutersParameters.put("sortBy", "SN");
    	defaultRoutersParameters.put("asc", "true");

    	HashMap<String, String> defaultConnectionsParameters = new HashMap<>();
    	defaultConnectionsParameters.put("path", "connections");
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

    protected <T> String validate(T validateObj, Class<?>... groups) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(validateObj, groups);

        String constraintMessage = null;
        for (ConstraintViolation<T> violation : violations) {
            logger.debug("Violation during validate: {}", violation.getMessage());
            constraintMessage = violation.getMessage();
        }
        return constraintMessage;
    }
}