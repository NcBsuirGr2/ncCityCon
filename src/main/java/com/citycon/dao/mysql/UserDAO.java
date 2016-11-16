package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
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
     * @throws DAOException
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
     * @throws InternalDAOException
     */
    public UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) 
                                            throws InvalidDataDAOException, InternalDAOException {

        if(false) {
            throw new InvalidDataDAOException();
        }

        ArrayList<UserEntity> users = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_users = connection.prepareStatement(search);
            search_users.setInt(1, (page-1)*itemsPerPage);
            search_users.setInt(2, itemsPerPage);

            ResultSet resultSet =  search_users.executeQuery();

            while(resultSet.next()) {
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
            resultSet.close();
            search_users.close();
        }catch (SQLException e){
            throw new InternalDAOException("GetPage user failed", e);
        }
        return users.toArray(new UserEntity[users.size()]);
    }

    /**
     * @param newElement
     * @throws InternalDAOException, DublicateKeyDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        if(false) {
            throw new InvalidDataDAOException();
        }
        try{
            UserEntity user= (UserEntity) newElement;
            Calendar calendar = Calendar.getInstance();
            Date startDate = new Date(calendar.getTime().getTime());

            String insert = "insert into" + nameTable +
                    "(`Login`, `Pass`, `E-mail`, `Name`, `Group`, `create_date`)" +
                    " values (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            try {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getGroup());
                preparedStatement.setDate(6,  startDate);

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e){
                throw new DublicateKeyDAOException("Create user failed", e);
            }

        }catch (SQLException e){
            throw new InternalDAOException("Create user failed", e);
        }
    }

    /**
     * @param readElement
     * @throws NotFoundDAOException, InternalDAOException, InvalidDataDAOException
     */
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        
        if(false) {
            throw new InvalidDataDAOException();
        }
        UserEntity user = (UserEntity)readElement;
        String field = "";
        String value = "";
        try {
            if(user.getId() != 0) {
                field = "id";
                value = String.valueOf(user.getId());
            }
            else if(user.getLogin() != null){
                field = "Login";
                value = "'" + user.getLogin() + "'";
            }
            else {
                throw new InvalidDataDAOException("For reading user incorrectly chosen field, try id or login");
            }

            String search = "select * from" + nameTable + "where " + field + "=" + value;

            PreparedStatement search_user = connection.prepareStatement(search);
            try {
                ResultSet resultSet =  search_user.executeQuery();

                if(resultSet.first()) {
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Pass"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                }
              
                resultSet.close();
                search_user.close();
            } catch (SQLException e){
                throw new InvalidDataDAOException("Read user failed", e);
            }


        }catch (SQLException e){
            throw new InternalDAOException("Read user failed", e);
        }
    }

    /**
     * @param updateElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        if (false) {
            throw new DublicateKeyDAOException();
        }
        try {
            UserEntity user= (UserEntity) updateElement;

            String update = "update" + nameTable + "set `Login`=?, `Pass`=?, `E-mail`=?, `Name`=?, `Group`=?, `create_date`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            try {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getGroup());
                preparedStatement.setDate(6,  user.getCreateDate());
                preparedStatement.setInt(7, user.getId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Update user failed", e);
            }

        } catch (SQLException e) {
            throw new InternalDAOException("Update user failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws InternalDAOException, InvalidDataDAOException
     */
    public void delete(Entity deleteElement) throws InternalDAOException, InvalidDataDAOException {
        try {
            UserEntity user= (UserEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            try {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Delete user failed", e);
            }

        } catch (SQLException e) {
            throw new InternalDAOException("Delete user failed", e);
        }
    }
    /**
     * @throws InternalDAOException
     */
    public static UserDAO getInstance() throws InternalDAOException {
        return new UserDAO();
    }
}