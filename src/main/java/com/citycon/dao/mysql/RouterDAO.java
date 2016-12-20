package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.interfaces.RoutersOfCity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO implements RoutersOfCity {

    /**
     * @throws InternalDAOException
     */
    public RouterDAO() throws InternalDAOException {
        super();
        nameTable = " `Router` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.RouterDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("name", "`Name`");
        hashMap.put("SN", "`SN`");
        hashMap.put("portsNum", "`Port`");
        hashMap.put("isActive", "`In_Service`");
        hashMap.put("cityId", "`City_id`");
        hashMap.put("city", "`CityName`");
        hashMap.put("country", "`Country`");
        hashMap.put("usedPortsNum", "`UsedPortsNum`");
    }

    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
            throws InvalidDataDAOException, InternalDAOException {
        return this.getPage(page, itemsPerPage, sortBy, asc, null);
    }

    public void create(Entity newElement)
            throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterEntity router= null;

        String insert = "insert into" + nameTable +
                "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                " values (?, ?, ?, ?, ?)";

        try{
            router = (RouterEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        if (router.getCity().getId() == 0){
            if (router.getCity().getName() == null || router.getCity().getCountryName() == null){
                logger.info("For getCityID incorrectly chosen field, try City And Country");
                throw new InvalidDataDAOException("For getCityID incorrectly chosen field, try City And Country");
            }
            CityEntity city = new CityEntity();
            city.setName(router.getCity().getName());
            city.setCountryName(router.getCity().getCountryName());
            city.setId(this.getCityID(city));
            router.setCity(city);
        }

        String log_parameters = "With parameters: Name("+ router.getName() + "), SN(" + router.getSN()
                + "), CountPorts(" + router.getPortsNum() + "), IsActive(" + router.isActive()
                + "), CityId(" + router.getCity().getId() + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ){

            try {
                preparedStatement.setString(1, router.getName());
                preparedStatement.setString(2, router.getSN());
                preparedStatement.setInt(3, router.getPortsNum());
                preparedStatement.setBoolean(4, router.isActive());
                preparedStatement.setInt(5, router.getCity().getId());

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

    public void read(Entity readElement)
            throws InternalDAOException, InvalidDataDAOException {
        RouterEntity router = null;

        try {
            router = (RouterEntity) readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String log_parameters = "With parameters: ";

        String field = "";
        String value = "";

        if(router.getId() != 0) {
            field = "R.ID";
            value = String.valueOf(router.getId());
            log_parameters += "Id(" + value + ")";
        }
        else if(router.getSN() != null){
            field = "R.SN";
            value = "'" + router.getSN() + "'";
            log_parameters += "SN(" + value + ")";
        }
        else{
            logger.info("For reading router incorrectly chosen field, try ID or SN");
            throw new InvalidDataDAOException("For reading router incorrectly chosen field, try ID or SN");
        }

        String search = "select R.ID as ID, R.SN as SN, R.`Name`, R.`Port`, R.In_Service, R.City_id, " +
                "C.`Name` as CityName, C.Country as Country " +
                "from Router R join City C on R.City_id=C.ID where " + field + " = " + value;

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
            if(resultSet.first()) {
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.setActive(resultSet.getBoolean("In_Service"));

                CityEntity city = new CityEntity();
                city.setId(resultSet.getInt("City_id"));
                city.setName(resultSet.getString("CityName"));
                city.setCountryName(resultSet.getString("Country"));

                router.setCity(city);

                logger.debug("Read {}.\n {}", nameTable, log_parameters);
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

    public void update(Entity updateElement)
            throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {

        RouterEntity router = null;

        try {
            router = (RouterEntity) updateElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        String update = "";

        String log_parameters = "With parameters: ID(" + router.getId() + ")";

        if (router.getCity().getName() != null && router.getCity().getCountryName()!= null){
            CityEntity city = new CityEntity();
            city.setName(router.getCity().getName());
            city.setCountryName(router.getCity().getCountryName());
            city.setId(this.getCityID(city));

            router.setCity(city);

            log_parameters += ", CityID(" + router.getCity().getId() + ")";

            update = "update" + nameTable +
                    "set `Name`=?, `In_Service`=?, City_id = " + router.getCity().getId() + " " +
                    "where `ID`=?";
        }
        else{
            update = "update" + nameTable +
                    "set `Name`=?, `In_Service`=?" +
                    "where `ID`=?";
        }

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(update);
        ) {

            try {
                preparedStatement.setString(1, router.getName());
                preparedStatement.setBoolean(2, router.isActive());
                preparedStatement.setInt(3, router.getId());

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
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException {
        ArrayList<RouterEntity> routers = new ArrayList();

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

            if (city != null){
                if (city.getName() == null || city.getCountryName() == null){
                    logger.info("For GetPage Routers of City incorrectly" +
                            " chosen field, try Name and CountryName");
                    throw new InvalidDataDAOException("For GetPage Routers of City incorrectly" +
                            " chosen field, try Name and CountryName");
                }
                log_parameters += ", City(" + city.getName() + "), Country(" + city.getCountryName() + ")";

                search= "SELECT \n" +
                        "    RFrom.ID AS ID,\n" +
                        "            RFrom.SN as SN,\n" +
                        "            RFrom.`Name` as `Name`,\n" +
                        "            RFrom.`Port` as `Port`,\n" +
                        "            RFrom.In_Service as In_Service,\n" +
                        "            RFrom.City_id as City_id,\n" +
                        "            RFrom.CityName as CityName,\n" +
                        "            RFrom.Country as Country,\n" +
                        "            RFrom.UsedPortsNum + RTo.UsedPortsNum as UsedPortsNum\n" +
                        "FROM\n" +
                        "    (SELECT DISTINCT\n" +
                        "        R.ID AS ID,\n" +
                        "            R.SN AS SN,\n" +
                        "            R.`Name`,\n" +
                        "            R.`Port`,\n" +
                        "            R.In_Service,\n" +
                        "            R.City_id,\n" +
                        "            R.UsedPortsNum,\n" +
                        "            C.`Name` AS CityName,\n" +
                        "            C.Country AS Country\n" +
                        "    FROM\n" +
                        "        (SELECT \n" +
                        "        Ro.*, COUNT(Con.ID_From) AS UsedPortsNum\n" +
                        "    FROM\n" +
                        "        Router Ro\n" +
                        "    LEFT OUTER JOIN RouterConnection Con ON Ro.ID = Con.ID_From\n" +
                        "    GROUP BY Ro.ID\n" +
                        "    ORDER BY Ro.ID) R\n" +
                        "    JOIN City C ON R.City_id = C.ID) RFrom\n" +
                        "        JOIN\n" +
                        "    (SELECT DISTINCT\n" +
                        "        R.ID AS ID,\n" +
                        "            R.SN AS SN,\n" +
                        "            R.`Name`,\n" +
                        "            R.`Port`,\n" +
                        "            R.In_Service,\n" +
                        "            R.City_id,\n" +
                        "            R.UsedPortsNum,\n" +
                        "            C.`Name` AS CityName,\n" +
                        "            C.Country AS Country\n" +
                        "    FROM\n" +
                        "        (SELECT \n" +
                        "        Ro.*, COUNT(Con.ID_To) AS UsedPortsNum\n" +
                        "    FROM\n" +
                        "        Router Ro\n" +
                        "    LEFT OUTER JOIN RouterConnection Con ON Ro.ID = Con.ID_To\n" +
                        "    GROUP BY Ro.ID\n" +
                        "    ORDER BY Ro.ID) R\n" +
                        "    JOIN City C ON R.City_id = C.ID) RTo ON RFrom.ID = RTo.ID " +
                        "where RFrom.CityName='"+ city.getName() +"' and RFrom.Country='" + city.getCountryName() +
                        "' order by " + sorter + sorting_direction + " limit " +
                        ((page-1)*itemsPerPage) + "," + itemsPerPage;
            }
            else {
                search = "SELECT \n" +
                        "    RFrom.ID AS ID,\n" +
                        "            RFrom.SN,\n" +
                        "            RFrom.`Name`,\n" +
                        "            RFrom.`Port`,\n" +
                        "            RFrom.In_Service,\n" +
                        "            RFrom.City_id,\n" +
                        "            RFrom.CityName,\n" +
                        "            RFrom.Country,\n" +
                        "            RFrom.UsedPortsNum + RTo.UsedPortsNum as UsedPortsNum\n" +
                        "FROM\n" +
                        "    (SELECT DISTINCT\n" +
                        "        R.ID AS ID,\n" +
                        "            R.SN AS SN,\n" +
                        "            R.`Name`,\n" +
                        "            R.`Port`,\n" +
                        "            R.In_Service,\n" +
                        "            R.City_id,\n" +
                        "            R.UsedPortsNum,\n" +
                        "            C.`Name` AS CityName,\n" +
                        "            C.Country AS Country\n" +
                        "    FROM\n" +
                        "        (SELECT \n" +
                        "        Ro.*, COUNT(Con.ID_From) AS UsedPortsNum\n" +
                        "    FROM\n" +
                        "        Router Ro\n" +
                        "    LEFT OUTER JOIN RouterConnection Con ON Ro.ID = Con.ID_From\n" +
                        "    GROUP BY Ro.ID\n" +
                        "    ORDER BY Ro.ID) R\n" +
                        "    JOIN City C ON R.City_id = C.ID) RFrom\n" +
                        "        JOIN\n" +
                        "    (SELECT DISTINCT\n" +
                        "        R.ID AS ID,\n" +
                        "            R.SN AS SN,\n" +
                        "            R.`Name`,\n" +
                        "            R.`Port`,\n" +
                        "            R.In_Service,\n" +
                        "            R.City_id,\n" +
                        "            R.UsedPortsNum,\n" +
                        "            C.`Name` AS CityName,\n" +
                        "            C.Country AS Country\n" +
                        "    FROM\n" +
                        "        (SELECT \n" +
                        "        Ro.*, COUNT(Con.ID_To) AS UsedPortsNum\n" +
                        "    FROM\n" +
                        "        Router Ro\n" +
                        "    LEFT OUTER JOIN RouterConnection Con ON Ro.ID = Con.ID_To\n" +
                        "    GROUP BY Ro.ID\n" +
                        "    ORDER BY Ro.ID) R\n" +
                        "    JOIN City C ON R.City_id = C.ID) RTo ON RFrom.ID = RTo.ID " +
                        "order by " + sorter + sorting_direction + " limit " +
                        ((page-1)*itemsPerPage) + "," + itemsPerPage;
            }
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
                    RouterEntity router = new RouterEntity();
                    router.setId(resultSet.getInt("id"));
                    router.setName(resultSet.getString("Name"));
                    router.setSN(resultSet.getString("SN"));
                    router.setPortsNum(resultSet.getInt("Port"));
                    router.setActive(resultSet.getBoolean("In_Service"));

                    CityEntity cityEntity = new CityEntity();
                    cityEntity.setId(resultSet.getInt("City_id"));
                    cityEntity.setName(resultSet.getString("CityName"));
                    cityEntity.setCountryName(resultSet.getString("Country"));

                    router.setCity(cityEntity);

                    router.setUsedPortsNum(resultSet.getInt("UsedPortsNum"));
                    routers.add(router);
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

        return routers.toArray(new RouterEntity[routers.size()]);
    }

    @Override
    public int count_element(CityEntity city) throws InvalidDataDAOException, InternalDAOException {
        String search = "";

        if (city.getName() != null && city.getCountryName() != null){
            search = "SELECT COUNT(ID) " +
                    "FROM (SELECT DISTINCT Router.ID AS ID FROM Router " +
                    "INNER JOIN `City` ON (`Router`.`City_id` = `City`.`ID`) " +
                    "WHERE (`City`.`Name` = '" + city.getName() + "' AND `City`.`Country` = '" +
                    city.getCountryName() + "')) E";
        }
        else{
            logger.info("For reading router incorrectly chosen field, try name and country");
            throw new InvalidDataDAOException("For reading router incorrectly chosen field," +
                    " try name and country");
        }

        return count_element(search);
    }

    private int getCityID(CityEntity city) throws InvalidDataDAOException, InternalDAOException {
        int cityID = 0;

        if (city.getName() == null || city.getCountryName() == null){
            logger.info("For getCityID incorrectly chosen field, try City And Country");
            throw new InvalidDataDAOException("For getCityID incorrectly chosen field, try City And Country");
        }

        String search = "select ID from City where `Name`='" + city.getName() +
                "' and Country='" + city.getCountryName() + "'";

        String log_parameters = "With parameters: Name("+ city.getName() +
                "), Country(" + city.getCountryName() + ")";

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
            if(resultSet.first()) {
                cityID = resultSet.getInt("ID");
            }
            else{
                logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
            }

        }catch (SQLException e){
            logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }

        return cityID;
    }
}