package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.interfaces.UsersOfGroup;
import com.citycon.dao.interfaces.UsersStatistic;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO implements UsersOfGroup, UsersStatistic {

    /**
     * @throws InternalDAOException
     */
    private UserDAO() throws InternalDAOException {
        super();
        nameTable = " `User` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.UserDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("login", "`Login`");
        hashMap.put("email", "`E-mail`");
        hashMap.put("name", "`Name`");
        hashMap.put("group", "`Group`");
        hashMap.put("createDate", "`create_date`");
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

        String sorter = hashMap.get(sortBy);

        String search = "";

        String log_parameters = "With parameters: page("+ page + "), itemsPerPage(" + itemsPerPage
                + "), sortBy(" + sortBy + "), asc(" + asc + ")";

        if(sorter != null) {
            String sorting_direction = "";

            if(asc){
                sorting_direction = " asc ";
            }
            else {
                sorting_direction = " desc ";
            }

            search = "select * from " + nameTable + " order by " + sorter + sorting_direction + " limit ?,?";
        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            connection = getConnection();

            search_users = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
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

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(String.format("Put data to PrepareStatement in {} invalid", nameTable), e);
        }
        finally {
            closeConnection();

            if (search_users!=null){
                try {
                    search_users.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in getPage {} failed", nameTable, e);
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
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in create wasn't created", e);
            throw new InternalDAOException("PrepareStatement in create wasn't created", e);
        }

        String log_parameters = "With parameters: Login("+ user.getLogin() + "), Password(" + user.getPassword()
                + "), E-Mail(" + user.getEmail() + "), Name(" + user.getEmail() + "), Group(" + user.getGroup()
                + ")";

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getGroup());
            preparedStatement.setDate(6,  startDate);

            preparedStatement.executeUpdate();

            logger.trace("Create {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e){
            logger.info("Create {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
        }
        finally {
            closeConnection();

            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in create {} failed", nameTable, e);
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

        String search = "select * from" + nameTable + "where `Login`=?";

        try {
            user = (UserEntity) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String log_parameters = "With parameters: Login("+ user.getLogin() + ")";

        if (user.getPassword() != null){
            search += " and `Pass`=?";
            log_parameters += ", Password("+ user.getPassword()+")";
        }

        if(user.getLogin() != null) {
            try{
                connection = getConnection();
                search_user = connection.prepareStatement(search);
            }catch (SQLException e) {
                logger.warn("PreparedStatement in read wasn't created", e);
                throw new InternalDAOException("PreparedStatement in read wasn't created", e);
            }

            try {
                search_user.setString(1, user.getLogin());

                if(user.getPassword() != null) {
                    search_user.setString(2, user.getPassword());
                }
                resultSet =  search_user.executeQuery();
                if(resultSet.first()) {
                    user.setId(resultSet.getInt("id"));
                    user.setLogin(resultSet.getString("Login"));
                    user.setPassword(resultSet.getString("Pass"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    user.setGrant(this.getGrantFromDB(user.getGroup()));

                    logger.trace("Read {}.\n {}", nameTable, log_parameters);
                }
                else{
                    logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                    throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
                }
            }catch (SQLException e){
                logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
                throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
            }
            finally {
                closeConnection();

                if (search_user!=null){
                    try {
                        search_user.close();
                    } catch (SQLException e) {
                        logger.warn("Close PrepareStatement in read {} failed", nameTable, e);
                        throw new InternalDAOException(e);
                    }
                }
                if (resultSet!= null){
                    try{
                        resultSet.close();
                    }catch (SQLException e){
                        logger.warn("Close ResultSet in read {} failed", nameTable,e);
                        throw new InternalDAOException(e);
                    }
                }
            }
        }
        else{
            logger.info("For reading user incorrectly chosen field, try Login with Password or only Login");
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, try Login with Password or only Login");
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

        String update = "update" + nameTable + "set `Login`=?, `Pass`=?, `E-mail`=?, `Name`=?, `Group`=? where `id`=?";

        try {
            user = (UserEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        try {
            connection = getConnection();

            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in update wasn't created", e);
            throw new InternalDAOException("PreparedStatement in update wasn't created", e);
        }

        String log_parameters = "With parameters: id(" + user.getId() + ")";

        try {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getName());
            preparedStatement.setString(5, user.getGroup());
            preparedStatement.setInt(6, user.getId());

            preparedStatement.executeUpdate();

            logger.trace("Update {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e) {
            logger.info("Update {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
            closeConnection();

            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in update {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
        }
    }


    /**
     * @param group
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    @Override
    public int count_element(String group) throws InvalidDataDAOException, InternalDAOException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String search = "";

        if (group != null){
            search = "SELECT COUNT(ID) FROM `User` WHERE `GROUP` = ?";
        }
        else{
            logger.info("For reading user incorrectly chosen field, try group");
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, try group");
        }

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(search);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in get count wasn't created", e);
            throw new InternalDAOException("PrepareStatement in get count wasn't created", e);
        }

        String log_parameters = "With parameters: Group(" + group + ")";

        try {
            preparedStatement.setString(1, group);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.first()) {
                count = resultSet.getInt(1);

                logger.trace("Get count elements. {}", log_parameters);
            }
        } catch (SQLException e) {
            logger.info("Get count elements failed. {}", log_parameters, e);
            throw new InternalDAOException("Get count elements failed.", e);
        }
        finally {
            closeConnection();

            if (preparedStatement!=null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in get count false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in get count false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return count;
    }

    /**
     * @throws InternalDAOException
     */
    public static UserDAO getInstance() throws InternalDAOException {
        return new UserDAO();
    }

    private Grant getGrantFromDB(String group) throws InternalDAOException, InvalidDataDAOException {
        PreparedStatement search_grant = null;
        ResultSet resultSet = null;

        Grant grant = new Grant();

        String search = "select * from `Grant` where `idGrant`=?";

        try {
            connection = getConnection();
            search_grant = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("Prepare statement in get Grant wasn't created", e);
            throw new InternalDAOException("Prepare statement in get Grant wasn't created", e);
        }

        try {
            search_grant.setString(1, group);

            resultSet =  search_grant.executeQuery();

            if(resultSet.first()) {
                grant.setSystemUnitsBranchLevel(resultSet.getInt("systemUnitBranch"));
                grant.setUsersBranchLevel(resultSet.getInt("userBranch"));
            }
            else{
                logger.info("Grant not found");
                throw new InvalidDataDAOException("Grant not found");
            }
        }catch (SQLException e){
            logger.info("Read grant failed", e);
            throw new InternalDAOException("Read grant failed", e);
        }
        finally {
            closeConnection();

            if (search_grant!=null){
                try {
                    search_grant.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in get Grant false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in in get Grant false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return grant;
    }

    @Override
    public ArrayList<UserEntity> first_users() throws InvalidDataDAOException, InternalDAOException {
        Statement search_users = null;
        ResultSet resultSet = null;

        ArrayList<UserEntity> users = new ArrayList();

        String search = "select * from User where create_date = (select min(create_date) from User)";

        try {
            connection = getConnection();

            search_users = connection.createStatement();
        }catch (SQLException e) {
            logger.warn("Statement in first_users wasn't created");
            throw new InternalDAOException("Statement in first_users wasn't created", e);
        }

        try {
            resultSet =  search_users.executeQuery(search);

            try {
                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setLogin(resultSet.getString("Login"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    users.add(user);
                }

                logger.trace("First_users");
            }catch (SQLException e){
                logger.info("First_users failed", e);
                throw new InvalidDataDAOException("First_users failed", e);
            }
        }catch (SQLException e){
            logger.info("Put data to Statement invalid. ", e);
            throw new InvalidDataDAOException("Put data to Statement invalid. ", e);
        }
        finally {
            closeConnection();

            if (search_users!=null){
                try {
                    search_users.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in first_users failed", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in first_users failed", e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return users;
    }

    @Override
    public ArrayList<UserEntity> last_users() throws InvalidDataDAOException, InternalDAOException {
        Statement search_users = null;
        ResultSet resultSet = null;

        ArrayList<UserEntity> users = new ArrayList();

        String search = "select * from User where create_date = (select max(create_date) from User)";

        try {
            connection = getConnection();

            search_users = connection.createStatement();
        }catch (SQLException e) {
            logger.warn("Statement in last_users wasn't created");
            throw new InternalDAOException("Statement in last_users wasn't created", e);
        }

        try {
            resultSet =  search_users.executeQuery(search);

            try {
                while (resultSet.next()) {
                    UserEntity user = new UserEntity();
                    user.setLogin(resultSet.getString("Login"));
                    user.setEmail(resultSet.getString("E-mail"));
                    user.setName(resultSet.getString("Name"));
                    user.setGroup(resultSet.getString("Group"));
                    user.setCreateDate(resultSet.getDate("create_date"));
                    users.add(user);
                }

                logger.trace("Last_users");
            }catch (SQLException e){
                logger.info("Last_users failed", e);
                throw new InvalidDataDAOException("First_users failed", e);
            }
        }catch (SQLException e){
            logger.info("Put data to Statement invalid. ", e);
            throw new InvalidDataDAOException("Put data to Statement invalid. ", e);
        }
        finally {
            closeConnection();

            if (search_users!=null){
                try {
                    search_users.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in last_users failed", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in last_users failed", e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return users;
    }
}