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
 * Superclass for DAO Layer.
 *
 * @author Alex
 * @version 2.0
 */
public abstract class MySQLDAO implements DAO {

    protected String nameTable;
    protected Connection connection;
    protected Logger logger;
    protected MySQLDAOConnection connections;

    protected Map<String, String> hashMap = new HashMap<>();


    protected MySQLDAO() throws InternalDAOException {
        connections = MySQLDAOConnection.getInstance();
    }

    /**
     * Get parameters for sorting in Db
     *
     * @return
     */
    @Override
    public Set<String> getSortingParameters() {
        return hashMap.keySet();
    }

    /**
     * Helper for get count of entity
     *
     * @param search
     * @return
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    protected int count_search(String search) throws InternalDAOException, InvalidDataDAOException {
        int count = 0;
        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
            if (resultSet.first()) {
                count = resultSet.getInt(1);

                logger.trace("Get count elements in {}", nameTable);
            }
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for count_element in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for count_element in " + nameTable, e);
        }

        return count;
    }

    /**
     * Delete chosen entity from DB
     *
     * @param deleteElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    @Override
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {
        String delete = "delete from " + nameTable + " where `id`=?";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(delete)
        ){
            try {
                preparedStatement.setInt(1, deleteElement.getId());

                preparedStatement.executeUpdate();

                logger.trace("Delete was successful in {}", nameTable);
            } catch (SQLException e) {
                logger.info("Delete failed in {}", nameTable, e);
                throw new InvalidDataDAOException("Delete failed in " + nameTable, e);
            }
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for delete in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for delete in " + nameTable, e);
        }
    }
}