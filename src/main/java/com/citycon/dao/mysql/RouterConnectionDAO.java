package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;

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
        nameTable = " RouterConnection ";
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
        String search = "select * from" + nameTable + "limit ?,?";

        try {
            search_routerConnections = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in get RouterConnectionPage wasn't created", e);
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
            }catch (SQLException e){
                throw new InvalidDataDAOException("GetPage routerConnection failed", e);
            }
        }catch (SQLException e){
            throw new InvalidDataDAOException("Put data to prepare statement in routerConnection invalid",e);
        }
        finally {
            if (search_routerConnections!=null){
                try {
                    search_routerConnections.close();
                } catch (SQLException e) {
                    throw new InternalDAOException(e);
                }
            }
            if (resultSet!= null){
                try{
                    resultSet.close();
                }catch (SQLException e){
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
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in create RouterConnection wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException("Create RouterConnection failed", e);
        }
        finally {
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
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

        String search = "select * from" + nameTable + "where id=?";

        try {
            routerConnection  = (RouterConnectionEntity)readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        if(routerConnection.getId() != 0) {
            try{
                search_routerConnection = connection.prepareStatement(search);
            }catch (SQLException e) {
                throw new InternalDAOException("Prepare statement in read router connection wasn't created", e);
            }

            try {
                search_routerConnection.setInt(1, readElement.getId());

                resultSet = search_routerConnection.executeQuery();

                if(resultSet.first()) {
                    routerConnection.setId(resultSet.getInt("id"));
                    routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                    routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));
                }
                else{
                    throw new InvalidDataDAOException("Router connection not found");
                }
            }catch (SQLException e) {
                throw new InternalDAOException("Read router connection failed", e);
            }
            finally {
                if (search_routerConnection!=null){
                    try {
                        search_routerConnection.close();
                    } catch (SQLException e) {
                        throw new InternalDAOException(e);
                    }
                }
                if (resultSet!= null){
                    try{
                        resultSet.close();
                    }catch (SQLException e){
                        throw new InternalDAOException(e);
                    }
                }
            }
        }
        else{
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
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in update router connection wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());
            preparedStatement.setInt(3, routerConnection.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException("Update router connection failed", e);
        }
        finally {
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
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
