package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO implements RoutersOfCity{


    /**
     * @throws InternalDAOException
     */
    private RouterDAO() throws InternalDAOException {
        super();
        nameTable = " Router ";
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
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @param city
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, CityEntity city)
                                                throws InvalidDataDAOException, InternalDAOException {

        PreparedStatement search_routers = null;
        ResultSet resultSet = null;

        ArrayList<RouterEntity> routers = new ArrayList();
        String search = "";

        if (city != null){
            search = "select * from" + nameTable + "limit ?,? where City_id=" + String.valueOf(city.getId());
        }
        else {
            search = "select * from" + nameTable + "limit ?,?";
        }

        try {
            search_routers = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in get routerPage wasn't created", e);
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
                    router.isActive(resultSet.getBoolean("In_Service"));
                    router.setCityId(resultSet.getInt("City_id"));
                    routers.add(router);
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException("GetPage router failed", e);
            }
        }catch (SQLException e){
            throw new InternalDAOException("GetPage router failed", e);
        }
        finally {
            if (search_routers!=null){
                try {
                    search_routers.close();
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

        return routers.toArray(new RouterEntity[routers.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException, InternalDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        if(false) {
            throw new InvalidDataDAOException();
        }
        try{
            RouterEntity router = (RouterEntity) newElement;

            String insert = "insert into" + nameTable +
                    "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                    " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            try {
                preparedStatement.setString(1, router.getName());
                preparedStatement.setString(2, router.getSN());
                preparedStatement.setInt(3, router.getPortsNum());
                preparedStatement.setBoolean(4, router.isActive());
                preparedStatement.setInt(5, router.getCityId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e){
                throw new DublicateKeyDAOException("Create router failed", e);
            }

        }catch (SQLException e){
            throw new InternalDAOException("Create router failed", e);
        }
    }

    /**
     * @param readElement
     * @throws  InternalDAOException, InvalidDataDAOException
     */
    public void read(Entity readElement)throws InternalDAOException, InvalidDataDAOException {
        
        if(false) {
            throw new InvalidDataDAOException();
        }
        RouterEntity router = (RouterEntity)readElement;
        String field = "";
        String value = "";
        try {
            if(router.getId() != 0) {
                field = "id";
                value = String.valueOf(router.getId());
            }
            else if(router.getSN() != null){
                field = "SN";
                value = "'" + router.getSN() + "'";
            }

            String search = "select * from" + nameTable + "where " + field + "=" + value;
            PreparedStatement search_router = connection.prepareStatement(search);

            try {
                ResultSet resultSet = search_router.executeQuery();

                if(resultSet.first()) {
                    router.setId(resultSet.getInt("id"));
                    router.setName(resultSet.getString("Name"));
                    router.setSN(resultSet.getString("SN"));
                    router.setPortsNum(resultSet.getInt("Port"));
                    router.isActive(resultSet.getBoolean("In_Service"));
                    router.setCityId(resultSet.getInt("City_id"));
                }
                resultSet.close();
                search_router.close();
            }catch (SQLException e){
                throw new InvalidDataDAOException("Read router failed", e);
            }

        }catch (SQLException e){
            throw new InternalDAOException("Read router failed", e);
        }
    }

    /**
     * @param updateElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        if (false) {
            throw new DublicateKeyDAOException();
        }
        try {
            RouterEntity router = (RouterEntity) updateElement;

            String update = "update" + nameTable +
                    "set `Name`=?, `SN`=?, `Port`=?, `In_Service`=?, `City_id`=? " +
                    "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            try {
                preparedStatement.setString(1, router.getName());
                preparedStatement.setString(2, router.getSN());
                preparedStatement.setInt(3, router.getPortsNum());
                preparedStatement.setBoolean(4, router.isActive());
                preparedStatement.setInt(5, router.getCityId());
                preparedStatement.setInt(6, router.getId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Update router failed", e);
            }

        } catch (SQLException e) {
            throw new InternalDAOException("Update router failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException {
        try {
            RouterEntity router = (RouterEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            try {
                preparedStatement.setInt(1, router.getId());
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Delete router failed", e);
            }
        } catch (SQLException e) {
            throw new InternalDAOException("Delete router failed", e);
        }
    }
    /**
     * @throws InternalDAOException
     */
    public static RouterDAO getInstance() throws InternalDAOException {
        return new RouterDAO();
    }
}
