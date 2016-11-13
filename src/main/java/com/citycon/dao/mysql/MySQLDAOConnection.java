package com.citycon.dao.mysql;

import com.citycon.dao.DAOException;

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

    private MySQLDAOConnection() throws DAOException {
        try {
            Class.forName(MYSQL_CONNECTOR_CLASS);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Driver for database failed");
        } catch (SQLException e) {
            throw new DAOException("Connect to database failed");
        }
    }

    public static MySQLDAOConnection getInstance() throws DAOException {
        return new MySQLDAOConnection();
    }

//    public static MySQLDAOConnection getInstance() throws DAOException {
//        MySQLDAOConnection localInstance = instance;
//        if (localInstance == null) {
//            synchronized (MySQLDAOConnection.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    try {
//                        instance = localInstance = new MySQLDAOConnection();
//                    } catch (DAOException e) {
//                        throw new DAOException("Dummy");
//                    }
//                }
//            }
//        }
//        return localInstance;
//    }

    public Connection getConnection(){
        return connection;
    }
}
