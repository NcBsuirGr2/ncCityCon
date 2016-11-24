package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.InternalDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Provides interface for autocompete functionality. 
 * 
 * @author Mike
 * @version 0.1
 */
public class AutoCompleteDAO {

    protected Connection connection;
    protected static AutoCompleteDAO instance;
    private static final String URL = "jdbc:mysql://23.99.115.175:3306/nc2_cityCon";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nc_2groupDB";
    Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");

    private AutoCompleteDAO() throws InternalDAOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.error("Cannot create connection", e);
            throw new InternalDAOException("Critical error", e);
        }
        //connection = MySQLDAOConnection.getInstance().getConnection();
    }

    public static AutoCompleteDAO getInstance() throws InternalDAOException {
        if(instance == null) {
            instance = new AutoCompleteDAO();
        }
        return instance;
    }

    /**
     * Return list of countries by the part of it's name.
     * 
     * @param  queryPart            part of country name
     * @return suggestions            ArrayList of country names
     * @throws InternalDAOException if any internal DAOException occurs
     */
    public synchronized ArrayList<String> getSuggestions(String queryPart) throws InternalDAOException {
        try {
            if(connection.isClosed()) {
                InternalDAOException e = new InternalDAOException("Connection is closed");
                //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
                logger.error("Cannot create connection", e);
                throw e;
            }
        }catch (SQLException e) {
            //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.error("Cannot check if exception is closed", e);
            throw new InternalDAOException("Cannot check if exception is closed", e);
        }

        ArrayList<String> suggestions = new ArrayList<>();
        logger.trace("Before prepare statement");
        try (PreparedStatement statement = connection.prepareStatement("select distinct Country from City  where Country like ?")) {
            logger.trace("After prepare statement");
            if (queryPart != null) {
                statement.setString(1, "%"+queryPart+"%");
            } else {
                statement.setString(1, "%%");
            }
            

            try (ResultSet resultSet = statement.executeQuery()) {             

                while(resultSet.next()) {
                    suggestions.add(resultSet.getString("Country"));
                }

            } catch (SQLException e) {
                //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
                logger.warn("Cannot get suggestions", e);
                throw new InternalDAOException();
            }
        } catch (SQLException e) {
            //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.warn("Cannot prepare statement", e);
            throw new InternalDAOException();
        }

        return suggestions;
    }

    /**
     * Return list of cities or routers by the part of it's name and part of country or city name.
     * 
     * @param  type                 desired list type, "router" or "city"
     * @param  queryPart            part of country name
     * @param  parameter            part of city or country name
     * @return suggestions            ArrayList of country names
     * @throws InternalDAOException if any internal DAOException occurs
     */
    public synchronized ArrayList<String> getSuggestions(String type, String queryPart, String parameter) throws InternalDAOException {
        try {
            if(connection.isClosed()) {
                InternalDAOException e = new InternalDAOException("Connection is closed");
                //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
                logger.error("Cannot create connection", e);
                throw e;
            }
        }catch (SQLException e) {
            //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.error("Cannot check if exception is closed", e);
            throw new InternalDAOException("Cannot check if exception is closed", e);
        }

        String statementString = "";
        String resultString = "";
        switch (type) {
            case "city" : {
                statementString = "select distinct Name from City where Name like ? and Country like ?";
                resultString = "Name";
                break;
            }
            case "router" : {
                statementString = "select Router.SN from (City City inner join Router"
                                        + " Router on City.ID=Router.City_id) where Router.SN like ? and City.Name like ?";
                resultString = "Router.SN";
                break;
            }
        }
        ArrayList<String> suggestions = new ArrayList<>();
        logger.trace("Before prepare statement");
        try (PreparedStatement statement = connection.prepareStatement(statementString)) {
            logger.trace("After prepare statement");
            if (queryPart != null) {
                statement.setString(1, "%"+queryPart+"%");
                statement.setString(2, "%"+parameter+"%");
            } else {
                statement.setString(1, "%%");
                statement.setString(2, "%%");
            }
            

            try (ResultSet resultSet = statement.executeQuery()) {             

                while(resultSet.next()) {
                    suggestions.add(resultSet.getString(resultString));
                }

            } catch (SQLException e) {
                //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
                logger.warn("Cannot get suggestions", e);
                throw new InternalDAOException();
            }
        } catch (SQLException e) {
           //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.warn("Cannot prepare statement", e);
            throw new InternalDAOException();
        }

        return suggestions;
    }

    public void closeConnection() {
        try {
            if (!connection.isClosed()) {            
                connection.close();
            } 
        } catch(SQLException e) {
            //Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.AutoCompleteDAO");
            logger.warn("Cannot close connection", e);
        }
    }
}