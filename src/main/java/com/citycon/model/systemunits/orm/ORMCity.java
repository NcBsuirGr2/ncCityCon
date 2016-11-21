package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;

/**
 * ORM wrapper for the <code>CityEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 0.4
 * @see  CityEntity
 */
public class ORMCity extends ORMEntity {
	DAO dao;
	CityEntity city = new CityEntity();


	//Get-set interface for incapsulated object
	
	public int getId() {
		return city.getId();
	}
	public void setId(int id) {
		city.setId(id);
	}

	public String getName() {
		return city.getName();
	}
	public String getCountryName() {
		return city.getCountryName();
	}
	public int getRoutersNum() {
		return city.getRoutersNum();
	}

	public void setName(String name) {
		city.setName(name);
	}
	public void setCountryName(String countryName) {
		city.setCountryName(countryName);
	}
	public void setRoutersNum(int routersNum) {
		city.setRoutersNum(routersNum);
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

    public CityEntity getEntity()  {
		return city;
	}

     /**
	 * Get any page of cities from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of cities on demanded page.
	 */
    public static CityEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
        DAO staticDAO = daoFactory.getCityDAO();
        return (CityEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
}