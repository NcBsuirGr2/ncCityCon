package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */

public class CityDAO extends MySQLDAO {

    /**
     * @throws InternalDAOException
     */
    private CityDAO() throws InternalDAOException {
        super();
        nameTable = " `City` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.CityDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("name", "`Name`");
        hashMap.put("countryName", "`Country`");
        hashMap.put("routersNum", "`RoutersNum`");
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
    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
                                            throws InvalidDataDAOException, InternalDAOException {
        PreparedStatement search_cities = null;
        ResultSet resultSet = null;

        ArrayList<CityEntity> cities = new ArrayList();

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

            search = "select C.ID, C.`Name`, C.Country, count(R.ID) as RoutersNum " +
                    "from Router R Right outer join City C on R.City_id=C.ID " +
                    "group by C.ID " +
                    "order by " + sorter + sorting_direction + " limit ?,?";
        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            search_cities = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            search_cities.setInt(1, (page - 1) * itemsPerPage);
            search_cities.setInt(2, itemsPerPage);

            System.out.println(search_cities);

            resultSet = search_cities.executeQuery();

            try {
                while (resultSet.next()){
                    CityEntity city = new CityEntity();
                    city.setId(resultSet.getInt("id"));
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                    city.setRoutersNum(resultSet.getInt("RoutersNum"));
                    cities.add(city);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e) {
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(String.format("Put data to PrepareStatement in {} invalid", nameTable), e);
        }
        finally {
            if (search_cities!=null){
                try {
                    search_cities.close();
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

        return cities.toArray(new CityEntity[cities.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException,
            InternalDAOException, InvalidDataDAOException {
        CityEntity city = null;

        String insert = "insert into" + nameTable +
                "(`Name`, `Country`)" +
                " values (?, ?)";

        PreparedStatement preparedStatement = null;

        try {
            city = (CityEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in create wasn't created", e);
            throw new InternalDAOException("PrepareStatement in create wasn't created", e);
        }

        String log_parameters = "With parameters: Name("+ city.getName() + "), Country(" + city.getCountryName() + ")";

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.executeUpdate();

            logger.trace("Create {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e){
            logger.info("Create {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
        }
        finally {
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
        CityEntity city = null;

        PreparedStatement search_city = null;
        ResultSet resultSet= null;

//        String search = "select C.ID, C.`Name`, C.Country, count(R.ID) as RoutersNum " +
//                        "from Router R join City C on R.City_id=C.ID " +
//                        "where C.ID=?";

        String search = "select * from" + nameTable + "where `Name`=?";

        try {
            city = (CityEntity)readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String log_parameters = "With parameters: Name("+ city.getName() + ")";

        if (city.getCountryName() != null){
            search += " and `Country`=?";
            log_parameters += ", Country("+ city.getCountryName()+")";
        }

        if (city.getName() != null) {
            try{
                logger.debug("Before preparedStatement");
                logger.debug("Connection: {}", connection.isClosed());
                search_city = connection.prepareStatement(search);
                logger.debug("After preparedStatement");
                logger.debug("Connection: {}", connection.isClosed());
            }catch (SQLException e) {
                logger.warn("PreparedStatement in read wasn't created", e);
                throw new InternalDAOException("PreparedStatement in read wasn't created", e);
            }
            try {
//                search_city.setInt(1, readElement.getId());
                search_city.setString(1, city.getName());

                if(city.getCountryName() != null) {
                    search_city.setString(2, city.getCountryName());
                }

                logger.debug("Before resultSet");
                logger.debug("Connection: {}", connection.isClosed());
                resultSet = search_city.executeQuery();
                logger.debug("After resultSet");
                logger.debug("Connection: {}", connection.isClosed());

                if(resultSet.first()) {
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                    city.setRoutersNum(resultSet.getInt("RoutersNum"));

                    logger.trace("Read {}.\n {}", nameTable, log_parameters);
                }
                else{
                    logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                    throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
                }
            } catch (SQLException e) {
                logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
                throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
            }
            finally {
                if (search_city!=null){
                    try {
                        search_city.close();
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
            logger.info("For reading city incorrectly chosen field, try id");
            throw new InvalidDataDAOException("For reading city incorrectly chosen field, try id");
        }
    }

    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException,
            InvalidDataDAOException, InternalDAOException {
        CityEntity city = null;

        PreparedStatement preparedStatement = null;

        String update = "update" + nameTable + "set `Name`=?, `Country`=? where `id`=?";

        try {
            city = (CityEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in update wasn't created", e);
            throw new InternalDAOException("PreparedStatement in update wasn't created", e);
        }

        String log_parameters = "With parameters: id(" + city.getId() + ")";

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.setInt(3, city.getId());

            preparedStatement.executeUpdate();

            logger.trace("Update {}.\n {}", nameTable, log_parameters);
        } catch (SQLException e) {
            logger.info("Update {} failed.\n {}", nameTable, log_parameters, e);
            throw new DublicateKeyDAOException((String.format("Update %s failed", nameTable)), e);
        }
        finally {
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
     * @throws InternalDAOException
     */
    public static CityDAO getInstance() throws InternalDAOException {
        return new CityDAO();
    }
}