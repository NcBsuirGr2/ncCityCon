package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>RouterEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.1
 * @see  RouterEntity
 */
public class ORMRouter extends ORMEntity {
	DAO dao = daoFactory.getRouterDAO();
	RouterEntity router = new RouterEntity();
	
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