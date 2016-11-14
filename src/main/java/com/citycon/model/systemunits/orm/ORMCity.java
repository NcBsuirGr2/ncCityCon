package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;

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

	public ORMCity() throws ORMException {
		try {
			dao = daoFactory.getCityDAO();
		} catch (DAOException cause) {
			throw new ORMException("Cannot instantiate DAO object", cause);
		}		
	}


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

	public void create() throws ORMException {
		try {
			dao.create(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot create city", cause);
		}
	}
    public void read() throws ORMException {
		try {
			dao.read(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot read city", cause);
		}
    }
    public void update() throws ORMException {
		try {
			dao.update(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot update city", cause);
		}
    }
    public void delete() throws ORMException {
		try {
			dao.delete(city);
		} catch(DAOException cause) {
			throw new ORMException("Cannot delete city", cause);
		}
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
	 * @return id the id of deleted element.
	 * @throws ORMException if error occurs during delete operation
	 */
    public static CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws ORMException, DAOException {
        DAO staticDAO = daoFactory.getCityDAO();
        CityEntity[] cities = null;
        try {
        	Entity[] temp = staticDAO.getPage(page, itemsPerPage, sortBy, asc);
        	cities = (CityEntity[])temp;
        } catch (DAOException cause) {
        	throw new ORMException("Cannot get cities page", cause);
        } catch (ClassCastException exception) {
        	throw new ORMException("Cannot cast Entity[] from DAO to CityEntity[]", exception);
        }
        return cities;
    }
}