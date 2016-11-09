package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOAbstractFactory;

/**
 * Created by Vojts on 09.11.2016.
 */
public class MySQLDAOFactory extends DAOAbstractFactory {
    public DAO getUserDAO() {
        return UserDAO.getInstance();
    }

    public DAO getCityDAO() {
        return CityDAO.getInstance();
    }

    public DAO getRouterDAO() {
        return RouterDAO.getInstance();
    }

    public DAO getRouterConnectionDAO() {
        return RouterConnectionDAO.getInstance();
    }
}
