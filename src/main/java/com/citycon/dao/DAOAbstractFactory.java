package com.citycon.dao;


import com.citycon.dao.exceptions.DAOException;
/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class DAOAbstractFactory {
    public abstract DAO getUserDAO() throws DAOException;
    public abstract DAO getCityDAO() throws DAOException;
    public abstract DAO getRouterDAO() throws DAOException;
    public abstract DAO getRouterConnectionDAO() throws DAOException;
}
