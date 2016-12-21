package com.citycon.dao.interfaces;
import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;

import java.util.Set;

/**
 * Created by Vojts on 09.11.2016.
 */
public interface DAO {
    void create(Entity newElement) throws DublicateKeyDAOException, 
    							InvalidDataDAOException, InternalDAOException;

    void read(Entity readElement) throws InvalidDataDAOException, InternalDAOException;

    void update(Entity updateElement) throws DublicateKeyDAOException, 
    							InvalidDataDAOException, InternalDAOException;

    void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException;

    Entity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input)
		throws InvalidDataDAOException, InternalDAOException;

    int count_element(String search_input) throws InternalDAOException, InvalidDataDAOException;

    Set<String> getSortingParameters();

}
