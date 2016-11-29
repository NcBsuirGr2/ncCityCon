package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>CityEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 1.0
 * @see  CityEntity, ORMEntity, DAO
 */
public class ORMCity extends ORMEntity {
	DAO dao;
	CityEntity city = new CityEntity();


	//Get-set interface for incapsulated object
	public CityEntity getEntity() {
		return city;
	}

	public void setEntity(CityEntity city) {
		this.city = city;
	}


	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getCityDAO();
		}
		dao.create(city);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getCityDAO();
		}
		dao.read(city);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getCityDAO();
		}
		dao.update(city);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getCityDAO();
		}
		dao.delete(city);
    }

     /**
	 * Get any page of cities from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage 	number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of cities on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static CityEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getCityDAO();
        return (CityEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }

	/**
	 *	Retrieves total number of Cities from DAO layer.
	 *
	 * @return int number of Cities
	 * @throws DAOException if any DAO internal error occur
	 */
	public static int getCount() throws DAOException {
		DAO staticDAO = daoFactory.getCityDAO();
		return staticDAO.count_element();
	}



}