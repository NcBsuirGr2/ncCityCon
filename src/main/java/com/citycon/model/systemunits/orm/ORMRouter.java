package com.citycon.model.systemunits.orm;

import com.citycon.dao.mysql.RoutersOfCity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.mysql.RouterDAO;
import com.citycon.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>RouterEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 0.4
 * @see  RouterEntity
 */
public class ORMRouter extends ORMEntity {
	DAO dao;
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
	public int getCityId() {
		return router.getCityId();
	}
	public boolean isActive() {
		return router.isActive();
	}
	public String getCityName() {
		return router.getCityName();
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
	public void IsActive(boolean isActive) {
		router.isActive(isActive);
	}
	public void setCityId(Integer sityId) {
		router.setCityId(sityId);
	}
	public void setCityName(String cityName) {
		router.setCityName(cityName);
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
		dao.update(router);
    }
    
   	public RouterEntity getEntity()  {
		return router;
	}

     /**
	 * Get any page of routers from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of routers on demanded page.
	 */
    public static RouterEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getRouterDAO();
        return (RouterEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }

	/**
	 * @param page
	 * @param itemsPerPage
	 * @param sortBy
	 * @param asc
	 * @param city
	 * @return
	 * @throws DAOException
	 */
	public static RouterEntity[] getPage(int page, int itemsPerPage,
										 String sortBy, boolean asc, CityEntity city) throws DAOException {
		RoutersOfCity staticDAORouters = (RoutersOfCity) daoFactory.getRouterDAO();
		return staticDAORouters.getPage(page, itemsPerPage, sortBy, asc, city);
	}

	 /**
     *	Retrieves total number of routers from DAO layer.
     * 
     * @return int number of connections
     * @throws DAOException if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getRouterDAO();
        return staticDAO.count_element();
    }

     /**
     *	Retrieves number of routers for concrete city from DAO layer.
     * 
     * @return int number of connections
     * @throws DAOException if any DAO internal error occur
     */
    public static int getCount(CityEntity city) throws DAOException {
    	RouterDAO staticDAO = (RouterDAO)daoFactory.getRouterDAO();
        return staticDAO.count_element(city);
    }
}