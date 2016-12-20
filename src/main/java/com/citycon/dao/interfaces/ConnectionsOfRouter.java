package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;

/**
 * Created by Vojts on 20.11.2016.
 */
public interface ConnectionsOfRouter {
    RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input , RouterEntity router)
            throws InvalidDataDAOException, InternalDAOException;

    int count_element(RouterEntity router) throws InvalidDataDAOException, InternalDAOException;
}
