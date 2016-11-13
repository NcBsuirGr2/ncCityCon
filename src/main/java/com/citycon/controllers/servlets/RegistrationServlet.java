package com.citycon.controllers.servlets;

import com.citycon.dao.DAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMException;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by root on 13.11.16.
 */
public class RegistrationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ORMUser user = null;
        try {
            user = new ORMUser();   //TODO: logging
                                    //TODO: error page
        } catch (DAOException e) {
            e.printStackTrace();
        }
        Grant grant = new Grant();
        grant.setUsersBranchLevel(1); //может что-то напутано с правами

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("E-mail"));
        user.setGrant(grant);

        RequestDispatcher rd;

        try {
            user.create();
            rd = getServletContext().getRequestDispatcher("/app");
            //перенаправление на страницу после регистрации(пока не созданы) (заменить /index.html и /error)
        } catch (ORMException e) {
            e.printStackTrace();
            rd = getServletContext().getRequestDispatcher("/error.jsp");
            //TODO: logging
            //TODO: error page
        }


        rd.forward(request,response);

    }
}