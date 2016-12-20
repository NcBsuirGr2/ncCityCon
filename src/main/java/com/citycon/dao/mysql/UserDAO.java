package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.model.Grant;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import static java.util.Calendar.getInstance;

/**
 * Created by Vojts on 09.11.2016.
 */
public class UserDAO extends MySQLDAO{

    public UserDAO() throws InternalDAOException {
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

    @Override
    public UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input)
                                            throws InvalidDataDAOException, InternalDAOException {

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

            search = "select * from " + nameTable + " where Login LIKE '%" + search_input + "%' OR `Name` LIKE '%" +
                    search_input + "%' " +
                    "order by " + sorter + sorting_direction +
                    " limit " + ((page-1)*itemsPerPage) + "," + itemsPerPage;
        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();

                ResultSet resultSet =  statement.executeQuery(search);
        ){
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

        } catch (SQLException e) {
            logger.warn("Resources wasn't created for getPage in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for getPage in " + nameTable, e);
        }

        return users.toArray(new UserEntity[users.size()]);
    }

    @Override
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        UserEntity user = null;

        Calendar calendar = getInstance();
        Date startDate = new Date(calendar.getTime().getTime());

        String insert = "insert into" + nameTable +
                "(`Login`, `Pass`, `E-mail`, `Name`, `Group`, `create_date`)" +
                " values (?, ?, ?, ?, ?, ?)";

        try {
            user = (UserEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        String log_parameters = "With parameters: Login("+ user.getLogin() + "), Password(" + user.getPassword()
                + "), E-Mail(" + user.getEmail() + "), Name(" + user.getEmail() + "), Group(" + user.getGroup()
                + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ){

            try {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getGroup());
                preparedStatement.setDate(6,  startDate);

                preparedStatement.executeUpdate();

                logger.debug("Create {}.\n {}", nameTable, log_parameters);
            } catch (SQLException e){
                logger.info("Create {} failed.\n {}", nameTable, log_parameters, e);
                throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
            }
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for read in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for read in " + nameTable, e);
        }
    }

    @Override
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        UserEntity user = null;

        try {
            user = (UserEntity) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String search = "select * from " + nameTable + " where `Login`= '" + user.getLogin() + "'";

        String log_parameters = "With parameters: Login("+ user.getLogin() + ")";

        if (user.getPassword() != null){
            search += " and `Pass`='" + user.getPassword() + "'";
            log_parameters += ", Password("+ user.getPassword()+")";
        }

        if (user.getLogin() == null){
            logger.info("For reading user incorrectly chosen field, try Login with Password or only Login");
            throw new InvalidDataDAOException("For reading user incorrectly chosen field, try Login with Password or only Login");
        }

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
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
    }

    @Override
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        UserEntity user = null;

        String update = "update" + nameTable + "set `Login`=?, `Pass`=?, `E-mail`=?, `Name`=?, `Group`=? where `id`=?";

        try {
            user = (UserEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        String log_parameters = "With parameters: id(" + user.getId() + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(update);
        ) {

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
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for update in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for update in " + nameTable, e);
        }
    }

    private Grant getGrantFromDB(String group) throws InternalDAOException, InvalidDataDAOException {
        Grant grant = new Grant();

        String search = "select * from `Grant` where `idGrant`='" + group + "'";

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ) {
            if (resultSet.first()) {
                if(resultSet.first()) {
                    grant.setSystemUnitsBranchLevel(resultSet.getInt("systemUnitBranch"));
                    grant.setUsersBranchLevel(resultSet.getInt("userBranch"));
                }
                else{
                    logger.info("Grant not found");
                    throw new InvalidDataDAOException("Grant not found");
                }
            }
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for count_element in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for count_element in " + nameTable, e);
        }

        return grant;
    }
}