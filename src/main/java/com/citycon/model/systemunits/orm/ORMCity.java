package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;

/**
 * ORM wrapper for the <code>CityEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.3
 * @see  CityEntity
 */
public class ORMCity extends ORMEntity {
	DAO dao = daoFactory.getCityDAO();
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

	public void setName(String name) {
		city.setName(name);
	}
	public void setCountryName(String countryName) {
		city.setCountryName(countryName);
	}


	//ORM interface for incapsulated object

	public int create() throws ORMException {
		int cityId = -1;
		try {
			cityId = dao.create(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot create city", cause);
		}
		return cityId;
	}
    public int read() throws ORMException {
    	int cityId = -1;
		try {
			cityId = dao.read(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot read city", cause);
		}
		return cityId;
    }
    public int update() throws ORMException {
    	int cityId = -1;
		try {
			cityId = dao.update(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot update city", cause);
		}
		return cityId;
    }
    public void delete() throws ORMException {
		try {
			dao.delete(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot delete city", cause);
		}
    }
}