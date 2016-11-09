package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.DAO;

/**
 * ORM wrapper for the <code>CityEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.1
 * @see  CityEntity
 */
public class ORMCity extends ORMEntity {
	DAO dao = daoFactory.getCityDAO();
	CityEntity city = new CityEntity();
	
	public int create() {
		return 0;
	}
    public int read() {
    	return 0;
    }
    public int update() {
    	return 0;
    }
    public void delete() {

    }
}