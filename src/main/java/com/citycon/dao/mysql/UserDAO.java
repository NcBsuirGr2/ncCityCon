package com.citycon.dao.mysql;

import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;

/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO {
    private static volatile UserDAO instance;

    private UserDAO() throws DAOException {
        super();
    }

    public UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        UserEntity users[] = new UserEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            users[i] = new UserEntity();
        }
        return users;
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
    public static UserDAO getInstance() throws DAOException {
        UserDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (UserDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new UserDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
