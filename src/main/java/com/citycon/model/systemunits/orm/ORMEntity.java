package com.citycon.model.systemunits.orm;

import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.mysql.MySQLDAOFactory;

/**
 * Common interface for the ORM entities. ORM entities are the wrappers for
 * the plain entities and aimed to provide CRUD interface for them. ORMEntity 
 * also provides common DAOAbstractFactory for concrete ORM entities.
 * 
 * @author Mike
 * @version 1.2
 */
public abstract class ORMEntity {
	DAOAbstractFactory daoFactory = new MySQLDAOFactory();
	
	/**
	 * Ask DAO layer to create new entity. Sends the incapsulated Entity object 
	 * to DAO layer that should perform 'create' operation. The incapsulated Entity
	 * should not contain id field.
	 * 
	 * @return id the id of created element.
	 */
	public abstract int create();

	/**
	 * Retrive new Entity from DAO layer. If there was another Entity saved in
	 * this ORMEntity, it will be lost.
	 * 
	 * @return id the id of retrieved element.
	 */
    public abstract int read();

    /**
	 * Ask DAO layer to update entity with current values. Sends the incapsulated 
	 * Entity object to DAO layer that should perform 'update' operation. This field depends on 
	 * concrete DAO class. Setting 'id' field is prefered.
	 * 
	 * @return id the id of updated element.
	 */
    public abstract int update();

    /**
	 * Ask DAO layer to delete current entity. The incapsulated Entity must contain 
	 * at list one field required to find deleting element. This field depends on 
	 * concrete DAO class. Setting 'id' field is prefered.
	 * 
	 * @return id the id of deleted element.
	 */
    public abstract void delete();
}