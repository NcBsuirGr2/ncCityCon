package com.citycon.dao.mysql;

import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ArrayList<UserEntity> users = new ArrayList();
        try {
            String search = "select * from `User` limit ?,?";

            PreparedStatement search_users = connection.prepareStatement(search);
            search_users.setInt((int)1, page*itemsPerPage);
            search_users.setInt((int)2, itemsPerPage);

            ResultSet resultSet =  search_users.executeQuery();

            while(resultSet.next()) {
                UserEntity user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("Login"));
                user.setPassword(resultSet.getString("Pass"));
                user.setEmail(resultSet.getString("E-mail"));
                user.setName(resultSet.getString("Name"));
                users.add(user);
            }
            resultSet.close();
            search_users.close();
        }catch (SQLException e){
            throw new DAOException("GetPage user failed\n" + e.toString());
        }
        return (UserEntity[]) users.toArray();
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
            throw new DAOException("Create user failed");
        }
    }

    public Entity read(int id) throws DAOException {
        UserEntity user = null;
        try {
            String search = "select * from `User` when id=?";

            PreparedStatement search_user = connection.prepareStatement(search);
            search_user.setInt((int)1, id);

            ResultSet resultSet =  search_user.executeQuery();

            while(resultSet.next()) {
                user = new UserEntity();
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("Login"));
                user.setPassword(resultSet.getString("Pass"));
                user.setEmail(resultSet.getString("E-mail"));
                user.setName(resultSet.getString("Name"));
            }
            resultSet.close();
            search_user.close();
        }catch (SQLException e){
            throw new DAOException("Read user failed\n" + e.toString());
        }
        return user;
    }

    public void update(Entity updateElement) throws DAOException {
        try {
            UserEntity user= (UserEntity) updateElement;

            Calendar calendar = Calendar.getInstance();
            java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());

            String update = "update `User` set `Login`=?, `Pass`=?, `E-mail`=?, `Name`=?, `Group`=?, `create_date`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, "guest");
            preparedStatement.setDate(6,  startDate);
            preparedStatement.setInt(7, user.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Update user failed");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        try {
            UserEntity user= (UserEntity) deleteElement;

            String delete = "delete from `User` where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete user failed");
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
