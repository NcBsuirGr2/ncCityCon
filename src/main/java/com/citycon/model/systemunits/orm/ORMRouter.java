package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;

/**
 * ORM wrapper for the <code>RouterEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.3
 * @see  RouterEntity
 */
public class ORMRouter extends ORMEntity {
	DAO dao = daoFactory.getRouterDAO();
	RouterEntity router = new RouterEntity();
	

	//Get-set interface for incapsulated object
	
	public int getId() {
		return router.getId();
	}
	public void setId(int id) {
		router.setId(id);
	}

	public String getName() {
		return router.getName();
	}
	public String getSN() {
		return router.getSN();
	}
	public int getPortsNum() {
		return router.getPortsNum();
	}
	public boolean isActive() {
		return router.isActive();
	}
	public void setName(String name) {
		router.setName(name);
	}
	public void setSN(String SN) {
		router.setSN(SN);
	}
	public void setPortsNum(int portsNum) {
		router.setPortsNum(portsNum);
	}
	public void setIsActive(boolean isActive) {
		router.setIsActive(isActive);
	}

	//ORM interface for incapsulated object

	public int create() throws ORMException {
		int routerId = -1;
		try {
			routerId = dao.create(router);
		} catch(DAOException cause) {
			throw new ORMException("Cannot create router", cause);
		}
		return routerId;
	}
    public int read() throws ORMException {
    	int routerId = -1;
		try {
			routerId = dao.read(router);
		} catch(DAOException cause) {
			throw new ORMException("Cannot read router", cause);
		}
		return routerId;
    }
    public int update() throws ORMException {
    	int routerId = -1;
		try {
			routerId = dao.update(router);
		} catch(DAOException cause) {
			throw new ORMException("Cannot update router", cause);
		}
		return routerId;
    }
    public void delete() throws ORMException {
		try {
			dao.update(router);
		} catch(DAOException cause) {
			throw new ORMException("Cannot delete router", cause);
		}
    }
}