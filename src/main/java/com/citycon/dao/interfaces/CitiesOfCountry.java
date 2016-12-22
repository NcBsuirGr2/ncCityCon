package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;

/**
 * Provided cities of concrete country.
 *
 * @author Alex
 * @version 2.0
 */
public interface CitiesOfCountry {
    CityEntity[] getCities(String country) throws InternalDAOException, InvalidDataDAOException;
    String[] getCountries();
}
