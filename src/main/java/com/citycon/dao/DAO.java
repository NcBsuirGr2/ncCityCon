package com.citycon.dao;
import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;

import java.util.DuplicateFormatFlagsException;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException;
    void read(Entity readElement) throws NotFoundDAOException, InternalDAOException, InvalidDataDAOException;
    void update(Entity updateElement) throws InvalidDataDAOException, InternalDAOException;
    void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException;
    Entity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws InternalDAOException;
}
