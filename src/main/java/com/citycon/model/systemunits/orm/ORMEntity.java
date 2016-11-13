package com.citycon.model.systemunits.orm;

import com.citycon.dao.DAOAbstractFactory;
import com.citycon.dao.mysql.MySQLDAOFactory;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;

/**
 * Common interface for the ORM entities. ORM entities are the wrappers for
 * the plain entities and aimed to provide CRUD interface for them. ORMEntity 
 * also provides common DAOAbstractFactory for concrete ORM entities.
 * 
 * @author Mike
 * @version 0.4
 */
public abstract class ORMEntity {
	static DAOAbstractFactory daoFactory = new MySQLDAOFactory();
	
	/**
	 * Ask DAO layer to create new entity. Sends the incapsulated Entity object 
	 * to DAO layer that should perform 'create' operation. The incapsulated Entity
	 * should not contain id field.
	 * 
	 * @return id the id of created element.
	 * @throws ORMException if error occurs during create operation
	 */
	public abstract void create() throws ORMException;

	/**
	 * Retrive new Entity from DAO layer. Updates the incapsulated Entity object. If 
	 * there was another Entity saved in this ORMEntity, it will be lost.
	 * 
	 * @return id the id of retrieved element.
	 * @throws ORMException if error occurs during read operation
	 */
    public abstract void read() throws ORMException;

    /**
	 * Ask DAO layer to update entity with current values. Sends the incapsulated 
	 * Entity object to DAO layer that should perform 'update' operation. This field depends on 
	 * concrete DAO class. Setting 'id' field is prefered.
	 * 
	 * @return id the id of updated element.
	 * @throws ORMException if error occurs during update operation
	 */
    public abstract void update() throws ORMException;

    /**
	 * Ask DAO layer to delete current entity. The incapsulated Entity must contain 
	 * at list one field required to find deleting element. This field depends on 
	 * concrete DAO class. Setting 'id' field is prefered.
	 * 
	 * @return id the id of deleted element.
	 * @throws ORMException if error occurs during delete operation
	 */

	public abstract Entity getEntity() throws ORMException;

	/**
	 * @return simple entity from orm object
	 */

    public abstract void delete() throws ORMException;
}