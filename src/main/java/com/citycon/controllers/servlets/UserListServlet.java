package com.citycon.controllers.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

/**
 * Used to show the list of users. Support pagination. Redirects to the
 * same page if some pagination data is invlaid and redirects to the error
 * page if DAOException occurs.
 * 	
 * @author Mike
 * @version 0.2
 */
public class UserListServlet extends AbstractHttpServlet {
	private static String USER_LIST_PAGE = "/jsp/users/userList.jsp";
	private static String ERROR_PAGE = "/jsp/errors/error.jsp";

	public UserListServlet(){
		super();
		logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.UserListServlet");
	}

	protected void doGet(HttpServletRequest req, 
		HttpServletResponse res) throws ServletException, IOException {

		String pageString = req.getParameter("page");
		String itemsPerPageString = req.getParameter("itemsPerPage");
		String sortByString = req.getParameter("sortBy");
		String ascString = req.getParameter("asc");

		int page = 1;
		int itemsPerPage = 10;
		String sortBy = "name";
		boolean asc = true;
		try {
			if(pageString != null && !pageString.equals("")) {
			page = Integer.parseInt(pageString);
			}
			if(itemsPerPageString != null && !itemsPerPageString.equals("")) {
				itemsPerPage = Integer.parseInt(itemsPerPageString);
			}
			if(sortByString != null && !sortByString.equals("")) {
				sortBy = sortByString;
			}
		} catch(NumberFormatException exception) {
			forwardToErrorPage("Invalid search input", req, res);
		}
		
		if(ascString != null && !ascString.equals("")) {
			asc = ascString.equals("true");
		}

		logger.trace("getPage of users with args page:{} itemsPerPage:{}, sortBy:{}, asc:{}", 
																page, itemsPerPage, sortBy, asc);
		try {
			int usersNum = ORMUser.getCount();
			int pagesNum = (int)Math.ceil((double)usersNum / (double)itemsPerPage);	
				
			if (usersNum <= (page-1)*itemsPerPage) {
				page = usersNum/itemsPerPage;
				if(usersNum != page*itemsPerPage) {
					page += 1;
				}
				StringBuilder redirect = new StringBuilder();
				redirect.append("/users?page=");
				redirect.append(page);
				redirect.append("&itemsPerPage=");
				redirect.append(itemsPerPage);
				redirect.append("&sortBy=");
				redirect.append(sortBy);
				redirect.append("&asc=");
				redirect.append(asc);
				logger.debug("Redirect to the "+redirect.toString());
				res.sendRedirect(redirect.toString());
				return;
			}
			// Pagination variables
			int currentPage = page;
			int beginPage = (page/10)*10;
			int endPage = beginPage+9;
			if(beginPage < 1) beginPage = 1;
			if(endPage > pagesNum) endPage = pagesNum;

			int previousPage = currentPage-10;
			int nextPage = currentPage+10;
			if(previousPage < 1) previousPage = 1;
			if(nextPage > pagesNum) nextPage = pagesNum;

			req.setAttribute("currentPage", currentPage);
			req.setAttribute("beginPage", beginPage);
			req.setAttribute("endPage", endPage);
			req.setAttribute("previousPage", previousPage);
			req.setAttribute("nextPage", nextPage);		
			// -------------
			UserEntity[] users = ORMUser.getPage(page, itemsPerPage, sortBy, asc);
			req.setAttribute("entityArray", users);
			req.getRequestDispatcher(USER_LIST_PAGE).forward(req, res);
		} catch (InvalidDataDAOException exception) {
			forwardToErrorPage("Invalid search input", req, res);
			logger.debug("Invalid getPage data", exception);
		} catch (DAOException exception) {
			forwardToErrorPage("Internal DAO exception", req, res);
		}
	}
	
	protected void doPost(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}
}
