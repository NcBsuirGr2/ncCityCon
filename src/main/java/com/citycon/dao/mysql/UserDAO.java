package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO {

    /**
     * @throws InternalDAOException
     */
    private UserDAO() throws InternalDAOException {
        super();
        nameTable = " User ";
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
                                            throws InvalidDataDAOException, InternalDAOException {
        PreparedStatement search_users = null;
        ResultSet resultSet = null;

        ArrayList<UserEntity> users = new ArrayList();
        String search = "select * from" + nameTable + "limit ?,?";

        try {
            search_users = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in get userPage wasn't created", e);
        }

        try {
            search_users.setInt(1, (page-1)*itemsPerPage);
            search_users.setInt(2, itemsPerPage);

            resultSet =  search_users.executeQuery();

            try {
                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Pass"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    users.add(user);
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException("GetPage user failed", e);
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException("Put data to prepare statement in user invalid",e);
        }
        finally {
            if (search_users!=null){
                try {
                    search_users.close();
                } catch (SQLException e) {
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    throw new InternalDAOException(e);
                }
            }
        }

        return users.toArray(new UserEntity[users.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        UserEntity user = null;

        Calendar calendar = Calendar.getInstance();
        Date startDate = new Date(calendar.getTime().getTime());

        String insert = "insert into" + nameTable +
                "(`Login`, `Pass`, `E-mail`, `Name`, `Group`, `create_date`)" +
                " values (?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = null;

        try {
            user = (UserEntity) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in create user wasn't created", e);
        }

        try {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getGroup());
                preparedStatement.setDate(6,  startDate);

                preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException("Create user failed", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new InternalDAOException(e);
                }
            }
        }
    }

    /**
     * @param readElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        UserEntity user = null;

        PreparedStatement search_user = null;
        ResultSet resultSet= null;
        String search = "select * from" + nameTable + "where Login=?";

        try {
            user = (UserEntity) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        if(user.getLogin() != null) {
            try{
                search_user = connection.prepareStatement(search);
            }catch (SQLException e) {
                throw new InternalDAOException("Prepare statement in read user wasn't created", e);
            }

            try {
                search_user.setString(1, user.getLogin());

                resultSet =  search_user.executeQuery();

                if(resultSet.first()) {
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Pass"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                }
                else{
                    throw new InvalidDataDAOException("User not found");
                }
            }catch (SQLException e){
                throw new InternalDAOException("Read user failed", e);
            }
            finally {
                if (search_user!=null){
                    try {
                        search_user.close();
                    } catch (SQLException e) {
                        throw new InternalDAOException(e);
                    }
                }
                if (resultSet!= null){
                    try{
                        resultSet.close();
                    }catch (SQLException e){
                        throw new InternalDAOException(e);
                    }
                }
            }
        }
        else{
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, try Login");
        }
    }


    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        UserEntity user = null;

        PreparedStatement preparedStatement = null;
        String update = "update" + nameTable + "set `Login`=?, `Pass`=?, `E-mail`=?, `Name`=?, `Group`=?, `create_date`=? where `id`=?";

        try {
            user = (UserEntity) updateElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in update user wasn't created", e);
        }

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getGroup());
            preparedStatement.setDate(6, user.getCreateDate());
            preparedStatement.setInt(7, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException("Update user failed", e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new InternalDAOException(e);
                }
            }
        }
    }


    /**
     * @param deleteElement
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {
        UserEntity user = null;

        PreparedStatement preparedStatement = null;
        String delete = "delete from" + nameTable + "where `id`=?";

        try {
            user= (UserEntity) deleteElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(delete);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in delete user wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
                throw new InvalidDataDAOException("Delete user failed", e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    throw new InternalDAOException(e);
                }
            }
        }
    }

    /**
     * @throws InternalDAOException
     */
    public static UserDAO getInstance() throws InternalDAOException {
        return new UserDAO();
    }
}