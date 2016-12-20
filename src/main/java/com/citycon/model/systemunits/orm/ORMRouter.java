package com.citycon.model.systemunits.orm;

import com.citycon.dao.interfaces.RoutersOfCity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.mysql.RouterDAO;
import com.citycon.dao.exceptions.DAOException;

import java.util.Set;

/**
 * ORM wrapper for the <code>RouterEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 1.0
 * @see  RouterEntity, ORMEntity
 */
public class ORMRouter extends ORMEntity {
	DAO dao;
	RouterEntity router = new RouterEntity();

	//Get-set interface for incapsulated object
	public RouterEntity getEntity() {
		return router;
	}

	public void setEntity(RouterEntity router) {
		this.router = router;
	}

	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.create(router);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.read(router);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.update(router);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getRouterDAO();
		}
		dao.delete(router);
    }

	public static Set<String> getSortingParameters() throws DAOException {
		DAO staticDAO = daoFactory.getRouterDAO();
		return staticDAO.getSortingParameters();
	}

     /**
	 * Get any page of routers from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of routers on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static RouterEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getRouterDAO();
        return (RouterEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }

	/**
	 * Get any page of routers for concrete city from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of routers on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
	public static RouterEntity[] getPage(int page, int itemsPerPage,
				 String sortBy, boolean asc, CityEntity city) throws DAOException {

		RoutersOfCity staticDAORouters = (RoutersOfCity) daoFactory.getRouterDAO();
		return staticDAORouters.getPage(page, itemsPerPage, sortBy, asc, city);
	}

	 /**
     *	Retrieves total number of routers from DAO layer.
     * 
     * @return int 				number of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getRouterDAO();
        return staticDAO.count_element();
    }

     /**
     *	Retrieves number of routers for concrete city from DAO layer.
     * 
     * @return int 				number of connections
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount(CityEntity city) throws DAOException {
    	RouterDAO staticDAO = (RouterDAO)daoFactory.getRouterDAO();
        return staticDAO.count_element(city);
    }
}