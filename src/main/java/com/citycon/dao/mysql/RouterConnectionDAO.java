package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterConnectionDAO extends MySQLDAO {
    private static volatile RouterConnectionDAO instance;

    private RouterConnectionDAO(){}

    public List<Entity> getList(int begin, int count) {
        return new ArrayList<Entity>();
    }

    public int create(Entity newElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public int read(Entity readElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public int update(Entity updateElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
    }

    public void delete(Entity deleteElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }
    public static RouterConnectionDAO getInstance() {
        RouterConnectionDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterConnectionDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new RouterConnectionDAO();
                }
            }
        }
        return localInstance;
    }
}
