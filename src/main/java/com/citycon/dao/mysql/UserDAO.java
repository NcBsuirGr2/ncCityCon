package com.citycon.dao.mysql;

import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO {
    private static volatile UserDAO instance;

    private UserDAO() throws DAOException {
        super();
    }

    public UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        if (false) {
            throw new DAOException("Dummy");
        }
        UserEntity users[] = new UserEntity[itemsPerPage];
        for (int i = 0; i< itemsPerPage; ++i) {
            users[i] = new UserEntity();
        }
        return users;
    }

    public void create(Entity newElement) throws DAOException {
        try{
            UserEntity user= (UserEntity) newElement;

            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            String insert = "insert into `User` " +
                    "(`Login`, `Pass`, `E-mail`, `Name`, `Group`, `create_date`)" +
                    " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, "guest");
            preparedStatement.setDate(6,  startDate);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (SQLException e){
            throw new DAOException("Dummy");
        }
    }

    public void read(Entity readElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public void update(Entity updateElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        if(false) {
            throw new DAOException("Dummy");
        }
    }
    public static UserDAO getInstance() throws DAOException {
        UserDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (UserDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new UserDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
