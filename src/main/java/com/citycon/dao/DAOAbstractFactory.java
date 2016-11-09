package com.citycon.dao;

/**
 * Created by Vojts on 09.11.2016.
 */
public abstract class DAOAbstractFactory {
    public abstract DAO getUserDAO();
    public abstract DAO getCityDAO();
    public abstract DAO getRouterDAO();
    public abstract DAO getRouterConnectionDAO();
}
