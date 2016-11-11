package com.citycon.dao;
import com.citycon.model.systemunits.entities.*;

import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    List<Entity> getList(int begin, int count) throws DAOException;
    int create(Entity newElement) throws DAOException;
    int read(Entity readElement) throws DAOException;
    int update(Entity updateElement) throws DAOException;
    void delete(Entity deleteElement) throws DAOException;
}
