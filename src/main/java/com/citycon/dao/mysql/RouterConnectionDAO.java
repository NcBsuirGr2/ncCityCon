package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterConnectionDAO extends MySQLDAO {
    private static volatile RouterConnectionDAO instance;

    private RouterConnectionDAO() throws DAOException {
        super();
    }

    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        RouterConnectionEntity routerConnections[] = new RouterConnectionEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            routerConnections[i] = new RouterConnectionEntity();
        }
        return routerConnections;
    }

    public void create(Entity newElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public void read(Entity readElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public void update(Entity updateElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }
    public static RouterConnectionDAO getInstance() throws DAOException {
        RouterConnectionDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterConnectionDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new RouterConnectionDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
