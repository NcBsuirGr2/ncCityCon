package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>RouterConnectionEntity</code>. This class must be used 
 * in servlets to manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 0.4
 * @see  RouterConnectionEntity
 */
public class ORMRouterConnection extends ORMEntity {
	DAO dao;
	RouterConnectionEntity routerConnection = new RouterConnectionEntity();

	public ORMRouterConnection() throws DAOException {
		dao = daoFactory.getRouterConnectionDAO();
	}


	//Get-set interface for incapsulated object
	
	public int getId() {
		return routerConnection.getId();
	}
	public void setId(int id) {
		routerConnection.setId(id);
	}

	public int getFirstRouterId() {
		return routerConnection.getFirstRouterId();
	}
	public int getSecondRouterId() {
		return routerConnection.getSecondRouterId();
	}
	public void setFirstRouterId(int firstRouterId) {
		routerConnection.setFirstRouterId(firstRouterId);
	}
	public void setSecondRouterId(int secondRouterId) {
		routerConnection.setSecondRouterId(secondRouterId);
	}


	//ORM interface for incapsulated object

	public void create() throws DAOException {
		dao.create(routerConnection);
	}
    public void read() throws DAOException {
		dao.read(routerConnection);
    }
    public void update() throws DAOException {
		dao.update(routerConnection);
    }
    public void delete() throws DAOException {
		dao.delete(routerConnection);
    }

    public RouterConnectionEntity getEntity()  {
		return routerConnection;
	}

    /**
	 * Get any page of connections from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of connecions on demanded page.
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
}	