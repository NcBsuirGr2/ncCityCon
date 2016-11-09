package com.citycon.model.systemunits.orm;

import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.mysql.MySQLDAOFactory;

/**
 * Common interface for the ORM entities. ORM entities are the wrappers for
 * the plain entities and aimed to provide CRUD interface for them. ORMEntity 
 * also provides common DAOAbstractFactory for concrete ORM entities.
 * 
 * @author Mike
 * @version 1.1
 */
public abstract class ORMEntity {
	DAOAbstractFactory daoFactory = new MySQLDAOFactory();
	/**
	 * Ask DAO layer to create new entity. Sends the incapsulated Entity object 
	 * to DAO layer that should call INSERT operation. The incapsulated Entity
	 * should not contains id field.
	 * 
	 * @return id the id of created element.
	 */
	public abstract int create();
    public abstract int read();
    public abstract int update();
    public abstract void delete();
}