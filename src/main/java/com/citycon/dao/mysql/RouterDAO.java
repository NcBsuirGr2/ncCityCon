package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO{

    /**
     * @throws DAOException
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
     * @throws InternalDAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) 
                                                throws InvalidDataDAOException, InternalDAOException {

        if(false) {
            throw new InvalidDataDAOException();
        }
        ArrayList<RouterEntity> routers = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_routers = connection.prepareStatement(search);
            search_routers.setInt(1, (page-1)*itemsPerPage);
            search_routers.setInt(2, itemsPerPage);

            ResultSet resultSet =  search_routers.executeQuery();

            while(resultSet.next()) {
                RouterEntity router = new RouterEntity();
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.setIsActive(resultSet.getBoolean("In_Service"));
                router.setCityId(resultSet.getInt("City_id"));
                routers.add(router);
            }
            resultSet.close();
            search_routers.close();
        }catch (SQLException e){
            throw new InternalDAOException("GetPage router failed", e);
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
     * @throws NotFoundDAOException, InternalDAOException, InvalidDataDAOException
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
                    router.setIsActive(resultSet.getBoolean("In_Service"));
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
