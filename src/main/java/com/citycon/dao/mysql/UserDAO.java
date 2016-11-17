package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.LoggerFactory;

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
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.UserDAO");
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
            logger.warn("Prepare statement in get userPage wasn't created", e);
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

                logger.trace(String.format("getPage user on %d page", page));
            }catch (SQLException e){
                logger.info("GetPage user failed", e);
                throw new InvalidDataDAOException("GetPage user failed", e);
            }
        }catch (SQLException e){
            logger.info("Put data to prepare statement in user invalid",e);
            throw new InvalidDataDAOException("Put data to prepare statement in user invalid",e);
        }
        finally {
            if (search_users!=null){
                try {
                    search_users.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in getPage false",e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in getPage false",e);
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
            logger.info("Enter parameters in create are invalid", e);
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            logger.warn("Prepare statement in create user wasn't created", e);
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

            logger.trace(String.format("create user %s", user.getLogin()));
        } catch (SQLException e){
            logger.info("Create user failed", e);
            throw new DublicateKeyDAOException("Create user failed", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in create false", e);
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

        String search = "select * from" + nameTable + "where Login=? and Pass=?";

        try {
            user = (UserEntity) readElement;
        }catch (ClassCastException e) {
            logger.info("Enter parameters in read are invalid", e);
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        if(user.getLogin() != null || user.getPassword() != null) {
            try{
                search_user = connection.prepareStatement(search);
            }catch (SQLException e) {
                logger.warn("Prepare statement in read user wasn't created", e);
                throw new InternalDAOException("Prepare statement in read user wasn't created", e);
            }

            try {
                search_user.setString(1, user.getLogin());
                search_user.setString(2, user.getPassword());

                resultSet =  search_user.executeQuery();

                if(resultSet.first()) {
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Pass"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));

                    //Заглушка инициализации прав
                    Grant grant = new Grant();
                    switch (user.getGroup()) {
                        case "Admin" : {
                            grant.setUsersBranchLevel(Grant.EDIT);
                            grant.setSystemUnitsBranchLevel(Grant.EDIT);

                            break;
                        }
                        case "Operator" : {
                            grant.setUsersBranchLevel(Grant.NONE);
                            grant.setSystemUnitsBranchLevel(Grant.EDIT);
                            break;
                        }
                        case "Guest" : {
                            grant.setUsersBranchLevel(Grant.NONE);
                            grant.setSystemUnitsBranchLevel(Grant.READ);
                            break;
                        }
                        default : {
                            grant.setUsersBranchLevel(Grant.NONE);
                            grant.setSystemUnitsBranchLevel(Grant.NONE);
                            break;
                        }
                    }

                    user.setGrant(grant);

                    logger.trace(String.format("read user %s", user.getLogin()));
                }
                else{
                    logger.info("User not found");
                    throw new InvalidDataDAOException("User not found");
                }
            }catch (SQLException e){
                logger.info("Read user failed", e);
                throw new InternalDAOException("Read user failed", e);
            }
            finally {
                if (search_user!=null){
                    try {
                        search_user.close();
                    } catch (SQLException e) {
                        logger.warn("Close PrepareStatement in read false", e);
                        throw new InternalDAOException(e);
                    }
                }
                if (resultSet!= null){
                    try{
                        resultSet.close();
                    }catch (SQLException e){
                        logger.warn("Close ResultSet in read false",e);
                        throw new InternalDAOException(e);
                    }
                }
            }
        }
        else{
            logger.info("For reading user incorrectly chosen field, try Login with Password");
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, try Login with Password");
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
            logger.info("Enter parameters in update are invalid", e);
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            logger.warn("Prepare statement in update user wasn't created", e);
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

            logger.trace(String.format("update user %s", user.getLogin()));
        } catch (SQLException e) {
            logger.info("Update user failed", e);
            throw new DublicateKeyDAOException("Update user failed", e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in update false", e);
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
            logger.info("Enter parameters in delete are invalid", e);
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(delete);
        }catch (SQLException e) {
            logger.warn("Prepare statement in delete user wasn't created", e);
            throw new InternalDAOException("Prepare statement in delete user wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();

            logger.trace(String.format("delete user %s", user.getLogin()));
        } catch (SQLException e) {
            logger.info("Delete user failed", e);
            throw new InvalidDataDAOException("Delete user failed", e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in delete false", e);
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