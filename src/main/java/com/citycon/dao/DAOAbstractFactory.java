package com.citycon.dao;


import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.interfaces.DAO;

/**
 * DAO Abstract Factory provide Object for Business Layer that implement DAO interface.
 *
 * @author Alex
 * @version 2.0
 */
public abstract class DAOAbstractFactory {
    public abstract DAO getUserDAO() throws DAOException;
    public abstract DAO getCityDAO() throws DAOException;
    public abstract DAO getRouterDAO() throws DAOException;
    public abstract DAO getRouterConnectionDAO() throws DAOException;
}
