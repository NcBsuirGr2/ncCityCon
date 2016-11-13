package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.DAOException;

/**
 * Created by Vojts on 09.11.2016.
 */
public class MySQLDAOFactory extends DAOAbstractFactory {
    /**
     * @return
     * @throws DAOException
     */
    public DAO getUserDAO() throws DAOException {
        return UserDAO.getInstance();
    }

    /**
     * @return
     */
    public DAO getCityDAO() {
        return CityDAO.getInstance();
    }

    /**
     * @return
     */
    public DAO getRouterDAO() {
        return RouterDAO.getInstance();
    }

    /**
     * @return
     */
    public DAO getRouterConnectionDAO() {
        return RouterConnectionDAO.getInstance();
    }
}
