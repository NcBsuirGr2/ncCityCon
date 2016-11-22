package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;


/**
 * Represents REST interface for CRUD operations with users. Assumes,
 * that user has successfully logged into system.
 *
 * @author  Dima
 * @version  0.2
 */
public class CityEditServlet extends AbstractHttpServlet {

    private String CITY_EDIT_PAGE = "/jsp/cities/cityEdit.jsp";
    private String CITY_LIST_URL = "/cities";
    private String CITY_EDIT_URL = "/city";

    public CityEditServlet(){
        super();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.CityEditServlet");
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String cityName = req.getParameter("name");
        String cityCountryName = req.getParameter("countryName");
        if (cityName != null && cityCountryName != null) {
            try {
                ORMCity city = new ORMCity();
                city.setName(cityName);
                city.setCountryName(cityCountryName);
                city.read();
                req.setAttribute("editCity", city.getEntity());
            } catch (DAOException cause) {
                logger.warn("Error occur during reading city", cause);
                forwardToErrorPage("Error occur during reading city", req, res);
                return;
            }
        }
        RequestDispatcher editView = req.getRequestDispatcher(CITY_EDIT_PAGE);
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
                String countryName = req.getParameter("countryName");
                String routersNum = req.getParameter("routersNum");
                logger.info("New city {}", name);
                if (name == null ||  countryName == null || routersNum == null) {
                    logger.info("Something is null {},{}", name, countryName);
                    forwardToErrorPage("Not enough info to create new user", req, res);
                    return;
                }
                ORMCity newCity = new ORMCity();
                try {
                    newCity.setName(name);
                    newCity.setCountryName(countryName);

                    newCity.create();
                } catch(DublicateKeyDAOException cause) {
                    StringBuilder redirect = new StringBuilder();
                    redirect.append(CITY_EDIT_URL);
                    redirect.append("?errorType=dublicate&editName=");
                    redirect.append(newCity.getName());
                    redirect.append("&editCountryName=");
                    redirect.append(newCity.getCountryName());
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

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        String countryName = req.getParameter("countryName");

        if (idString == null) {
            forwardToErrorPage("Cannot update city cause id field is empty", req, res);
            logger.warn("Cannot update city cause id field is empty");
            return;
        }
        ORMCity updateCity = new ORMCity();
        try {

            updateCity.setId(Integer.parseInt(idString));
            updateCity.setName(name);
            updateCity.setCountryName(countryName);
            logger.debug("Updating city with id:{} name:{} countryname:{} ",
                    updateCity.getId(), updateCity.getName(), updateCity.getCountryName() );
            updateCity.update();
        } catch(DublicateKeyDAOException cause) {
            StringBuilder redirect = new StringBuilder();
            redirect.append(CITY_EDIT_URL);
            redirect.append("?errorType=dublicate&editName=");
            redirect.append(updateCity.getName());
            redirect.append("&editCountryName=");
            redirect.append(updateCity.getCountryName());
            res.sendRedirect(redirect.toString());
            return;
        } catch (DAOException cause) {
            logger.warn("Cannot update city", cause);
            forwardToErrorPage("Cannot update city", req, res);
            return;
        } catch (NumberFormatException cause) {
            forwardToErrorPage("Invalid id string", req, res);
            return;
        }

        res.sendRedirect(CITY_LIST_URL+"?success=edit");
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        if (idString == null && name == null) {
            forwardToErrorPage("Cannot delete city cause the id & name fields are empty", req, res);
            logger.warn("Attempt to delete city without name");
            return;
        }
        try {
            ORMCity deleteCity = new ORMCity();
            deleteCity.setId(Integer.parseInt(idString));
            deleteCity.setName(name);

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