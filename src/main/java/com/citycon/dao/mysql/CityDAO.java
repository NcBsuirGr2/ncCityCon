package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.CityEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */

public class CityDAO extends MySQLDAO {
    private static volatile CityDAO instance;

    private CityDAO(){}

    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        CityEntity cities[] = new CityEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            cities[i] = new CityEntity();
        }
        return cities;
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
