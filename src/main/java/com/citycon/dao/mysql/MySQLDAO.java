package com.citycon.dao.mysql;

import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.Entity;
import org.slf4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class MySQLDAO implements DAO {

    protected String nameTable;
    protected Connection connection;
    protected Logger logger;

    protected Map<String, String> hashMap = new HashMap<>();

    /**
     * @throws InternalDAOException
     */
    protected MySQLDAO() throws InternalDAOException {
    }

    protected Connection getConnection() throws InternalDAOException {
        logger.trace("Get connection");
        return MySQLDAOConnection.getInstance().getConnection();
    }


    protected void closeConnection() throws InternalDAOException {
        try {
            if(!connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Close Connection failed", e);
            throw new InternalDAOException(e);
        }
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public int count_element() throws InternalDAOException {
        int count = 0;

        String search = String.format("select count(`id`) from %s", nameTable);

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.warn("Statement in get count wasn't created", e);
            throw new InternalDAOException("Statement in get count wasn't created", e);
        }

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                count = resultSet.getInt(1);

                logger.trace("Get count elements");
            }

        } catch (SQLException e) {
            logger.info("Get count elements failed", e);
            throw new InternalDAOException("Get count elements failed", e);
        }
        finally {
            closeConnection();

            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in get count false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in get count false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return count;
    }

    /**
     * @param deleteElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {

        PreparedStatement preparedStatement = null;

        String delete = "delete from" + nameTable + "where `id`=?";

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(delete);
        }catch (SQLException e) {
            logger.warn("Prepare statement in delete wasn't created", e);
            throw new InternalDAOException("Prepare statement in delete wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, deleteElement.getId());

            preparedStatement.executeUpdate();

            logger.trace("Delete was successful");
        } catch (SQLException e) {
            logger.info("Delete failed", e);
            throw new InvalidDataDAOException("Delete failed", e);
        }
        finally {
            closeConnection();

            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in delete false", e);
                    throw new InternalDAOException(e);
                }
            }
        }
    }

    @Override
    public Set<String> getSortingParameters() {
        return hashMap.keySet();
    }

}