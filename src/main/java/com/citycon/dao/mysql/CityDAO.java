package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.interfaces.CitiesOfCountry;
import com.citycon.dao.interfaces.CitiesStatistic;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.CountryEntity;
import com.citycon.model.systemunits.entities.Entity;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Vojts on 09.11.2016.
 */

public class CityDAO extends MySQLDAO implements CitiesOfCountry, CitiesStatistic {

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
            connection = getConnection();
            search_cities = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            search_cities.setInt(1, (page - 1) * itemsPerPage);
            search_cities.setInt(2, itemsPerPage);

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
            closeConnection();

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
            connection = getConnection();
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
        CityEntity city = null;

        PreparedStatement search_city = null;
        ResultSet resultSet= null;

        String search = "select C.ID, C.`Name`, C.Country, count(R.ID) as RoutersNum " +
                        "from Router R join City C on R.City_id=C.ID " +
                        "where C.`Name`=? and C.Country=?";

        try {
            city = (CityEntity)readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (city.getName() == null || city.getCountryName() == null){
            logger.info("For reading city incorrectly chosen field, try City and Country");
            throw new InvalidDataDAOException("For reading city incorrectly chosen field, try City and Country");
        }

        String log_parameters = "With parameters: Name("+ city.getName() +
                "), Country(" + city.getCountryName() + ")";

        try{
            connection = getConnection();
            search_city = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in read wasn't created", e);
            throw new InternalDAOException("PreparedStatement in read wasn't created", e);
        }
        try {
            //                search_city.setInt(1, readElement.getId());
            search_city.setString(1, city.getName());
            search_city.setString(2, city.getCountryName());

            resultSet = search_city.executeQuery();

            if(resultSet.first()) {
                city.setId(resultSet.getInt("id"));
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
            closeConnection();

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
//        String update = "update" + nameTable + "set `Name`=?, `Country`=? where `Name`=? and `Country`=?";

        try {
            city = (CityEntity) updateElement;
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

        String log_parameters = "With parameters: id(" + city.getId() + ")";

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.setInt(3, city.getId());

//            preparedStatement.setString(3, city.getName());
//            preparedStatement.setString(4, city.getCountryName());


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
     * @throws InternalDAOException
     */
    public static CityDAO getInstance() throws InternalDAOException {
        return new CityDAO();
    }

    /**
     * @param country
     * @return
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    @Override
    public CityEntity[] getCities(String country) throws InternalDAOException, InvalidDataDAOException {

        PreparedStatement search_cities = null;
        ResultSet resultSet = null;

        ArrayList<CityEntity> cities = new ArrayList();

        String search = "";

        if (country == null) {
            logger.info("Enter parameter to get cities of country are invalid. Try country.");
            throw new InvalidDataDAOException("Enter parameter to get cities of country are invalid. " +
                    "Try country.");
        }

        String log_parameters = "With parameters: Country(" + country + ")";

        search = "select ID, `Name` from City where Country=?";

        try {
            connection = getConnection();
            search_cities = connection.prepareStatement(search);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in get cities of country wasn't created");
            throw new InternalDAOException("PrepareStatement in get cities of country wasn't created", e);
        }

        try {
            search_cities.setString(1, country);

            resultSet = search_cities.executeQuery();

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
                logger.info("Get cities of country failed.\n {}", log_parameters, e);
                throw new InvalidDataDAOException(String.format(
                        "Get cities of country failed"), e);
            }
        } catch (SQLException e) {
            logger.info("Put data to PrepareStatement in Get cities of country invalid. \n {}"
                    , log_parameters, e);
            throw new InvalidDataDAOException(String.format("Put data to PrepareStatement " +
                    "in Get cities of country invalid"), e);
        } finally {
            closeConnection();

            if (search_cities != null) {
                try {
                    search_cities.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in Get cities of country failed", e);
                    throw new InternalDAOException(e);
                }

            }
        }
        return cities.toArray(new CityEntity[cities.size()]);
    }

    /**
     * @return
     * @throws InternalDAOException
     */
    public int getCountCountries() throws InternalDAOException {
        int count = 0;

        String search = "select count(distinct Country) from City";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.warn("Statement in getCountCountries wasn't created", e);
            throw new InternalDAOException("Statement in getCountCountries wasn't created", e);
        }

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                count = resultSet.getInt(1);

                logger.trace("getCountCountries elements");
            }

        } catch (SQLException e) {
            logger.info("getCountCountries failed", e);
            throw new InternalDAOException("getCountCountries failed", e);
        }
        finally {
            closeConnection();

            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in getCountCountries false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in getCountCountries false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return count;
    }

    public CountryEntity maxCityCountry() throws InternalDAOException {
        CountryEntity countryEntity = null;

        String search = "SELECT Country, COUNT(id) AS num FROM City GROUP BY Country ORDER BY num desc LIMIT 1";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.warn("Statement in maxCityCountry wasn't created", e);
            throw new InternalDAOException("Statement in maxCityCountry wasn't created", e);
        }

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                countryEntity = new CountryEntity();

                countryEntity.setName(resultSet.getString("Country"));
                countryEntity.setCountCities(resultSet.getInt("num"));

                logger.trace("maxCityCountry elements");
            }

        } catch (SQLException e) {
            logger.info("maxCityCountry failed", e);
            throw new InternalDAOException("maxCityCountry failed", e);
        }
        finally {
            closeConnection();

            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in maxCityCountry false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in maxCityCountry false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return countryEntity;
    }

    public CountryEntity minCityCountry() throws InternalDAOException {
        CountryEntity countryEntity = null;

        String search = "SELECT Country, COUNT(id) AS num FROM City GROUP BY Country ORDER BY num LIMIT 1";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.warn("Statement in minCityCountry wasn't created", e);
            throw new InternalDAOException("Statement in minCityCountry wasn't created", e);
        }

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                countryEntity = new CountryEntity();

                countryEntity.setName(resultSet.getString("Country"));
                countryEntity.setCountCities(resultSet.getInt("num"));

                logger.trace("minCityCountry elements");
            }

        } catch (SQLException e) {
            logger.info("minCityCountry failed", e);
            throw new InternalDAOException("minCityCountry failed", e);
        }
        finally {
            closeConnection();

            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in minCityCountry false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in minCityCountry false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return countryEntity;
    }

    public CityEntity maxRouterCity() throws InternalDAOException {
        CityEntity cityEntity = null;

        String search = "select `Name`, num, Country from " +
                "((select City_id, COUNT(id) AS num from Router " +
                "group by City_id order by num desc Limit 1) S) " +
                "join City on City.ID = S.City_ID";

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            logger.warn("Statement in maxRouterCity wasn't created", e);
            throw new InternalDAOException("Statement in maxRouterCity wasn't created", e);
        }

        try {
            resultSet = statement.executeQuery(search);


            if (resultSet.first()) {
                cityEntity = new CityEntity();

                cityEntity.setName(resultSet.getString("Name"));
                cityEntity.setCountryName(resultSet.getString("Country"));
                cityEntity.setRoutersNum(resultSet.getInt("num"));

                logger.trace("maxRouterCity elements");
            }

        } catch (SQLException e) {
            logger.info("maxRouterCity failed", e);
            throw new InternalDAOException("maxRouterCity failed", e);
        }
        finally {
            closeConnection();

            if (statement!=null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in maxRouterCity false", e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in maxRouterCity false",e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return cityEntity;
    }

    /**
     * @return
     */
    @Override
    public String[] getCountries(){
        String[] countries = { "Belarus", "Russia", "Ukraine", "Poland", "Moldova"};

        return countries;
    }
}