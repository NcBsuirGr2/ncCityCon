package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;

/**
 * Created by Vojts on 09.12.2016.
 */
public interface RouterConnectionStatistic {
    int countPorts() throws InvalidDataDAOException, InternalDAOException;
}
