package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;

/**
 * Created by Vojts on 24.11.2016.
 */
public interface CitiesOfCountry {
    CityEntity[] getCities(String country) throws InternalDAOException, InvalidDataDAOException;
}
