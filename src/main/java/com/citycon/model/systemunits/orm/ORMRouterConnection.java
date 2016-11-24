package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.interfaces.ConnectionsOfRouter;
import com.citycon.dao.interfaces.ConnectionsOfCity;
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
	public String getFirstRouterSN() {
		return routerConnection.getFirstRouterSN();
	}
	public String getFirstRouterCityName() {
		return routerConnection.getFirstRouterCityName();
	}
	public String getFirstRouterCountryName() {
		return routerConnection.getFirstRouterCountryName();
	}
	public int getSecondRouterId() {
		return routerConnection.getSecondRouterId();
	}
	public String getSecondRouterSN() {
		return routerConnection.getSecondRouterSN();
	}
	public String getSecondRouterCityName() {
		return routerConnection.getSecondRouterCityName();
	}
	public String getSecondRouterCountryName() {
		return routerConnection.getSecondRouterCountryName();
	}

	public void setFirstRouterId(int firstRouterId) {
		routerConnection.setFirstRouterId(firstRouterId);
	}
	public void setFirstRouterSN(String firstRouterSN) {
		routerConnection.setFirstRouterSN(firstRouterSN);
	}
	public void setFirstRouterCityName(String firstRouterCityName) {
		routerConnection.setFirstRouterCityName(firstRouterCityName);
	}
	public void setFirstRouterCountryName(String firstRouterCountryName) {
		routerConnection.setFirstRouterCountryName(firstRouterCountryName);
	}
	public void setSecondRouterId(int secondRouterId) {
		routerConnection.setSecondRouterId(secondRouterId);
	}
	public void setSecondRouterSN(String secondRouterSN) {
		routerConnection.setSecondRouterSN(secondRouterSN);
	}
	public void setSecondRouterCityName(String secondRouterCityName) {
		routerConnection.setSecondRouterCityName(secondRouterCityName);
	}
	public void setSecondRouterCountryName(String secondRouterCountryName) {
		routerConnection.setSecondRouterCountryName(secondRouterCountryName);
	}


	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getRouterConnectionDAO();
		}
		dao.create(routerConnection);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterConnectionDAO();
		}
		dao.read(routerConnection);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterConnectionDAO();
		}
		dao.update(routerConnection);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterConnectionDAO();
		}
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
    /**
	 * Get any page of connections for concrete city from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of connecions on demanded page.
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc, CityEntity city) throws DAOException {
        ConnectionsOfCity staticDAO = (ConnectionsOfCity)daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc, city);
    }
    /**
	 * Get any page of connections for concrete router from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of connecions on demanded page.
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc, RouterEntity router) throws DAOException {
        ConnectionsOfRouter staticDAO = (ConnectionsOfRouter)daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc, router);
    }
    /**
     *	Retrieves total number of connetions from DAO layer.
     * 
     * @return int nuber of connections
     * @throws DAOException if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element();
    }
    /**
     *	Retrieves total number of connetions for concrete city from DAO layer.
     * 
     * @return int nuber of connections
     * @throws DAOException if any DAO internal error occur
     */
    public static int getCount(CityEntity city) throws DAOException {
    	ConnectionsOfCity staticDAO = (ConnectionsOfCity)daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element(city);
    }
    /**
     *	Retrieves total nuber of connetions for concrete router from DAO layer.
     * 
     * @return int number of connections
     * @throws DAOException if any DAO internal error occur
     */
    public static int getCount(RouterEntity router) throws DAOException {
    	ConnectionsOfRouter staticDAO = (ConnectionsOfRouter)daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element(router);
    }
}	