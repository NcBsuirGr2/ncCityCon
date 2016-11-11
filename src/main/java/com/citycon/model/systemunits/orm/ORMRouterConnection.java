package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;

/**
 * ORM wrapper for the <code>RouterConnectionEntity</code>. This class must be used 
 * in servlets to manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.3
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

	public int create() throws ORMException {
		int connectionId = -1;
		try {
			connectionId = dao.create(routerConnection);
		} catch(DAOException cause) {
			throw new ORMException("Cannot create connection", cause);
		}
		return connectionId;
	}
    public int read() throws ORMException {
    	int connectionId = -1;
		try {
			connectionId = dao.read(routerConnection);
		} catch(DAOException cause) {
			throw new ORMException("Cannot read connection", cause);
		}
		return connectionId;
    }
    public int update() throws ORMException {
    	int connectionId = -1;
		try {
			connectionId = dao.update(routerConnection);
		} catch(DAOException cause) {
			throw new ORMException("Cannot update connection", cause);
		}
		return connectionId;
    }
    public void delete() throws ORMException {
		try {
			dao.delete(routerConnection);
		} catch(DAOException cause) {
			throw new ORMException("Cannot delete connection", cause);
		}
    }
}	