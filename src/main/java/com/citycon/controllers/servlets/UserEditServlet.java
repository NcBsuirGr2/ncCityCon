package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.controllers.listeners.SessionHolder;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Set;


/**
 * Represents REST interface for CRUD operations with users. Assumes, 
 * that user has successfully logged into system.
 * 
 * @author  Mike
 * @version  0.2
 */
public class UserEditServlet extends AbstractHttpServlet {

	private String USER_EDIT_PAGE = "/jsp/users/userEdit.jsp";
	private String USER_LIST_URL = "/users";
	private String USER_EDIT_URL = "/user";

	public UserEditServlet(){
		super();
		logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.UserEditServlet");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String userLogin = req.getParameter("login");
		if (userLogin != null) {
			try {
				ORMUser user = new ORMUser();
				user.getEntity().setLogin(userLogin);
				user.read();
				req.setAttribute("editUser", user.getEntity());
			} catch (DAOException cause) {
				logger.warn("Error occur during reading user", cause);
				forwardToErrorPage("Error occur during reading user", req, res);
				return;
			} catch (Exception exception) {
                logger.warn("Unexcepted exception");
                forwardToErrorPage("Internal servler error", req, res);
                return;
            }
		}
		RequestDispatcher editView = req.getRequestDispatcher(USER_EDIT_PAGE);
		editView.forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
														throws ServletException, IOException {
		String type = req.getParameter("type");
		if (type == null) {
			forwardToErrorPage("type parameter is null", req, res);
		}
		switch (type) {
			case "edit" : {
				doPut(req, res);
				return;
			}
			case "delete" : {
				doDelete(req, res);
				return;
			}
			default : {
				String name = req.getParameter("name");
				String login = req.getParameter("login");
				String password = req.getParameter("password");
				String email = req.getParameter("email");
				String group = req.getParameter("group");
				Date createDate = new Date(Calendar.getInstance().getTimeInMillis());
				logger.info("New user {}", login);
				if (name == null || login == null || password == null || group == null) {
					logger.info("Something is null {},{},{},{},{}", name, login, password, email, group);
					forwardToErrorPage("Not enough info to create new user", req, res);
					return;
				}
				UserEntity user = new UserEntity();				
				user.setName(name);
				user.setLogin(login);
				user.setPassword(password);
				user.setEmail(email);
				user.setGroup(group);
				user.setCreateDate(createDate);

				ORMUser newUser = new ORMUser();
				try {	
					ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
					Validator validator = factory.getValidator();
					Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
					
					logger.debug("Violations size: {}", violations.size());
					for (ConstraintViolation<UserEntity> violation : violations) {
						logger.debug("Violation");
						logger.debug("There are violations for new user: {}", violation.getMessage());
					}			

					newUser.setEntity(user);
					newUser.create();
				} catch(DublicateKeyDAOException cause) {
					StringBuilder redirect = new StringBuilder();
					redirect.append(USER_EDIT_URL);
					redirect.append("?errorType=dublicate&editName=");
					redirect.append(user.getName());
					redirect.append("&editLogin=");
					redirect.append(user.getLogin());
					redirect.append("&editEmail=");
					redirect.append(user.getEmail());
					redirect.append("&editGroup=");
					redirect.append(user.getGroup());
					res.sendRedirect(redirect.toString());
					return;
				} catch(DAOException cause) {
					logger.warn("Cannot create new user", cause);
					forwardToErrorPage("Cannot create new user", req, res);
					return;
				}

				res.sendRedirect(USER_LIST_URL+"?success=add");
				return;
			}
		}
		
	}

	protected void doPut(HttpServletRequest req, HttpServletResponse res)
            									throws ServletException, IOException {
		String idString = req.getParameter("id");
		String name = req.getParameter("name");
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		String email = req.getParameter("email");
		String group = req.getParameter("group");
		logger.info("New user {}", login);
		if (name == null || login == null || password == null || group == null) {
			logger.info("Something is null {},{},{},{},{}", name, login, password, email, group);
			forwardToErrorPage("Not enough info to create new user", req, res);
			return;
		}
		if (idString == null) {
			forwardToErrorPage("Cannot update user cause id field is empty", req, res);
			logger.warn("Cannot update user cause id field is empty");
			return;
		}
		
		UserEntity user = new UserEntity();
		try {				
			user.setId(Integer.parseInt(idString));
			user.setName(name);
			user.setLogin(login);
			user.setPassword(password);
			user.setEmail(email);
			user.setGroup(group);

			ORMUser updateUser = new ORMUser();

			updateUser.setEntity(user);

			logger.debug("Updating user with id:{} loging:{} password:{} name:{} email:{} group:{}", 
				user.getId(), user.getName(), user.getLogin(), user.getPassword(),
				user.getEmail(), user.getGroup());

			updateUser.update();
			SessionHolder.updateUser(updateUser.getEntity());

		} catch(DublicateKeyDAOException cause) {
			StringBuilder redirect = new StringBuilder();
			redirect.append(USER_EDIT_URL);
			redirect.append("?errorType=dublicate&editName=");
			redirect.append(user.getName());
			redirect.append("&editLogin=");
			redirect.append(user.getLogin());
			redirect.append("&editEmail=");
			redirect.append(user.getEmail());
			redirect.append("&editGroup=");
			redirect.append(user.getGroup());
			res.sendRedirect(redirect.toString());
			return;
		} catch (DAOException cause) {
			logger.warn("Cannot update user", cause);
			forwardToErrorPage("Cannot update user", req, res);
			return;
		} catch (NumberFormatException cause) {
			forwardToErrorPage("Invalid id string", req, res);
			return;
		}
		
		res.sendRedirect(USER_LIST_URL+"?success=edit");
	}

	protected void doDelete(HttpServletRequest req, HttpServletResponse res) 
											throws ServletException, IOException {
		String idString = req.getParameter("id");
		if (idString == null) {
			forwardToErrorPage("Cannot delete user cause the id & login fields are empty", req, res);
			logger.warn("Attempt to delete user without login or password");
			return;
		}
		try {
			ORMUser deleteUser = new ORMUser();
			deleteUser.getEntity().setId(Integer.parseInt(idString));
			deleteUser.delete();
			SessionHolder.deleteUser(deleteUser.getEntity());
		} catch (DAOException cause) {
			logger.warn("Cannot delete user", cause);
			forwardToErrorPage("Cannot delete user", req, res);
			return;
		} catch (NumberFormatException cause) {
			logger.warn("Invalid id string", cause);
			forwardToErrorPage("Invalid id string", req, res);
			return;
		}
		res.sendRedirect(USER_LIST_URL+"?success=delete");
	}
}