package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class MySQLDAO implements DAO {
    protected Connection connection;

    /**
     * @throws DAOException
     */
    protected MySQLDAO() throws DAOException {
        connection = MySQLDAOConnection.getInstance().getConnection();
    }
}
