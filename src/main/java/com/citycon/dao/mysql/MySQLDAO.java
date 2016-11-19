package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
        connection = MySQLDAOConnection.getInstance().getConnection();
    }

    public int count_element() throws InternalDAOException, InvalidDataDAOException {
        int count = 0;

        String search = "select count(`id`) from" + nameTable;

        Statement statement = null;
        ResultSet resultSet = null;

        try {
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
            } else {
                logger.info("This table is empty");
                throw new InternalDAOException("This table is empty");
            }

        } catch (SQLException e) {
            logger.info("Get count elements failed", e);
            throw new InternalDAOException("Get count elements failed", e);
        }
        finally {
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
}