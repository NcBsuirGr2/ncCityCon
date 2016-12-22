package com.citycon.dao.mysql;

import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.exceptions.InternalDAOException;

/**
 * MysqlDAO Factory provide Object for Business Layer that implement DAO interface.
 *
 * @author Alex
 * @version 2.0
 */
public class MySQLDAOFactory extends DAOAbstractFactory {

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getUserDAO() throws InternalDAOException {
        return new UserDAO();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getCityDAO() throws InternalDAOException {
        return new CityDAO();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getRouterDAO() throws InternalDAOException {
        return new RouterDAO();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getRouterConnectionDAO() throws InternalDAOException {
        return new RouterConnectionDAO();
    }
}
