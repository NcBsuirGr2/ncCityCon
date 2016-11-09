package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>RouterConnectionEntity</code>. This class must be used 
 * in servlets to manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.2
 * @see  RouterConnectionEntity
 */
public class ORMRouterConnection extends ORMEntity {
	DAO dao = daoFactory.getRouterConnectionDAO();
	RouterConnectionEntity routerConnection = new RouterConnectionEntity();


	//Get-set interface for incapsulated object
	
	public int getId() {
		return routerConnection.getId();
	}
	public void setId(int id) {
		routerConnection.setId(id);
	}

	public String getFirstRouterId() {
		return routerConnection.getFirstRouterId();
	}
	public String getSecondRouterId() {
		return routerConnection.getSecondRouterId();
	}

	public void setFirstRouterId(String firstRouterId) {
		routerConnection.setFirstRouterId(firstRouterId);
	}
	public void setSecondRouterId(String secondRouterId) {
		routerConnection.setSecondRouterId(secondRouterId);
	}


	//ORM interface for incapsulated object

	public int create() {
		return dao.create(routerConnection);
	}
    public int read() {
    	return dao.read(routerConnection);
    }
    public int update() {
    	return dao.update(routerConnection);
    }
    public void delete() {
    	dao.delete(routerConnection);
    }
}	