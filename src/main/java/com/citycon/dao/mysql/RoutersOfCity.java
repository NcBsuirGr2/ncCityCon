package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.RouterEntity;

/**
 * Created by Vojts on 16.11.2016.
 */
public interface RoutersOfCity {
    RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, int cityId)
            throws InvalidDataDAOException, InternalDAOException;
}
