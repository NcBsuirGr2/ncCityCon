package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */

public class CityDAO extends MySQLDAO {
    private static volatile CityDAO instance;

    private CityDAO(){}

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
    
    public static CityDAO getInstance() {
        CityDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (CityDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new CityDAO();
                }
            }
        }
        return localInstance;
    }
}
