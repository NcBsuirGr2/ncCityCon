package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.RouterConnectionEntity;
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
public class RouterConnectionDAO extends MySQLDAO {
    private static volatile RouterConnectionDAO instance;

    private RouterConnectionDAO() throws DAOException {
        super();
        nameTable = " RouterConnection";
    }

    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_users = connection.prepareStatement(search);
            search_users.setInt((int)1, page*itemsPerPage);
            search_users.setInt((int)2, itemsPerPage);

            ResultSet resultSet =  search_users.executeQuery();

            while(resultSet.next()) {
                RouterConnectionEntity routerConnection = new RouterConnectionEntity();
                routerConnection.setId(resultSet.getInt("id"));
                routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));
                routerConnections.add(routerConnection);
            }
            resultSet.close();
            search_users.close();
        }catch (SQLException e){
            throw new DAOException("GetPage routerConnection failed\n" + e.toString());
        }
        return (RouterConnectionEntity[]) routerConnections.toArray();
    }

    public void create(Entity newElement) throws DAOException {
        try{
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) newElement;

            String insert = "insert into" + nameTable +
                    "`ID_From`, `ID_To`" +
                    " values (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (SQLException e){
            throw new DAOException("Create routerConnection failed");
        }
    }

    public Entity read(int id) throws DAOException {
        RouterConnectionEntity  routerConnection  = null;
        try {
            String search = "select * from" + nameTable + "when id=?";

            PreparedStatement search_routerConnection = connection.prepareStatement(search);
            search_routerConnection.setInt((int)1, id);

            ResultSet resultSet =  search_routerConnection.executeQuery();

            while(resultSet.next()) {
                routerConnection.setId(resultSet.getInt("id"));
                routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));
            }
            resultSet.close();
            search_routerConnection.close();
        }catch (SQLException e){
            throw new DAOException("Read routerConnection failed\n" + e.toString());
        }
        return routerConnection;
    }

    public void update(Entity updateElement) throws DAOException {
        try {
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) updateElement;

            String update = "update" + nameTable + "set `ID_From`=?, `ID_To`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setInt(1, routerConnection.getFirstRouterId());
            preparedStatement.setInt(2, routerConnection.getSecondRouterId());
            preparedStatement.setInt(3, routerConnection.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Update routerConnection failed");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        try {
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, routerConnection.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete routerConnection failed");
        }
    }
    public static RouterConnectionDAO getInstance() throws DAOException {
        RouterConnectionDAO localInstance = instance;
        if (localInstance == null) {
            synchronized (RouterConnectionDAO.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new RouterConnectionDAO();
                    } catch (DAOException e) {
                        throw new DAOException("Dummy");
                    }
                }
            }
        }
        return localInstance;
    }
}
