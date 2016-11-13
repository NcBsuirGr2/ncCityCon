package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO{
    private static volatile RouterDAO instance;

    private RouterDAO() throws DAOException {
        super();
    }

    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        RouterEntity routers[] = new RouterEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            routers[i] = new RouterEntity();
        }
        return routers;
    }

    public void create(Entity newElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public Entity read(int id) throws DAOException {
        return null;
    }

    public int read(Entity readElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
        return 0;
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
    public static RouterDAO getInstance() throws DAOException {
        RouterDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new RouterDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
