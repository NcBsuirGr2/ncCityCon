package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.InternalDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Vojts on 13.11.2016.
 */
public class MySQLDAOConnection {

    private static volatile MySQLDAOConnection instance;

    private Connection connection;

    private final String MYSQL_CONNECTOR_CLASS = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://23.99.115.175:3306/nc2_cityCon";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nc_2groupDB";

    /**
     * @throws InternalDAOException
     */
    private MySQLDAOConnection() throws InternalDAOException {
        Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.MySQLDAOConnection");
        try {
            Class.forName(MYSQL_CONNECTOR_CLASS);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.trace("Connection create");
        } catch (ClassNotFoundException e) {
            logger.error("Driver for database failed");
            throw new InternalDAOException("Driver for database failed", e);
        } catch (SQLException e) {
            logger.error("Connect to database failed");
            throw new InternalDAOException("Connect to database failed", e);
        }
    }

    /**
     * @throws InternalDAOException
     */
    public static MySQLDAOConnection getInstance() throws InternalDAOException {
        MySQLDAOConnection localInstance = instance;
        if (localInstance == null) {
            synchronized (MySQLDAOConnection.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySQLDAOConnection();
                }
            }
        }
        return localInstance;
    }

    public Connection getConnection() throws InternalDAOException {
        return connection;
    }

    public static void close() throws InternalDAOException {
        Logger logger = LoggerFactory.getLogger("com.citycon.dao.mysql.MySQLDAOConnection");

        if (instance != null) {
            synchronized (MySQLDAOConnection.class) {
                try {
                    instance.connection.close();
                    instance = null;
                    logger.trace("Connection close");
                } catch (SQLException e) {
                    logger.error("Connection close failed", e);
                    throw new InternalDAOException("Connection close failed", e);
                } catch (Throwable throwable) {
                    logger.error("Destroy MySQLConnection failed", throwable);
                    throw new InternalDAOException("Destroy MySQLConnection failed", throwable);
                }
            }
        }
    }

}
