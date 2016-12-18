//package com.citycon.dao.mysql;
//
//import com.citycon.dao.exceptions.InternalDAOException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.naming.InitialContext;
//import javax.naming.NamingException;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
///**
// * Created by Vojts on 13.11.2016.
// */
//public class MySQLDAOConnection {
//
//    private static volatile MySQLDAOConnection instance;
//
//    private static DataSource dataSource;
//
//    private Logger logger;
//
//    /**
//     * @throws InternalDAOException
//     */
//    private MySQLDAOConnection() throws InternalDAOException {
//        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.MySQLDAOConnection");
//
//        try {
//            InitialContext ctx = new InitialContext();
//            logger.trace("Get context", ctx);
//            dataSource = (DataSource) ctx.lookup("jdbc/City");
//        } catch (NamingException e) {
//            logger.error("ConnectionPool create failed", e);
//            throw new InternalDAOException(e);
//        }
//
//        logger.trace("ConnectionPool create");
//    }
//
//    /**
//     * @throws InternalDAOException
//     */
//    public static MySQLDAOConnection getInstance() throws InternalDAOException {
//        MySQLDAOConnection localInstance = instance;
//        if (localInstance == null) {
//            synchronized (MySQLDAOConnection.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    instance = localInstance = new MySQLDAOConnection();
//                }
//            }
//        }
//        return localInstance;
//    }
//
//    public Connection getConnection() throws InternalDAOException {
//        try {
//            logger.trace("getConnection");
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            logger.error("Get connection failed", e);
//            throw new InternalDAOException("Get connection failed", e);
//        }
//    }
//}

package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.InternalDAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Vojts on 13.11.2016.
 */
public class MySQLDAOConnection {

    private static volatile MySQLDAOConnection instance;

    private ConnectionPool connectionPool;

    private static final String MYSQL_CONNECTOR_CLASS = "com.mysql.jdbc.Driver";

    private static final String URL = "jdbc:mysql://23.99.115.175:3306/nc2_cityCon";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nc_2groupDB";

    private Logger logger;

    /**
     * @throws InternalDAOException
     */
    private MySQLDAOConnection() throws InternalDAOException {
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.MySQLDAOConnection");
        try {
            Class.forName(MYSQL_CONNECTOR_CLASS);
            connectionPool = new ConnectionPool(URL, USERNAME, PASSWORD);
            //connectionPool.setMaxUseTime(5*100);
            connectionPool.setCleaningInterval(20*1000);
            logger.trace("ConnectionPool create");
        } catch (ClassNotFoundException e) {
            logger.error("Driver for database failed");
            throw new InternalDAOException("Driver for database failed", e);
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
        try {
            return connectionPool.getConnection();
        } catch (SQLException e) {
            logger.error("Get connection failed", e);
            throw new InternalDAOException("Get connection failed", e);
        }
    }

}