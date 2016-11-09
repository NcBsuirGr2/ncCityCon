package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>CityEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.2
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

	public int create() {
		return dao.create(city);
	}
    public int read() {
    	return dao.read(city);
    }
    public int update() {
    	return dao.update(city);
    }
    public void delete() {
    	dao.delete(city);
    }
}