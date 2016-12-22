package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.interfaces.CitiesOfCountry;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO wrapper for the <code>ORMCity</code>. This class must be used in business logic for
 * deliver CRUD operations for the plain ORMEntity. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass
 * <code>MySQLDAO</code>.
 *
 * @author Alex
 * @version 2.0
 */
public class CityDAO extends MySQLDAO implements CitiesOfCountry {

    public CityDAO() throws InternalDAOException {
        super();
        nameTable = " `City` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.CityDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("name", "`Name`");
        hashMap.put("countryName", "`Country`");
        hashMap.put("routersNum", "`RoutersNum`");
    }

    @Override
    public int count_element(String search_input) throws InternalDAOException, InvalidDataDAOException {
        String search = "select count(1) from " + nameTable + " where `Name` LIKE '%" + search_input + "%' " +
                "OR Country LIKE '%" + search_input + "%'";

        return count_search(search);
    }

    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input)
                                            throws InvalidDataDAOException, InternalDAOException {

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
                     "where C.`Name` LIKE '%" + search_input + "%' OR  C.Country LIKE '%" + search_input + "%' " +
                     "group by C.ID "+
                     "order by " + sorter + sorting_direction + " limit " +
                    ((page-1)*itemsPerPage) + "," + itemsPerPage;
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
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for getPage in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for getPage in " + nameTable, e);
        }

        return cities.toArray(new CityEntity[cities.size()]);
    }

    public void create(Entity newElement) throws DublicateKeyDAOException,
            InternalDAOException, InvalidDataDAOException {

        CityEntity city;

        String insert = "insert into " + nameTable +
                "(`Name`, `Country`)" +
                " values (?, ?)";

        try {
            city = (CityEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        String log_parameters = "With parameters: Name("+ city.getName() + "), Country(" + city.getCountryName() + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ) {
            try {
                preparedStatement.setString(1, city.getName());
                preparedStatement.setString(2, city.getCountryName());

                preparedStatement.executeUpdate();
                logger.trace("Create {}.\n {}", nameTable, log_parameters);
            } catch (SQLException e) {
                logger.info("Create {} failed.\n {}", nameTable, log_parameters, e);
                throw new DublicateKeyDAOException(String.format("Create %s failed", nameTable), e);
            }
        }catch (SQLException e) {
            logger.warn("Resources wasn't created for read in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for read in " + nameTable, e);
        }
    }

    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {

        CityEntity city;

        try {
            city = (CityEntity)readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String search = "select C.ID, C.`Name`, C.Country, count(R.ID) as RoutersNum " +
                        "from Router R join City C on R.City_id=C.ID " +
                        "where C.`Name`='" + city.getName() + "' and C.Country='" +
                        city.getCountryName() + "'";

        if (city.getName() == null || city.getCountryName() == null){
            logger.info("For reading city incorrectly chosen field, try City and Country");
            throw new InvalidDataDAOException("For reading city incorrectly chosen field, try City and Country");
        }

        String log_parameters = "With parameters: Name("+ city.getName() +
                "), Country(" + city.getCountryName() + ")";

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
            try {
                if (resultSet.first()) {
                    city.setId(resultSet.getInt("id"));
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                    city.setRoutersNum(resultSet.getInt("RoutersNum"));

                    logger.trace("Read {}.\n {}", nameTable, log_parameters);
                } else {
                    logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                    throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
                }
            }catch (SQLException e){
                logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
                throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }
    }

    public void update(Entity updateElement) throws DublicateKeyDAOException,
            InvalidDataDAOException, InternalDAOException {
        CityEntity city = null;

        String update = "update" + nameTable + "set `Name`=?, `Country`=? where `id`=?";

        try {
            city = (CityEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        String log_parameters = "With parameters: id(" + city.getId() + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(update);
        ) {

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
        } catch (SQLException e) {
            logger.warn("Resources wasn't created for update in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for update in " + nameTable, e);
        }
    }

    @Override
    public CityEntity[] getCities(String country) throws InternalDAOException, InvalidDataDAOException {
        ArrayList<CityEntity> cities = new ArrayList();

        String search = "";

        if (country == null) {
            logger.info("Enter parameter to get cities of country are invalid. Try country.");
            throw new InvalidDataDAOException("Enter parameter to get cities of country are invalid. " +
                    "Try country.");
        }

        String log_parameters = "With parameters: Country(" + country + ")";

        search = "select ID, `Name` from City where Country='" + country + "'";

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();

                ResultSet resultSet =  statement.executeQuery(search);
        ){
            try {
                while (resultSet.next()) {
                    CityEntity city = new CityEntity();

                    city.setId(resultSet.getInt("id"));
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(country);

                    cities.add(city);
                }

                logger.trace("Get cities of country.\n {}", log_parameters);
            } catch (SQLException e) {
                logger.info("Put data to PrepareStatement in Get cities of country invalid. \n {}"
                        , log_parameters, e);
                throw new InvalidDataDAOException(String.format("Put data to PrepareStatement " +
                        "in Get cities of country invalid"), e);
            }

        } catch (SQLException e) {
            logger.warn("Resources wasn't created for getCities in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for getCities in " + nameTable, e);
        }

        return cities.toArray(new CityEntity[cities.size()]);
    }

    @Override
    public String[] getCountries(){
        String[] countries = { "Belarus", "Russia", "Ukraine", "Poland", "Moldova"};

        return countries;
    }
}