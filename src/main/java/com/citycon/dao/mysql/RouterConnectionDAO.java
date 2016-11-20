package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterConnectionDAO extends MySQLDAO {

    /**
     * @throws InternalDAOException
     */
    private RouterConnectionDAO() throws InternalDAOException {
        super();
        nameTable = " `RouterConnection` ";
        logger = LoggerFactory.getLogger("com.citycon.dao.mysql.RouterConnectionDAO");

        hashMap.put("", "`ID`");
        hashMap.put("id", "`ID`");
        hashMap.put("id_from", "`ID_From`");
        hashMap.put("id_to", "`ID_To`");
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

        PreparedStatement search_routerConnections = null;
        ResultSet resultSet = null;

        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();

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
            search_routerConnections = connection.prepareStatement(search);
        }catch (SQLException e) {
            logger.warn("PrepareStatement in getPage wasn't created");
            throw new InternalDAOException("PrepareStatement in getPage wasn't created", e);
        }

        try {
            search_routerConnections.setInt(1, (page-1)*itemsPerPage);
            search_routerConnections.setInt(2, itemsPerPage);

            resultSet =  search_routerConnections.executeQuery();

            try {
                while (resultSet.next()) {
                    RouterConnectionEntity routerConnection = new RouterConnectionEntity();
                    routerConnection.setId(resultSet.getInt("id"));
                    routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                    routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));
                    routerConnections.add(routerConnection);
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
            if (search_routerConnections!=null){
                try {
                    search_routerConnections.close();
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
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterConnectionEntity routerConnection = null;

        String insert = "insert into" + nameTable +
                "`ID_From`, `ID_To`" +
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
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
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
     * @throws InternalDAOException
     */
    public static RouterConnectionDAO getInstance() throws InternalDAOException {
        return new RouterConnectionDAO();
    }
}
