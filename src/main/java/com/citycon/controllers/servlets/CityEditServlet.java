package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.entities.CityEntity;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Represents REST interface for CRUD operations with users. Assumes,
 * that user has successfully logged into system.
 *
 * @author  Dima, Mike
 * @version  2.0
 */
public class CityEditServlet extends AbstractHttpServlet {
    private static final String CITY_EDIT_PAGE = "/jsp/cities/cityEdit.jsp";
    private static final String CITY_LIST_URL = "/cities";
    private static final String CITY_EDIT_URL = "/city";

    public CityEditServlet() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.CityEditServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String cityName = req.getParameter("name");
        String cityCountryName = req.getParameter("countryName");
        if (notEmpty(cityName) && notEmpty(cityCountryName)) {
            CityEntity city = new CityEntity();
            
            city.setName(cityName);
            city.setCountryName(cityCountryName);
            
            try {
                ORMCity editCity = new ORMCity();
                editCity.setEntity(city);
                editCity.read();
                req.setAttribute("editCity", editCity.getEntity());
            }
            catch ( DAOException cause) {
                logger.warn("Error occur during reading city. DAOException", cause);
                forwardToErrorPage("Internal server error: error occurs during reading city.", req, res);
                return;
            }
        }
        RequestDispatcher editView = req.getRequestDispatcher(CITY_EDIT_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (!notEmpty(action)) {
            forwardToErrorPage("action parameter cannot be empty", req, res);
            return;
        }
        switch (action) {
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
                String countryName = req.getParameter("countryName");

                CityEntity city = new CityEntity();
                city.setName(name);
                city.setCountryName(countryName);

                /*Validation*/
                String validationMessage = validate(city);
                if (validationMessage != null) {
                    forwardToErrorPage(validationMessage, req, res);
                    return;
                }

                ORMCity newCity = new ORMCity();
                newCity.setEntity(city);
                try {
                    logger.trace("Creating city with name:{} countryName:{}", city.getName(), city.getCountryName());
                    newCity.create();
                } catch(DublicateKeyDAOException cause) {
                    StringBuilder redirect = new StringBuilder();
                    redirect.append(CITY_EDIT_URL);
                    redirect.append("?errorType=dublicate2&editName=");
                    redirect.append(city.getName());
                    redirect.append("&editCountryName=");
                    redirect.append(city.getCountryName());
                    res.sendRedirect(redirect.toString());
                    return;
                } catch(DAOException cause) {
                    logger.warn("Cannot create new city", cause);
                    forwardToErrorPage("Cannot create new city", req, res);
                    return;
                }
                res.sendRedirect(CITY_LIST_URL+"?success=add");
                return;
            }
        }

    }

    protected void doPut(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        String countryName = req.getParameter("countryName");

        CityEntity city = new CityEntity();
        try {
            city.setId(Integer.parseInt(idString));
        } catch (NullPointerException | NumberFormatException cause) {
            forwardToErrorPage("Invalid id", req, res);
            return;
        }
        city.setName(name);
        city.setCountryName(countryName);

        /*Validation*/
        String validationMessage = validate(city);
        if (validationMessage != null) {
            forwardToErrorPage(validationMessage, req, res);
            return;
        }

        ORMCity updateCity = new ORMCity();
        updateCity.setEntity(city);
        logger.trace("Updating city {}", city);
        try{
            updateCity.update();
        } catch(DublicateKeyDAOException cause) {
            StringBuilder redirect = new StringBuilder();
            redirect.append(CITY_EDIT_URL);
            redirect.append("?errorType=dublicate&editName=");
            redirect.append(city.getName());
            redirect.append("&editCountryName=");
            redirect.append(city.getCountryName());
            res.sendRedirect(redirect.toString());
            return;
        } catch (DAOException cause) {
            logger.warn("Cannot update city", cause);
            forwardToErrorPage("Internal server error: error occurs during updating city", req, res);
            return;
        } catch (Exception e) {
            logger.warn("Unexcepted exception", e);
            forwardToErrorPage("Internal server error.", req, res);
            return;
        }
        res.sendRedirect(CITY_LIST_URL+"?success=edit");
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String idString = req.getParameter("id");

        CityEntity city = new CityEntity();
        try {
            city.setId(Integer.parseInt(idString));
        } catch (NullPointerException | NumberFormatException cause) {
            forwardToErrorPage("Invalid id", req, res);
            return;
        }

        ORMCity deleteCity = new ORMCity();
        deleteCity.setEntity(city);
        logger.trace("Deleting city {}", city);
        try {
            deleteCity.delete();
        } catch (DAOException cause) {
            logger.warn("Cannot delete city", cause);
            forwardToErrorPage("Cannot delete city", req, res);
            return;
        } catch (NumberFormatException cause) {
            logger.warn("Invalid id string", cause);
            forwardToErrorPage("Invalid id string", req, res);
            return;
        }
        res.sendRedirect(CITY_LIST_URL+"?success=delete");
    }
}