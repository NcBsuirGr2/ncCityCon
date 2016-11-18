package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import org.slf4j.Logger;

import java.sql.Connection;
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
}
