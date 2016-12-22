package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.dao.interfaces.ConnectionsOfCity;
import com.citycon.dao.interfaces.ConnectionsOfRouter;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import com.citycon.model.systemunits.entities.RouterEntity;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * DAO wrapper for the <code>ORMRouterConnection</code>. This class must be used in business logic for
 * deliver CRUD operations for the plain ORMEntity. DAO object
 * is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass
 * <code>MySQLDAO</code>.
 *
 * @author Alex
 * @version 2.0
 */
public class RouterConnectionDAO extends MySQLDAO implements ConnectionsOfCity, ConnectionsOfRouter {

    public RouterConnectionDAO() throws InternalDAOException {
        super();
        nameTable = " `RouterConnection` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.RouterConnectionDAO");

        hashMap.put("", "`City1`");
        hashMap.put("id", "`ID`");
        hashMap.put("City1", "`City1`");
        hashMap.put("Country1", "`Country1`");
        hashMap.put("SN1", "`SN1`");
        hashMap.put("ID1", "`Id1`");
        hashMap.put("City2", "`City2`");
        hashMap.put("Country2", "`Country2`");
        hashMap.put("SN2", "`SN2`");
        hashMap.put("ID2", "`Id2`");
    }

    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input)
            throws InvalidDataDAOException, InternalDAOException {

        return getPage(page, itemsPerPage, sortBy, asc, null, null, search_input);
    }

    public void create(Entity newElement)
            throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {

        RouterConnectionEntity routerConnection = null;

        String insert = "";

        String log_parameters = "";

        try {
            routerConnection = (RouterConnectionEntity) newElement;
        } catch (ClassCastException e) {
            logger.info("Cast Entity in create failed.", e);
            throw new InvalidDataDAOException("Cast Entity in create are failed", e);
        }

        if (routerConnection.getFirstRouter().getSN() != null && routerConnection.getSecondRouter().getSN() != null){
            insert = "insert into RouterConnection (ID_From, ID_To) " +
                    "values ((select ID from Router where SN=?), " +
                    "(select ID from Router where SN=?))";

            log_parameters = "With parameters: SN_From(" + routerConnection.getFirstRouter().getSN()
                    + "), SN_To(" + routerConnection.getSecondRouter().getSN() + ")";
        }
        else {
            insert = "insert into" + nameTable +
                "(`ID_From`, `ID_To`)" +
                " values (?, ?)";

            log_parameters = "With parameters: Id_From(" + routerConnection.getFirstRouter().getId()
                    + "), Id_To(" + routerConnection.getSecondRouter().getId() + ")";
        }

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(insert);
        ){

            try {
                if (routerConnection.getFirstRouter().getSN() != null && routerConnection.getSecondRouter().getSN() != null){
                    preparedStatement.setString(1, routerConnection.getFirstRouter().getSN());
                    preparedStatement.setString(2, routerConnection.getSecondRouter().getSN());
                }
                else {
                    preparedStatement.setInt(1, routerConnection.getFirstRouter().getId());
                    preparedStatement.setInt(2, routerConnection.getSecondRouter().getId());
                }

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

    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {
        RouterConnectionEntity routerConnection = null;

        String search = "";

        try {
            routerConnection = (RouterConnectionEntity) readElement;
        } catch (ClassCastException e) {
            logger.info("Cast Entity in read failed.", e);
            throw new InvalidDataDAOException("Cast Entity in read failed.", e);
        }

        if (routerConnection.getId() == 0) {
            logger.info("For reading routerConnection connection incorrectly chosen field, try id");
            throw new InvalidDataDAOException("For reading routerConnection " +
                    "connection incorrectly chosen field, try id");
        }

        String log_parameters = "With parameters: Id(" + routerConnection.getId() + ")";

        search = "SELECT DISTINCT C1.ID AS ID, C1.`Name` AS City1, C1.Country AS Country1, C1.SN AS SN1, " +
                "C1.ID_From AS Id1, C1.In_Service as FirstAvailable, " +
                "C2.`Name` AS City2, C2.Country AS Country2, C2.SN AS SN2, " +
                "C2.ID_To AS Id2, C2.In_Service as SecondAvailable " +
                "FROM (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_From " +
                "from RouterConnection T join CitySN C on T.ID_From = C.ID left outer join Router R on R.SN = C.SN " +
                "where T.ID=" + routerConnection.getId() + ") C1 " +
                "JOIN (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_To " +
                "from RouterConnection T join CitySN C on T.ID_To = C.ID left outer join Router R on R.SN = C.SN " +
                "where T.ID=" + routerConnection.getId() + ") C2 ON C1.ID = C2.ID ";

        try(
                Connection connection = connections.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(search);
        ){
            if (resultSet.first()) {
                routerConnection.setId(resultSet.getInt("ID"));

                CityEntity cityFirstConnection = new CityEntity();
                cityFirstConnection.setName(resultSet.getString("City1"));
                cityFirstConnection.setCountryName(resultSet.getString("Country1"));

                RouterEntity routerFirstConnection = new RouterEntity();
                routerFirstConnection.setSN(resultSet.getString("SN1"));
                routerFirstConnection.setId(resultSet.getInt("Id1"));
                routerFirstConnection.setActive(resultSet.getBoolean("FirstAvailable"));

                routerFirstConnection.setCity(cityFirstConnection);
                routerConnection.setFirstRouter(routerFirstConnection);

                CityEntity citySecondConnection = new CityEntity();
                citySecondConnection.setName(resultSet.getString("City2"));
                citySecondConnection.setCountryName(resultSet.getString("Country2"));

                RouterEntity routerSecondConnection = new RouterEntity();
                routerSecondConnection.setSN(resultSet.getString("SN2"));
                routerSecondConnection.setId(resultSet.getInt("Id2"));
                routerSecondConnection.setActive(resultSet.getBoolean("SecondAvailable"));

                routerSecondConnection.setCity(citySecondConnection);
                routerConnection.setSecondRouter(routerSecondConnection);

                logger.debug("Read {}.\n {}", nameTable, log_parameters);
            } else {
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
        RouterConnectionEntity routerConnection = null;

        String update = "";

        try {
            routerConnection = (RouterConnectionEntity) updateElement;
        } catch (ClassCastException e) {
            logger.info("Cast Entity in update failed.", e);
            throw new InvalidDataDAOException("Cast Entity in update failed.", e);
        }

        if (routerConnection.getFirstRouter().getSN() != null && routerConnection.getSecondRouter().getSN() != null) {
            update = "update RouterConnection set ID_From = (select ID from Router where SN=?), ID_To = (select ID from Router where SN=?) where id=?";
        }
        else {
            update = "update RouterConnection set `ID_From`=?, `ID_To`=? where `id`=?";
        }

        String log_parameters = "With parameters: id(" + routerConnection.getId() + ")";

        try(
                Connection connection = connections.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(update);
        ) {

            try {
                if (routerConnection.getFirstRouter().getSN() != null && routerConnection.getSecondRouter().getSN() != null){
                    preparedStatement.setString(1, routerConnection.getFirstRouter().getSN());
                    preparedStatement.setString(2, routerConnection.getSecondRouter().getSN());
                }
                else {
                    preparedStatement.setInt(1, routerConnection.getFirstRouter().getId());
                    preparedStatement.setInt(2, routerConnection.getSecondRouter().getId());
                }

                preparedStatement.setInt(3, routerConnection.getId());

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
    public int count_element(String search_input) throws InternalDAOException, InvalidDataDAOException {
        return count_element(search_input, null, null);
    }

    public int count_element(String search_input, CityEntity city) throws InternalDAOException, InvalidDataDAOException {
        return count_element(search_input, null, city);
    }

    public int count_element(String search_input, RouterEntity router) throws InvalidDataDAOException, InternalDAOException {
        return count_element(search_input, router, null);
    }

    private int count_element(String search_input, RouterEntity router, CityEntity city) throws InvalidDataDAOException, InternalDAOException {
        String search = "";
        String add_table = "";

        String log_parameters = "";

        if (router != null) {
            if (router.getSN() == null) {
                logger.info("For count_element connection of SN connection incorrectly, " +
                        "chosen field, try SN");
                throw new InvalidDataDAOException("For count_element connection router connection incorrectly," +
                        " chosen field, try SN");
            } else {
                log_parameters += ", SN(" + router.getSN() + ")";

                add_table = "(select * from RouterConnection where ID_From=(select id from Router where SN = '" +
                        router.getSN() + "') OR ID_To = (select id from Router where SN = '" +
                        router.getSN() + "'))";
            }
        } else if (city != null) {
            if (city.getName() == null || city.getCountryName() == null) {
                logger.info("For count_element connection of City connection incorrectly" +
                        " chosen field, try Name and CountryName");
                throw new InvalidDataDAOException("For count_element connection of City connection incorrectly" +
                        " chosen field, try Name and CountryName");
            } else {
                log_parameters += ", City(" + city.getName() + "), Country(" + city.getCountryName() + ")";

                add_table = "(select distinct R.ID as ID, R.ID_From, R.ID_To from RouterConnection R " +
                        "join (select ID from Router where City_id = (select id from City where `Name` = '" +
                        city.getName() + "' and `Country` = '" + city.getCountryName() + "')) " +
                        "Ro on Ro.id=R.id_from or Ro.id=R.id_to)";
            }
        } else {
            add_table = "RouterConnection";
        }

        search = "SELECT COUNT(1) " +
                "FROM (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_From " +
                "from " + add_table + " T, CitySN C, Router R where T.ID_From = C.id and R.SN = C.SN) C1 " +
                "JOIN (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_To " +
                "from " + add_table + " T, CitySN C, Router R where T.ID_To = C.id and R.SN = C.SN) C2 " +
                "ON C1.ID = C2.ID " +
                "WHERE C1.`Name` LIKE '%" + search_input + "%' OR C1.SN LIKE '%" + search_input + "%' OR " +
                "C2.`Name` LIKE '%" + search_input + "%' OR C2.SN LIKE '%" + search_input + "%' ";

        return count_search(search);
    }

    @Override
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input , CityEntity city)
            throws InvalidDataDAOException, InternalDAOException {

        return getPage(page, itemsPerPage, sortBy, asc, null, city, search_input);
    }

    @Override
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, String search_input , RouterEntity router)
            throws InvalidDataDAOException, InternalDAOException {

        return getPage(page, itemsPerPage, sortBy, asc, router, null, search_input);
    }

    private RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc,
                                             RouterEntity router, CityEntity city, String search_input)
            throws InvalidDataDAOException, InternalDAOException {
        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();

        String sorter = hashMap.get(sortBy);

        String search = "";
        String add_table = "";

        String log_parameters = "With parameters: page(" + page + "), itemsPerPage(" + itemsPerPage
                + "), sortBy(" + sortBy + "), asc(" + asc + ")";

        if (router != null) {
            if (router.getSN() == null) {
                logger.info("For GetPage connection of SN connection incorrectly, " +
                        "chosen field, try SN");
                throw new InvalidDataDAOException("For GetPage connection router connection incorrectly," +
                        " chosen field, try SN");
            } else {
                log_parameters += ", SN(" + router.getSN() + ")";

                add_table = "(select * from RouterConnection where ID_From=(select id from Router where SN = '" +
                        router.getSN() + "') OR ID_To = (select id from Router where SN = '" +
                        router.getSN() + "'))";
            }
        } else if (city != null) {
            if (city.getName() == null || city.getCountryName() == null) {
                logger.info("For GetPage connection of City connection incorrectly" +
                        " chosen field, try Name and CountryName");
                throw new InvalidDataDAOException("For GetPage connection of City connection incorrectly" +
                        " chosen field, try Name and CountryName");
            } else {
                log_parameters += ", City(" + city.getName() + "), Country(" + city.getCountryName() + ")";

                add_table = "(select distinct R.ID as ID, R.ID_From, R.ID_To from RouterConnection R " +
                        "join (select ID from Router where City_id = (select id from City where `Name` = '" +
                        city.getName() + "' and `Country` = '" + city.getCountryName() + "')) " +
                        "Ro on Ro.id=R.id_from or Ro.id=R.id_to)";
            }
        } else {
            add_table = "RouterConnection";
        }

        if (sorter != null) {
            String sorting_direction = "";

            if (asc) {
                sorting_direction = " asc ";
            } else {
                sorting_direction = " desc ";
            }

            search = "SELECT DISTINCT C1.ID AS ID, C1.`Name` AS City1, C1.Country AS Country1, C1.SN AS SN1, " +
                    "C1.ID_From AS Id1, C1.In_Service as FirstAvailable, " +
                    "C2.`Name` AS City2, C2.Country AS Country2, C2.SN AS SN2, " +
                    "C2.ID_To AS Id2, C2.In_Service as SecondAvailable " +
                    "FROM (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_From " +
                    "from " + add_table + " T, CitySN C, Router R where T.ID_From = C.id and R.SN = C.SN) C1 " +
                    "JOIN (select distinct T.ID as ID, R.In_Service, C.`Name`, C.Country, C.SN, T.ID_To " +
                    "from " + add_table + " T, CitySN C, Router R where T.ID_To = C.id and R.SN = C.SN) C2 " +
                    "ON C1.ID = C2.ID " +
                    "WHERE C1.`Name` LIKE '%" + search_input + "%' OR C1.SN LIKE '%" + search_input + "%' OR " +
                    "C2.`Name` LIKE '%" + search_input + "%' OR C2.SN LIKE '%" + search_input + "%' " +
                    "order by " + sorter + sorting_direction +
                    " limit " + ((page-1)*itemsPerPage) + "," + itemsPerPage;
        } else {
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
                    RouterConnectionEntity routerConnection = new RouterConnectionEntity();

                    routerConnection.setId(resultSet.getInt("ID"));

                    CityEntity cityFirstConnection = new CityEntity();
                    cityFirstConnection.setName(resultSet.getString("City1"));
                    cityFirstConnection.setCountryName(resultSet.getString("Country1"));

                    RouterEntity routerFirstConnection = new RouterEntity();
                    routerFirstConnection.setSN(resultSet.getString("SN1"));
                    routerFirstConnection.setId(resultSet.getInt("Id1"));
                    routerFirstConnection.setActive(resultSet.getBoolean("FirstAvailable"));

                    routerFirstConnection.setCity(cityFirstConnection);
                    routerConnection.setFirstRouter(routerFirstConnection);

                    CityEntity citySecondConnection = new CityEntity();
                    citySecondConnection.setName(resultSet.getString("City2"));
                    citySecondConnection.setCountryName(resultSet.getString("Country2"));

                    RouterEntity routerSecondConnection = new RouterEntity();
                    routerSecondConnection.setSN(resultSet.getString("SN2"));
                    routerSecondConnection.setId(resultSet.getInt("Id2"));
                    routerSecondConnection.setActive(resultSet.getBoolean("SecondAvailable"));

                    routerSecondConnection.setCity(citySecondConnection);
                    routerConnection.setSecondRouter(routerSecondConnection);

                    routerConnections.add(routerConnection);
                }

                logger.debug("GetPage of {}.\n {}", nameTable, log_parameters);
            }catch (SQLException e){
                logger.info("GetPage {} failed.\n {}", nameTable, log_parameters, e);
                throw new InvalidDataDAOException(String.format("GetPage %s failed", nameTable), e);
            }

        } catch (SQLException e) {
            logger.warn("Resources wasn't created for getPage in {}", nameTable,e);
            throw new InternalDAOException("Resources wasn't created for getPage in " + nameTable, e);
        }

        return routerConnections.toArray(new RouterConnectionEntity[routerConnections.size()]);
    }
}