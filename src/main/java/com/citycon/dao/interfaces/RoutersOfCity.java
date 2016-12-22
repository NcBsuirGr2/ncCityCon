package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;

/**
 * Provided connections of chosen city.
 *
 * @author Alex
 * @version 2.0
 */
public interface RoutersOfCity {

    RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;

    int count_element(String search_input, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;
}
