package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.CountryEntity;

/**
 * Created by Vojts on 09.12.2016.
 */
public interface CitiesStatistic {
    int getCountCountries() throws InternalDAOException;
    CountryEntity maxCityCountry() throws InternalDAOException;
    CountryEntity minCityCountry() throws InternalDAOException;
    CityEntity maxRouterCity() throws InternalDAOException;

}
