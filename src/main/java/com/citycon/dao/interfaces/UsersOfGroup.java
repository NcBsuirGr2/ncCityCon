package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;

/**
 * Created by Vojts on 08.12.2016.
 */
public interface UsersOfGroup {
    int count_element(String group) throws InvalidDataDAOException, InternalDAOException;
}
