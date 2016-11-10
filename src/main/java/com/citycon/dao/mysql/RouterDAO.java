package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.model.systemunits.entities.Entity;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO{
    private static volatile RouterDAO instance;
    private RouterDAO(){}

    public int create(Entity newElement) {
        return 0;
    }

    public int read(Entity readElement) {
        return 0;
    }

    public int update(Entity updateElement) {
        return 0;
    }

    public void delete(Entity deleteElement) {

    }
    public static RouterDAO getInstance() {
        RouterDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RouterDAO();
                }
            }
        }
        return localInstance;
    }
}
