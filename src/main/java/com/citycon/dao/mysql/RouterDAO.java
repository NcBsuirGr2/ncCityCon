package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterEntity;
import com.citycon.model.systemunits.entities.UserEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Vojts on 09.11.2016.
 */
public class RouterDAO extends MySQLDAO{
    private static volatile RouterDAO instance;

    private RouterDAO() throws DAOException {
        super();
        nameTable = " Router";
    }

    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        ArrayList<RouterEntity> routers = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_routers = connection.prepareStatement(search);
            search_routers.setInt((int)1, page*itemsPerPage);
            search_routers.setInt((int)2, itemsPerPage);

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
            throw new DAOException("GetPage router failed\n" + e.toString());
        }
        return (RouterEntity[]) routers.toArray();
    }

    public void create(Entity newElement) throws DAOException {
        try{
            RouterEntity router = (RouterEntity) newElement;

            String insert = "insert into" + nameTable +
                    "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                    " values (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (SQLException e){
            throw new DAOException("Create router failed");
        }
    }

    public Entity read(int id) throws DAOException {
        RouterEntity router = null;
        try {
            String search = "select * from" + nameTable + "when id=?";

            PreparedStatement search_router = connection.prepareStatement(search);
            search_router.setInt((int)1, id);

            ResultSet resultSet =  search_router.executeQuery();

            while(resultSet.next()) {
                router = new RouterEntity();
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
            throw new DAOException("Read router failed\n" + e.toString());
        }
        return router;    }

    public void update(Entity updateElement) throws DAOException {
        try {
            RouterEntity router = (RouterEntity) updateElement;

            String update = "update" + nameTable +
                    "set `Name`=?, `SN`=?, `Port`=?, `In_Service`=?, `City_id`=? " +
                    "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());
            preparedStatement.setInt(6, router.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Update router failed");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        try {
            RouterEntity router = (RouterEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, router.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete router failed");
        }
    }
    public static RouterDAO getInstance() throws DAOException {
        RouterDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new RouterDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
