package com.citycon.services;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.services.data.User;
import com.citycon.dao.mysql.UserDAO;

import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL)
public class GetAllUsers {
    @WebMethod
	public String getTestString(){
	    return "test string from cityCon app";
    }
    @WebMethod
    public String greet(String name){
        return "Nice to see you "+name;
    }
    @WebMethod
    public User getTestUser(){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("testpass");
        user.setLogin("testuser");
        return user;
    }

    @WebMethod
    public User getUserWithLogin(String login){
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setPassword("testpass");
        user.setLogin(login);
        return user;
    }
    @WebMethod
    public @WebResult(name = "User") User[] getUsers(){
        User returnUsers[] = null;
        try {
            UserDAO dao = new UserDAO();
            UserEntity users[] = dao.getPage(1, 999, "login", true);
            returnUsers = new User[users.length];
            for (int i = 0; i < users.length; ++i) {
                returnUsers[i] = new User();
                returnUsers[i].setLogin(users[i].getLogin());
                returnUsers[i].setEmail(users[i].getEmail());
                returnUsers[i].setPassword(users[i].getPassword());
            }
        } catch(InternalDAOException | InvalidDataDAOException e) {
            // TODO: return null
        }
        return returnUsers;
    }
}