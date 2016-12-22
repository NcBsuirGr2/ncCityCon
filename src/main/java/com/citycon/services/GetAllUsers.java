package com.citycon.services;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.services.data.User;
import com.citycon.dao.mysql.UserDAO;
import org.slf4j.LoggerFactory;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * Web service to get all CityCon users.
 *
 * @author Mike
 * @version 2.0
 */
@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class GetAllUsers {
    @WebMethod
    public @WebResult(name = "User") User[] getUsers() {
        User returnUsers[] = null;
        try {
            UserDAO dao = new UserDAO();
            UserEntity users[] = dao.getPage(1, 9999, "login", true, "");
            returnUsers = new User[users.length];
            for (int i = 0; i < users.length; ++i) {
                returnUsers[i] = new User();
                returnUsers[i].setLogin(users[i].getLogin());
                returnUsers[i].setEmail(users[i].getEmail());
                returnUsers[i].setPassword(users[i].getPassword());
            }
        } catch(InternalDAOException | InvalidDataDAOException e) {
            LoggerFactory.getLogger("com.citycon.services.GetAllUsers")
                    .warn("Cannot get users for web service", e);
            return null;
        }
        return returnUsers;
    }
}