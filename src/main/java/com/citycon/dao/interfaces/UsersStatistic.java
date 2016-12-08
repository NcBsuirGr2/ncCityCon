package com.citycon.dao.interfaces;

import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.systemunits.entities.UserEntity;

import java.util.ArrayList;

/**
 * Created by Vojts on 08.12.2016.
 */
public interface UsersStatistic {
    ArrayList<UserEntity> first_users() throws InvalidDataDAOException, InternalDAOException;
    ArrayList<UserEntity> last_users() throws InvalidDataDAOException, InternalDAOException;
}
