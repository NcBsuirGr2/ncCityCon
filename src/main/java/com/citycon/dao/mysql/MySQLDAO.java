package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;

import java.sql.Connection;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class MySQLDAO implements DAO {

    protected String nameTable;
    protected Connection connection;

    /**
     * @throws InternalDAOException
     */
    protected MySQLDAO() throws InternalDAOException {
        connection = MySQLDAOConnection.getInstance().getConnection();
    }
}
