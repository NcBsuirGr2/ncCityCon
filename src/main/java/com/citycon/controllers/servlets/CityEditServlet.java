package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

// Created by dima

public class CityEditServlet extends AbstractHttpServlet {

    private String ERROR_PAGE = "/error.jsp";
    private String LIST_CITIES_PAGE = "/userEdit.jsp";
    private String LIST_CITIES_URL = "/cityCon/app/users";

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String cityName = req.getParameter("Name");
        if (cityName != null) {
            try {
                ORMCity city = new ORMCity();
                city.setName(cityName);
                city.read();
                req.setAttribute("city", city.getEntity());
            } catch (DAOException cause) {
                //TODO: logging
                forwardToErrorPage("Error occur during reading city", req, res);
                return;
            }
        }
        RequestDispatcher editView = req.getRequestDispatcher(LIST_CITIES_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String Name = req.getParameter("Name");
        String countryName = req.getParameter("countryName");
        String group = req.getParameter("name");
        Date createDate = new Date(Calendar.getInstance().getTimeInMillis());

        if (Name == null || group == null) {
            forwardToErrorPage("Not enough info to create new city", req, res);
            return;
        }

        try {
            ORMCity newCity = new ORMCity();
            newCity.setName(Name);
            newCity.setCountryName(countryName);

            newCity.create();
        } catch(DAOException cause) {
            //TODO: logging
            forwardToErrorPage("Cannot create new user", req, res);
            return;
        }

        res.sendRedirect(LIST_CITIES_URL);
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idString = req.getParameter("id");
        String Name = req.getParameter("name");
        String countryName = req.getParameter("login");
        String group = req.getParameter("name");

        if (idString == null) {
            forwardToErrorPage("Cannot update city cause id field is empty", req, res);
            return;
        }

        try {
            ORMCity updateCity = new ORMCity();
            updateCity.setName(Name);
            updateCity.setCountryName(countryName);

            updateCity.update();
        } catch (DAOException cause) {
            //TODO: logging
            forwardToErrorPage("Cannot update city", req, res);
            return;
        } catch (NumberFormatException cause) {
            forwardToErrorPage("Invalid id string", req, res);
            return;
        }

        res.sendRedirect(LIST_CITIES_URL);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idString = req.getParameter("id");
        String Name = req.getParameter("Name");
        if (idString == null && Name == null) {
            forwardToErrorPage("Cannot delete city cause the id & login fields are empty", req, res);
            return;
        }
        try {
            ORMCity deleteCity = new ORMCity();
            deleteCity.setId(Integer.parseInt(idString));
            deleteCity.setName(Name);

            deleteCity.delete();
        } catch (DAOException cause) {
            forwardToErrorPage("Cannot delete user", req, res);
            return;
        } catch (NumberFormatException cause) {
            forwardToErrorPage("Infalid id string", req, res);
            return;
        }
        res.sendRedirect(LIST_CITIES_URL);
    }

}
