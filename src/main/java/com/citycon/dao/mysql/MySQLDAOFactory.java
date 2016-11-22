package com.citycon.dao.mysql;

import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.exceptions.InternalDAOException;

/**
 * Created by Vojts on 09.11.2016.
 */
public class MySQLDAOFactory extends DAOAbstractFactory {

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getUserDAO() throws InternalDAOException {
        return UserDAO.getInstance();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getCityDAO() throws InternalDAOException {
        return CityDAO.getInstance();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getRouterDAO() throws InternalDAOException {
        return RouterDAO.getInstance();
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public DAO getRouterConnectionDAO() throws InternalDAOException {
        return RouterConnectionDAO.getInstance();
    }
}
