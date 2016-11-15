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
import java.sql.Date;
import java.util.Calendar;

/**
 * Created by root on 13.11.16.
 */
public class SignUpServlet extends HttpServlet {

    private static String ERROR = "/error.jsp";

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        ORMUser user = null;
        try {
            user = new ORMUser();   //TODO: logging
                                    //TODO: error page
        } catch (ORMException e) {
            e.printStackTrace();
        }
        Grant grant = new Grant();
        grant.setUsersBranchLevel(1); //может что-то напутано с правами
        java.sql.Date timeNow = new Date(Calendar.getInstance().getTimeInMillis());

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("E-mail"));
        user.setName(request.getParameter("name"));
        user.setGroup("user");
        user.setCreateDate(timeNow);
        user.setGrant(grant);

        RequestDispatcher rd;

        try {
            user.create();
            rd = getServletContext().getRequestDispatcher("/app/users");
            //перенаправление на страницу после регистрации(пока не созданы) (заменить /index.html и /error)
        } catch (ORMException e) {
            e.printStackTrace();
            request.getSession().setAttribute("error",e.getMessage());
            rd = getServletContext().getRequestDispatcher(ERROR);
            //TODO: logging
        }


        rd.forward(request,response);

    }
}