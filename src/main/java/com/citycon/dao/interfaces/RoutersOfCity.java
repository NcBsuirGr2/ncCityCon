package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;

/**
 * Created by Vojts on 16.11.2016.
 */
public interface RoutersOfCity {
    void create(RouterEntity newElement, CityEntity cityEntity)
            throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException;

    void update(RouterEntity updateElement, CityEntity cityEntity)
            throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException;

    RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;

    int count_element(CityEntity city)
            throws InvalidDataDAOException, InternalDAOException;
}
