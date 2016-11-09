package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>RouterConnectionEntity</code>. This class must be used 
 * in servlets to manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.1
 * @see  RouterConnectionEntity
 */
public class ORMRouterConnection extends ORMEntity {
	DAO dao = daoFactory.getRouterConnectionDAO();
	RouterConnectionEntity routerConnection = new RouterConnectionEntity();
	
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