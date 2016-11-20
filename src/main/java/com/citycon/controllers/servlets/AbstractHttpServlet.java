package com.citycon.controllers.servlets;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 */
public abstract class AbstractHttpServlet extends HttpServlet {
    private static final String ERROR_PAGE = "/jsp/errors/error.jsp";

    protected Logger logger;

    protected void forwardToErrorPage(String errorMessage, HttpServletRequest req,
                          HttpServletResponse res) throws ServletException, IOException {

        RequestDispatcher errorPage = req.getRequestDispatcher(ERROR_PAGE);
        req.setAttribute("errorMessage", errorMessage);
        errorPage.forward(req, res);
    }
    /**
     *	Aimed to validate and set default pagination variables. Return fasle, if
     *	page is negative or too lagre (and sets normalized page value in req resope)
     *	Also set default asc and itemsPerPage values if they don't exists.
     * 
     * @param  itemsNum number of itmes from DAO
     * @param  req request object to get pagination variables
     * @param  res response object to set correct pagination variables
     * @return validParametes if page number can be normalized 
     * @throws InternalDAOException if any internal DAO error occurs
     */
    protected boolean setPaginationVariables(int itemsNum, HttpServletRequest req,
     									 HttpServletResponse res) throws NumberFormatException {
    	// Req parametes
    	String pageString = req.getParameter("page");
		String itemsPerPageString = req.getParameter("itemsPerPage");
		String ascString = req.getParameter("asc");

		// Default parametes
		int page = 1;
		int itemsPerPage = 10;
		boolean asc = true;

		// Parsing req parameters, NumberFormatException can be thrown
	
		if(pageString != null && !pageString.equals("")) {
		page = Integer.parseInt(pageString);
		}
		if(itemsPerPageString != null && !itemsPerPageString.equals("")) {
			itemsPerPage = Integer.parseInt(itemsPerPageString);
		}		


		if(ascString != null && !ascString.equals("")) {
			asc = ascString.equals("true");
		}

		// Default values
		req.setAttribute("itemsPerPage", itemsPerPage);
		req.setAttribute("asc", asc);

		// Asc redirect to the first page if req page is negative
		if(page < 1) {
			req.setAttribute("currentPage", 1);
			return false;
		}

		int pagesNum = (int)Math.ceil((double)itemsNum / (double)itemsPerPage);	
		// If req page is too big, redirect to the last page
		if (itemsNum <= (page-1)*itemsPerPage) {
			page = itemsNum/itemsPerPage;
			if(itemsNum != page*itemsPerPage) {
				page += 1;
			}
			req.setAttribute("currentPage", page);
			return false;
		}

		// Pagination variables
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
		return true;
    }
}