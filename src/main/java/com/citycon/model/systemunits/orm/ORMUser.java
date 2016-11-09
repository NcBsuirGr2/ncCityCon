package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>UserEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.1
 * @see  UserEntity
 */
public class ORMUser extends ORMEntity {
	DAO dao = daoFactory.getUserDAO();
	UserEntity user = new UserEntity();
	
	public int create() {
		return 0;
	}
    public int read() {
    	return 0;
    }
    public int update() {
    	return 0;
    }
    public void delete() {

    }
}