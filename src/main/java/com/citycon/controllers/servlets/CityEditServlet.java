package com.citycon.controllers.servlets;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.orm.ORMCity;
import com.citycon.model.systemunits.entities.CityEntity;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.Validator;
import javax.validation.ConstraintViolation;

import java.util.Set;
import java.io.IOException;
import java.io.Writer;
import java.sql.Date;
import java.util.Calendar;


/**
 * Represents REST interface for CRUD operations with users. Assumes,
 * that user has successfully logged into system.
 *
 * @author  Dima
 * @version  0.3
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
            CityEntity city = new CityEntity();
            
            city.setName(cityName);
            city.setCountryName(cityCountryName);
            
            try {
                ORMCity editCity = new ORMCity();
                editCity.setEntity(city);
                editCity.read();
                req.setAttribute("editCity", editCity.getEntity());
            } catch (InvalidDataDAOException cause) {
                logger.warn("Error occur during reading city. InvalidDataDAOException", cause);
                forwardToErrorPage("Error occur during reading city. InvalidDataDAOException", req, res);
                return;
            }
            catch (DublicateKeyDAOException cause) {
                logger.warn("Error occur during reading city. DublicateKeyDAOException", cause);
                forwardToErrorPage("Error occur during reading city. DublicateKeyDAOException", req, res);
                return;
            }
            catch (InternalDAOException cause) {
                logger.warn("Error occur during reading city. InternalDAOException", cause);
                forwardToErrorPage("Error occur during reading city. InternalDAOException", req, res);
                return;
            }
            catch ( DAOException cause) {
                logger.warn("Error occur during reading city. DAOException", cause);
                forwardToErrorPage("Error occur during reading city. DAOException", req, res);
                return;
            }
        }

        RequestDispatcher editView = req.getRequestDispatcher(CITY_EDIT_PAGE);
        editView.forward(req, res);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            forwardToErrorPage("action parameter is null", req, res);
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
                logger.info("New city {}", name);
                if (name == null || countryName == null) {
                    logger.info("Something is null {},{}", name, countryName);
                    forwardToErrorPage("Not enough info to create new city", req, res);
                    return;
                }
                CityEntity city = new CityEntity();
                city.setName(name);
                city.setCountryName(countryName);

                ORMCity newCity = new ORMCity();

                /*Validation*/
                String validationMessage = validate(city);
                if (validationMessage != null) {
                    forwardToErrorPage(validationMessage, req, res);
                    return;
                }

                newCity.setEntity(city);
                logger.trace("Creating city with name:{} countryName:{}", city.getName(), city.getCountryName());
                try {      
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

    protected void doPut(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String idString = req.getParameter("id");
        String name = req.getParameter("name");
        String countryName = req.getParameter("countryName");

        if (name == null || countryName == null) {
            forwardToErrorPage("Cannot update city cause name & countryName fields is empty", req, res);
            logger.warn("Cannot update city cause name & countryName fields is empty");
            return;
        }
        CityEntity city = new CityEntity();
        city.setId(Integer.parseInt(idString));
        city.setName(name);
        city.setCountryName(countryName);

        ORMCity updateCity = new ORMCity();

        /*Validation*/
        String validationMessage = validate(city);
        if (validationMessage != null) {
            forwardToErrorPage(validationMessage, req, res);
            return;
        }

        updateCity.setEntity(city);
        logger.trace("Updating city with name:{} countryName:{}", city.getName(), city.getCountryName());
        try {
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
        String countryName = req.getParameter("countryName");
//        TODO: нужно удалять по name & countryName
//        
//        if (name == null && countryName == null) {
//            forwardToErrorPage("Cannot delete city cause the name & countryName fields are empty", req, res);
//            logger.warn("Attempt to delete city without name");
//            return;
//        }
        if (idString == null) {
            forwardToErrorPage("Cannot delete city cause idString field are empty", req, res);
            logger.warn("Attempt to delete city without idString");
            return;
        }
        CityEntity city = new CityEntity();
        city.setId(Integer.parseInt(idString));

        ORMCity deleteCity = new ORMCity();
        deleteCity.setEntity(city);
        logger.trace("Deleting city with id:{}", city.getId());
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
    protected String validate(CityEntity city) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CityEntity>> violations = validator.validate(city);

        String constraintMessage = null;
        for (ConstraintViolation<CityEntity> violation : violations) {
            logger.debug("There are violations for new city: {}", violation.getMessage());
            constraintMessage = violation.getMessage();
        }
        return constraintMessage;
    }
}