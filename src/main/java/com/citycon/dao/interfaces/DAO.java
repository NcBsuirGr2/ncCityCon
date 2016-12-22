package com.citycon.dao.interfaces;
import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;

import java.util.Set;

/**
 * Interface for DAO wrapper for the <code>ORMCity</code>. This class must be used in business logic for
 * deliver CRUD operations for the plain ORMEntity. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass
 * <code>MySQLDAO</code>.
 *
 * @author Alex
 * @version 2.0
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
