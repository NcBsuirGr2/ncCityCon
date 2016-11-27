package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
import com.citycon.dao.interfaces.RoutersOfCity;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO implements RoutersOfCity {

    /**
     * @throws InternalDAOException
     */
    private RouterDAO() throws InternalDAOException {
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

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
            throws InvalidDataDAOException, InternalDAOException {
        return this.getPage(page, itemsPerPage, sortBy, asc, null);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement)
            throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterEntity router= null;

        PreparedStatement preparedStatement = null;

        String insert = "insert into" + nameTable +
                "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                " values (?, ?, ?, ?, ?)";

        try{
            router = (RouterEntity) newElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        if (router.getCityId() == 0){
            if (router.getCityName() == null || router.getCountryName() == null){
                logger.info("For getCityID incorrectly chosen field, try City And Country");
                throw new InvalidDataDAOException("For getCityID incorrectly chosen field, try City And Country");
            }
            CityEntity city = new CityEntity();
            city.setName(router.getCityName());
            city.setCountryName(router.getCountryName());
            router.setCityId(this.getCityID(city));
        }

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in create wasn't created", e);
            throw new InternalDAOException("PrepareStatement in create wasn't created", e);
        }

        String log_parameters = "With parameters: Name("+ router.getName() + "), SN(" + router.getSN()
                + "), CountPorts(" + router.getPortsNum() + "), IsActive(" + router.isActive()
                + "), CityId(" + router.getCityId() + ")";

        try {
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());

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
    public void read(Entity readElement)
            throws InternalDAOException, InvalidDataDAOException {
        RouterEntity router = null;

        Statement search_router = null;
        ResultSet resultSet = null;

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

        try {
            connection = getConnection();
            search_router = connection.createStatement();
        }catch (SQLException e) {
            logger.warn("PreparedStatement in read wasn't created", e);
            throw new InternalDAOException("PreparedStatement in read wasn't created", e);
        }

        try {
            resultSet = search_router.executeQuery(search);

            if(resultSet.first()) {
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.setActive(resultSet.getBoolean("In_Service"));
                router.setCityId(resultSet.getInt("City_id"));
                router.setCityName(resultSet.getString("CityName"));
                router.setCountryName(resultSet.getString("Country"));
                logger.trace("Read {}.\n {}", nameTable, log_parameters);
            }
            else {
                logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
            }
        }catch (SQLException e){
            logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
            throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
        }
        finally {
            closeConnection();

            if (search_router!=null){
                try {
                    search_router.close();
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
     * @throws InvalidDataDAOException, InternalDAOException
     */
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

        PreparedStatement preparedStatement = null;

        String log_parameters = "With parameters: ID(" + router.getId() + ")";

        if (router.getCityName() != null && router.getCountryName()!= null){
            CityEntity city = new CityEntity();
            city.setName(router.getCityName());
            city.setCountryName(router.getCountryName());
            router.setCityId(this.getCityID(city));


            log_parameters += ", CityID(" + router.getCityId() + ")";

            update = "update" + nameTable +
                    "set `Name`=?, `In_Service`=?, City_id = " + router.getCityId() + " " +
                    "where `ID`=?";
        }
        else{
            update = "update" + nameTable +
                    "set `Name`=?, `In_Service`=?" +
                    "where `ID`=?";
        }

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in update wasn't created", e);
            throw new InternalDAOException("PreparedStatement in update wasn't created", e);
        }

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
    public static RouterDAO getInstance() throws InternalDAOException {
        return new RouterDAO();
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @param city
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    @Override
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException {

        PreparedStatement search_routers = null;
        ResultSet resultSet = null;

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
                        "' order by " + sorter + sorting_direction + " limit ?, ?";
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
                        "order by " + sorter + sorting_direction + " limit ?, ?";
            }
        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            connection = getConnection();
            search_routers = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try{
            search_routers.setInt(1, (page-1)*itemsPerPage);
            search_routers.setInt(2, itemsPerPage);

            resultSet =  search_routers.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterEntity router = new RouterEntity();
                    router.setId(resultSet.getInt("id"));
                    router.setName(resultSet.getString("Name"));
                    router.setSN(resultSet.getString("SN"));
                    router.setPortsNum(resultSet.getInt("Port"));
                    router.setActive(resultSet.getBoolean("In_Service"));
                    router.setCityId(resultSet.getInt("City_id"));
                    router.setCountryName(resultSet.getString("Country"));
                    router.setCityName(resultSet.getString("CityName"));
                    router.setUsedPortsNum(resultSet.getInt("UsedPortsNum"));
                    routers.add(router);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(String.format("Put data to PrepareStatement in {} invalid",
                    nameTable), e);
        }
        finally {
            closeConnection();

            if (search_routers!=null){
                try {
                    search_routers.close();
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

        return routers.toArray(new RouterEntity[routers.size()]);
    }

    /**
     * @param city
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    @Override
    public int count_element(CityEntity city) throws InvalidDataDAOException, InternalDAOException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String search = "";

        if (city.getName() != null && city.getCountryName() != null){
            search = "SELECT COUNT(ID) " +
                    "FROM (SELECT DISTINCT Router.ID AS ID FROM Router " +
                    "INNER JOIN `City` ON (`Router`.`City_id` = `City`.`ID`) " +
                    "WHERE (`City`.`Name` = ? AND `City`.`Country` = ?)) E";
        }
        else{
            logger.info("For reading router incorrectly chosen field, try name and country");
            throw new InvalidDataDAOException("For reading router incorrectly chosen field," +
                    " try name and country");
        }

        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement(search);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in get count wasn't created", e);
            throw new InternalDAOException("PrepareStatement in get count wasn't created", e);
        }

        String log_parameters = "With parameters: City(" + city.getName() + "), Country(" +
                city.getCountryName() + ")";

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

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
     * @param id
     * @return
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    private int getUsedPortsNum(int id) throws InternalDAOException, InvalidDataDAOException {

        int UsedPortsNum = 0;

        PreparedStatement preparedStatement = null;

        ResultSet resultSet= null;

        if (id == 0){
            logger.info("For getUsedPortsNum incorrectly chosen field, try ID");
            throw new InvalidDataDAOException("For getUsedPortsNum incorrectly chosen field, try ID");
        }

        String log_parameters = "With parameters: ID(" + id + ")";

        String search = "select count(ID_From)+count(ID_To) as num from RouterConnection where ID=?";

        try{
            connection = getConnection();
            preparedStatement = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PreparedStatement in read wasn't created", e);
            throw new InternalDAOException("PreparedStatement in read wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery(search);

            if(resultSet.first()) {
                UsedPortsNum = resultSet.getInt("num");

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

            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    logger.warn("Close PrepareStatement in getUsedPortsNum {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    logger.warn("Close ResultSet in getUsedPortsNum {} failed", nameTable,e);
                    throw new InternalDAOException(e);
                }
            }
        }

        return UsedPortsNum;
    }

    /**
     * @param city
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    private int getCityID(CityEntity city) throws InvalidDataDAOException, InternalDAOException {

        int cityID = 0;

        PreparedStatement search_city = null;
        ResultSet resultSet= null;

        if (city.getName() == null || city.getCountryName() == null){
            logger.info("For getCityID incorrectly chosen field, try City And Country");
            throw new InvalidDataDAOException("For getCityID incorrectly chosen field, try City And Country");
        }

        String search = "select ID from City where `Name`=? and Country=?";

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
            search_city.setString(1, city.getName());
            search_city.setString(2, city.getCountryName());

            resultSet = search_city.executeQuery();

            if(resultSet.first()) {
                cityID = resultSet.getInt("ID");

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

        return cityID;
    }

}