package com.citycon.controllers.servlets;

import com.citycon.dao.mysql.MySQLDAOFactory;
import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.interfaces.CitiesOfCountry;
import com.citycon.dao.interfaces.RoutersOfCity;
import com.citycon.dao.exceptions.InternalDAOException;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * Provides backend for connection setup. Can return all cities of particular
 * country and all routers of particular city.
 *
 * @author  Mike
 * @version  2.0
 */
public class SuggestionServlet extends HttpServlet {

    private DAOAbstractFactory factory;
    private Logger logger;

    public SuggestionServlet() {
        factory = new MySQLDAOFactory();
        logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SuggestionServlet"); 
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
                                    throws ServletException, IOException {

        String field = req.getParameter("field");
        if (field == null) {
            logger.debug("Invalid usage of SuggestionServlet, need \"field\" parameter");
            return;
        }
        switch (field) {
            case "city" : {
                try {
                    String country = req.getParameter("country");
                    if (field == null) {
                        logger.debug("Invalid usage of SuggestionServlet, need "
                                         + "\"country\" parameter for \"city\" field");
                        return;
                    }
                    
                    CitiesOfCountry dao = (CitiesOfCountry)factory.getCityDAO();                    
                    CityEntity[] cities = dao.getCities(country);

                    Writer writer = res.getWriter();
                    writer.write("{\"suggestions\":[");
                    for (int i = 0; i < cities.length-1; ++i) {
                        writer.write("\""+cities[i].getName()+"\",");
                    }
                    if (cities.length > 0) {
                        writer.write("\""+cities[cities.length-1].getName()+"\"");
                    }
                    writer.write("]}");
                    writer.flush(); 
                    return; 
                } catch(InternalDAOException e) {                               
                    logger.warn("Cannot read suggestions", e);
                } catch (Exception e) {           
                    logger.warn("Exception", e);
                }
                break;
            }
            case "router" : {
                try {
                    String country = req.getParameter("country");
                    if (field == null) {
                        logger.debug("Invalid usage of SuggestionServlet, need "
                                         + "\"country\" parameter for \"router\" field");
                        return;
                    }
                    String city = req.getParameter("city");
                    if (field == null) {
                        logger.debug("Invalid usage of SuggestionServlet, need "
                                         + "\"city\" parameter for \"router\" field");
                        return;
                    }

                    RoutersOfCity dao = (RoutersOfCity)factory.getRouterDAO();
                    

                    // TODO: fix hack, waiting for DAO
                    CityEntity tmpCity = new CityEntity();
                    tmpCity.setCountryName(country);
                    tmpCity.setName(city);
                    RouterEntity[] routers = dao.getPage(1, 9999, "SN", true, tmpCity);
                    //--------------------------------
                    
                    Writer writer = res.getWriter();
                    writer.write("{\"suggestions\":[");
                    for (int i = 0; i < routers.length-1; ++i) {
                        writer.write("\""+routers[i].getSN()+"\",");
                    }
                    if (routers.length > 0) {
                        writer.write("\""+routers[routers.length-1].getSN()+"\"");
                    }                    
                    writer.write("]}");
                    writer.flush(); 
                    return; 
                } catch(InternalDAOException e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SuggestionServlet");            
                    logger.warn("Cannot read suggestions", e);
                } catch (Exception e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.SuggestionServlet");            
                    logger.warn("Exception", e);
                }
                break;
            }
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
                                        throws ServletException, IOException {
        this.doGet(req, res);
    }
}