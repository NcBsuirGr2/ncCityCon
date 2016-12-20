package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.interfaces.ConnectionsOfRouter;
import com.citycon.dao.interfaces.ConnectionsOfCity;
import com.citycon.dao.exceptions.DAOException;
import java.util.Set;

/**
 * ORM wrapper for the <code>RouterConnectionEntity</code>. This class must be used 
 * in servlets to manipulate CRUD operations for the plain entity through the concrete DAO.
 * DAO object is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 1.0
 * @see  RouterConnectionEntity, ORMEntity
 */
public class ORMRouterConnection extends ORMEntity {
	DAO dao;
	RouterConnectionEntity routerConnection = new RouterConnectionEntity();
	
	//Get-set interface for incapsulated object
	public RouterConnectionEntity getEntity() {
		return routerConnection;
	}

	public void setEntity(RouterConnectionEntity routerConnection) {
		this.routerConnection = routerConnection;
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

	public static Set<String> getSortingParameters() throws DAOException {
		DAO staticDAO = daoFactory.getRouterConnectionDAO();
		return staticDAO.getSortingParameters();
	}


    /**
	 * Get any page of connections from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of connections on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {

        DAO staticDAO = daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
    /**
	 * Get any page of connections for concrete city from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of connections on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
						String sortBy, boolean asc, CityEntity city) throws DAOException {

        ConnectionsOfCity staticDAO = (ConnectionsOfCity)daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc, city);
    }
    /**
	 * Get any page of routers for concrete router from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of routers on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static RouterConnectionEntity[] getPage(int page, int itemsPerPage, 
						String sortBy, boolean asc, RouterEntity router) throws DAOException {

        ConnectionsOfRouter staticDAO = (ConnectionsOfRouter)daoFactory.getRouterConnectionDAO();
        return (RouterConnectionEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc, router);
    }
    /**
     *	Retrieves total number of connetions from DAO layer.
     * 
     * @return int 				nuber of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element();
    }
    /**
     *	Retrieves total number of connetions for concrete city from DAO layer.
     * 
     * @return int 				nuber of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount(CityEntity city) throws DAOException {
    	ConnectionsOfCity staticDAO = (ConnectionsOfCity)daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element(city);
    }
    /**
     *	Retrieves total nuber of connetions for concrete router from DAO layer.
     * 
     * @return int 				number of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount(RouterEntity router) throws DAOException {
    	ConnectionsOfRouter staticDAO = (ConnectionsOfRouter)daoFactory.getRouterConnectionDAO();
        return staticDAO.count_element(router);
    }
}	