package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterConnectionDAO extends MySQLDAO implements ConnectionsOfCity, ConnectionsOfRouter{

    /**
     * @throws InternalDAOException
     */
    private RouterConnectionDAO() throws InternalDAOException {
        super();
        nameTable = " `RouterConnection` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.RouterConnectionDAO");

        hashMap.put("", "`City1`");
        hashMap.put("id", "`ID`");
        hashMap.put("City1", "`City1`");
        hashMap.put("SN1", "`SN1`");
        hashMap.put("ID1", "`Id1`");
        hashMap.put("City2", "`City2`");
        hashMap.put("SN2", "`SN2`");
        hashMap.put("ID2", "`Id2`");
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
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
                                                        throws InvalidDataDAOException, InternalDAOException {

        Statement statement = null;
        PreparedStatement get_data_routerConnections = null;
        ResultSet resultSet = null;

        ArrayList<String> SQLCommandForGetTableOfRouters = new ArrayList<>();

        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();

        String sorter = hashMap.get(sortBy);

        String get_data = "";

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

            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS `CitySNFrom`");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS `CitySNTo`");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNFrom " +
                    "select T.ID, C.`Name`, C.SN, T.ID_From from RouterConnection T join CitySN C on T.ID_From = C.ID");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNTo" +
                    " select T.ID, C.`Name`, C.SN, T.ID_To from RouterConnection T join CitySN C on T.ID_To = C.ID");


            get_data = "select C1.ID as ID, C1.`Name` as City1, C1.SN as SN1, C1.ID_From as Id1, " +
                    "C2.`Name` as City2, C2.SN as SN2, C2.ID_To as Id2 " +
                    "from CitySNFrom C1 join CitySNTo C2 on C1.ID = C2.ID " +
                    "order by " + sorter + sorting_direction + " limit ?, ?";

        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            statement = connection.createStatement();
            get_data_routerConnections = connection.prepareStatement(get_data);
        }catch (SQLException e) {
            logger.warn("PrepareStatement or Statement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            for(String SQLCommand : SQLCommandForGetTableOfRouters){
                statement.executeUpdate(SQLCommand);
            }

            get_data_routerConnections.setInt(1, (page-1)*itemsPerPage);
            get_data_routerConnections.setInt(2, itemsPerPage);

            resultSet = get_data_routerConnections.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterConnectionEntity routerConnection = new RouterConnectionEntity();

                    routerConnection.setId(resultSet.getInt("ID"));
                    routerConnection.setFirstRouterCityName(resultSet.getString("City1"));
                    routerConnection.setFirstRouterSN(resultSet.getString("SN1"));
                    routerConnection.setFirstRouterId(resultSet.getInt("Id1"));
                    routerConnection.setSecondRouterCityName(resultSet.getString("City2"));
                    routerConnection.setSecondRouterSN(resultSet.getString("SN2"));
                    routerConnection.setSecondRouterId(resultSet.getInt("Id2"));

                    routerConnections.add(routerConnection);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(
                    String.format("Put data to PrepareStatement in %s invalid", nameTable), e);
        }
        finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }

            if (get_data_routerConnections!=null){
                try {
                    get_data_routerConnections.close();
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

        return routerConnections.toArray(new RouterConnectionEntity[routerConnections.size()]);
    }


    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement)
            throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterConnectionEntity routerConnection = null;

        String insert = "insert into" + nameTable +
                "(`ID_From`, `ID_To`)" +
                " values (?, ?)";

        PreparedStatement preparedStatement = null;

        try {
            routerConnection = (RouterConnectionEntity) newElement;
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

        String log_parameters = "With parameters: Id_From("+ routerConnection.getFirstRouterId()
                + "), Id_To(" + routerConnection.getSecondRouterId() + ")";

        try {
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());

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
        RouterConnectionEntity  routerConnection = null;

        PreparedStatement search_routerConnection = null;
        ResultSet resultSet= null;

        String search = "select * from" + nameTable + "where `id`=?";

        try {
            routerConnection  = (RouterConnectionEntity)readElement;
        }catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        String log_parameters = "With parameters: Id("+ routerConnection.getId() + ")";

        if(routerConnection.getId() != 0) {
            try{
                search_routerConnection = connection.prepareStatement(search);
            }catch (SQLException e) {
                logger.warn("PreparedStatement in read wasn't created", e);
                throw new InternalDAOException("PreparedStatement in read wasn't created", e);
            }

            try {
                search_routerConnection.setInt(1, readElement.getId());

                resultSet = search_routerConnection.executeQuery();

                if(resultSet.first()) {
                    routerConnection.setId(resultSet.getInt("id"));
                    routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                    routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));

                    logger.trace("Read {}.\n {}", nameTable, log_parameters);
                }
                else{
                    logger.info("{} in read not found.\n {}", nameTable, log_parameters);
                    throw new InvalidDataDAOException(String.format("%s in read not found", nameTable));
                }
            }catch (SQLException e) {
                logger.info("Read {} failed.\n {}", nameTable, log_parameters, e);
                throw new InternalDAOException(String.format("Read %s failed", nameTable), e);
            }
            finally {
                if (search_routerConnection!=null){
                    try {
                        search_routerConnection.close();
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
            logger.info("For reading router connection incorrectly chosen field, try id");
            throw new InvalidDataDAOException("For reading router connection incorrectly chosen field, try id");
        }
    }

    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement)
            throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        RouterConnectionEntity routerConnection = null;

        PreparedStatement preparedStatement = null;

        String update = "update" + nameTable + "set `ID_From`=?, `ID_To`=? where `id`=?";

        try {
            routerConnection = (RouterConnectionEntity) updateElement;
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

        String log_parameters = "With parameters: id(" + routerConnection.getId() + ")";

        try {
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());
            preparedStatement.setInt(3, routerConnection.getId());

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
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
            throws InvalidDataDAOException, InternalDAOException {

        Statement statement = null;
        PreparedStatement get_data_routerConnections = null;
        ResultSet resultSet = null;

        ArrayList<String> SQLCommandForGetTableOfRouters = new ArrayList<>();

        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();

        String sorter = hashMap.get(sortBy);

        String get_data = "";

        String log_parameters = "With parameters: page("+ page + "), itemsPerPage(" + itemsPerPage
                + "), sortBy(" + sortBy + "), asc(" + asc + "), City(" + city.getName() +
                "), Country(" + city.getCountryName() + ")";

        if (city.getName() == null || city.getCountryName() == null){
            logger.info("For GetPage connection of City connection incorrectly" +
                    " chosen field, try Name and CountryName");
            throw new InvalidDataDAOException("For GetPage connection of City connection incorrectly" +
                    " chosen field, try Name and CountryName");
        }

        if(sorter != null) {
            String sorting_direction = "";

            if(asc){
                sorting_direction = " asc ";
            }
            else {
                sorting_direction = " desc ";
            }

            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS RoutersOfCity");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS ConnectionsOfRouters");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS CitySNFrom");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS CitySNTo");

            SQLCommandForGetTableOfRouters.add("set @id_City = (select id from City" +
                    " where `Name` = '" + city.getName() +
                    "' and `Country` = '" + city.getCountryName() + "')");

            SQLCommandForGetTableOfRouters.add("create temporary table RoutersOfCity " +
                    "select ID from Router where City_id = @id_City");

            SQLCommandForGetTableOfRouters.add("create temporary table ConnectionsOfRouters " +
                    "select R.ID as ID, R.ID_From, R.ID_To " +
                    "from RouterConnection R join RoutersOfCity C " +
                    "on  R.ID_From = C.ID or R.ID_To = C.ID " +
                    "Group by ID");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNFrom " +
                    "select T.ID, C.`Name`, C.SN, T.ID_From from ConnectionsOfRouters T " +
                    "join CitySN C on T.ID_From = C.ID");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNTo " +
                    "select T.ID, C.`Name`, C.SN, T.ID_To from ConnectionsOfRouters T " +
                    "join CitySN C on T.ID_To = C.ID");


            get_data = "select C1.ID as ID, C1.`Name` as City1, C1.SN as SN1, C1.ID_From as Id1, " +
                    "C2.`Name` as City2, C2.SN as SN2, C2.ID_To as Id2 " +
                    "from CitySNFrom C1 join CitySNTo C2 on C1.ID = C2.ID " +
                    "order by " + sorter + sorting_direction + " limit ?, ?";

        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            statement = connection.createStatement();
            get_data_routerConnections = connection.prepareStatement(get_data);
        }catch (SQLException e) {
            logger.warn("PrepareStatement or Statement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            for(String SQLCommand : SQLCommandForGetTableOfRouters){
                statement.executeUpdate(SQLCommand);
            }

            get_data_routerConnections.setInt(1, (page-1)*itemsPerPage);
            get_data_routerConnections.setInt(2, itemsPerPage);

            resultSet = get_data_routerConnections.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterConnectionEntity routerConnection = new RouterConnectionEntity();

                    routerConnection.setId(resultSet.getInt("ID"));
                    routerConnection.setFirstRouterCityName(resultSet.getString("City1"));
                    routerConnection.setFirstRouterSN(resultSet.getString("SN1"));
                    routerConnection.setFirstRouterId(resultSet.getInt("Id1"));
                    routerConnection.setSecondRouterCityName(resultSet.getString("City2"));
                    routerConnection.setSecondRouterSN(resultSet.getString("SN2"));
                    routerConnection.setSecondRouterId(resultSet.getInt("Id2"));

                    routerConnections.add(routerConnection);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(
                    String.format("Put data to PrepareStatement in %s invalid", nameTable), e);
        }
        finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }

            if (get_data_routerConnections!=null){
                try {
                    get_data_routerConnections.close();
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

        return routerConnections.toArray(new RouterConnectionEntity[routerConnections.size()]);
    }

    /**
     * @param city
     * @return
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public int count_element(CityEntity city) throws InternalDAOException, InvalidDataDAOException {

        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String search = "";

        if (city.getName() != null && city.getCountryName() != null){
            search = "select count(`RouterConnection`.`ID`) from `RouterConnection` inner join " +
                        "(select `Router`.`ID` " +
                            "from `Router` inner join `City` " +
                            "on (`Router`.`City_id`=`City`.`ID`) " +
                            "where (`City`.`Name` = ? and `City`.`Country` = ?) " +
                        ") `routers`" +
                    "on (`RouterConnection`.`ID_From` = `routers`.`ID` " +
                    " or `RouterConnection`.`ID_To` = `routers`.`ID`) ";
        }
        else{
            logger.info("For reading router connection incorrectly chosen field, try name and country");
            throw new InvalidDataDAOException("For reading router connection incorrectly chosen field," +
                    " try name and country");
        }

        try {
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
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @param router
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    @Override
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, RouterEntity router)
            throws InvalidDataDAOException, InternalDAOException {

        Statement statement = null;
        PreparedStatement get_data_routerConnections = null;
        ResultSet resultSet = null;

        ArrayList<String> SQLCommandForGetTableOfRouters = new ArrayList<>();

        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();

        String sorter = hashMap.get(sortBy);

        String get_data = "";

        String log_parameters = "With parameters: page("+ page + "), itemsPerPage(" + itemsPerPage
                + "), sortBy(" + sortBy + "), asc(" + asc + "), SN(" + router.getSN() + ")";

        if (router.getSN() == null){
            logger.info("For GetPage connection of SN connection incorrectly, " +
                    "chosen field, try SN");
            throw new InvalidDataDAOException("For GetPage connection router connection incorrectly," +
                    " chosen field, try SN");
        }

        if(sorter != null) {
            String sorting_direction = "";

            if(asc){
                sorting_direction = " asc ";
            }
            else {
                sorting_direction = " desc ";
            }

            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS `temp`");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS `CitySNFrom`");
            SQLCommandForGetTableOfRouters.add("DROP TABLE IF EXISTS `CitySNTo`");

            SQLCommandForGetTableOfRouters.add("set @id_SN = (select id from Router where SN = '" +
                    router.getSN() + "')");

            SQLCommandForGetTableOfRouters.add("create temporary table temp" +
                    " select * from RouterConnection where ID_From = @id_SN or ID_To = @id_SN");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNFrom " +
                    "select T.ID, C.`Name`, C.SN, T.ID_From from temp T join CitySN C on T.ID_From = C.ID");

            SQLCommandForGetTableOfRouters.add("create temporary table CitySNTo" +
                    " select T.ID, C.`Name`, C.SN, T.ID_To from temp T join CitySN C on T.ID_To = C.ID");


            get_data = "select C1.ID as ID, C1.`Name` as City1, C1.SN as SN1, C1.ID_From as Id1, " +
                    "C2.`Name` as City2, C2.SN as SN2, C2.ID_To as Id2 " +
                    "from CitySNFrom C1 join CitySNTo C2 on C1.ID = C2.ID " +
                    "order by " + sorter + sorting_direction + " limit ?, ?";

        }
        else {
            logger.info("Enter parameter to sort in read {} are invalid.\n {}", nameTable, log_parameters);
            throw new InvalidDataDAOException("Enter parameter to sort in read are invalid");
        }

        try {
            statement = connection.createStatement();
            get_data_routerConnections = connection.prepareStatement(get_data);
        }catch (SQLException e) {
            logger.warn("PrepareStatement or Statement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            for(String SQLCommand : SQLCommandForGetTableOfRouters){
                statement.executeUpdate(SQLCommand);
            }

            get_data_routerConnections.setInt(1, (page-1)*itemsPerPage);
            get_data_routerConnections.setInt(2, itemsPerPage);

            resultSet = get_data_routerConnections.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterConnectionEntity routerConnection = new RouterConnectionEntity();

                    routerConnection.setId(resultSet.getInt("ID"));
                    routerConnection.setFirstRouterCityName(resultSet.getString("City1"));
                    routerConnection.setFirstRouterSN(resultSet.getString("SN1"));
                    routerConnection.setFirstRouterId(resultSet.getInt("Id1"));
                    routerConnection.setSecondRouterCityName(resultSet.getString("City2"));
                    routerConnection.setSecondRouterSN(resultSet.getString("SN2"));
                    routerConnection.setSecondRouterId(resultSet.getInt("Id2"));

                    routerConnections.add(routerConnection);
                }

                logger.trace("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }
        }catch (SQLException e){
            logger.info("Put data to PrepareStatement in {} invalid. \n {}", nameTable, log_parameters, e);
            throw new InvalidDataDAOException(
                    String.format("Put data to PrepareStatement in %s invalid", nameTable), e);
        }
        finally {
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.warn("Close Statement in getPage {} failed", nameTable, e);
                    throw new InternalDAOException(e);
                }
            }

            if (get_data_routerConnections!=null){
                try {
                    get_data_routerConnections.close();
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

        return routerConnections.toArray(new RouterConnectionEntity[routerConnections.size()]);
    }

    /**
     * @param router
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public int count_element(RouterEntity router) throws InvalidDataDAOException, InternalDAOException {
        int count = 0;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String search = "";

        if (router.getSN() != null){
            search = "select count(`RouterConnection`.`ID`) from RouterConnection inner join `Router` " +
                     "on (`RouterConnection`.`ID_From` = `Router`.`ID` " +
                     "or `RouterConnection`.`ID_To` = `Router`.`ID`) " +
                     "where `Router`.`SN` = ?";
        }
        else{
            logger.info("For reading router connection incorrectly chosen field, try SN");
            throw new InvalidDataDAOException("For reading router connection incorrectly chosen field," +
                    " try SN");
        }

        try {
            preparedStatement = connection.prepareStatement(search);
        } catch (SQLException e) {
            logger.warn("PrepareStatement in get count wasn't created", e);
            throw new InternalDAOException("PrepareStatement in get count wasn't created", e);
        }

        String log_parameters = "With parameters: SN(" + router.getSN() + ")";

        try {
            preparedStatement.setString(1, router.getSN());

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
    public static RouterConnectionDAO getInstance() throws InternalDAOException {
        return new RouterConnectionDAO();
    }
}