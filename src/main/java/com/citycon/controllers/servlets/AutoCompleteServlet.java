package com.citycon.controllers.servlets;

import com.citycon.dao.mysql.AutoCompleteDAO;
import com.citycon.dao.exceptions.InternalDAOException;

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
 * Autocomplete backend for connection setup.
 *
 * @author  Mike
 * @version  0.1
 */
public class AutoCompleteServlet extends HttpServlet {


    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String field = req.getParameter("field");
        switch (field) {
            case "country" : {
                try {
                    Writer writer = res.getWriter();
                    ArrayList<String> countryNames = AutoCompleteDAO.getInstance().getSuggestions(req.getParameter("query"));
                    printSuggestions(countryNames, writer);     
                } catch(InternalDAOException e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Cannot read suggestions", e);
                } catch (Exception e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Exception", e);
                }
                break;
            }
            case "city" : {
                try {
                    Writer writer = res.getWriter();
                    ArrayList<String> cityNames = AutoCompleteDAO.getInstance().getSuggestions("city", req.getParameter("query"), req.getParameter("country"));
                    printSuggestions(cityNames, writer);     
                } catch(InternalDAOException e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Cannot read suggestions", e);
                } catch (Exception e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Exception", e);
                }
                break;
            }
            case "router" : {
                try {
                    Writer writer = res.getWriter();
                    ArrayList<String> routerSN = AutoCompleteDAO.getInstance().getSuggestions("router", req.getParameter("query"), req.getParameter("city"));
                    printSuggestions(routerSN, writer);     
                } catch(InternalDAOException e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Cannot read suggestions", e);
                } catch (Exception e) {
                    Logger logger = LoggerFactory.getLogger("com.citycon.controllers.servlets.AutoCompleteServlet");            
                    logger.warn("Exception", e);
                }
                break;
            }
        }

    }

    private void printSuggestions(ArrayList<String> suggestions, Writer writer) throws IOException {
        Iterator iterator = suggestions.iterator();

        writer.write("[");
        while(iterator.hasNext()) {
            writer.write("{\"name\":\""+(String)iterator.next()+"\"}");
            if(iterator.hasNext()) {
                writer.write(",");
            }
        }
        writer.write("]");

        writer.flush();                    
        return;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
                                        throws ServletException, IOException {
        this.doGet(req, res);
    }
    // @Override
    // public void destroy() {
    //     dao.closeConnection();        
    // }

   
}