package com.citycon.dao.mysql;

import com.citycon.dao.DAOException;
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
    private RouterDAO() throws DAOException {
        super();
        nameTable = " Router ";
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @return
     * @throws DAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
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
            throw new DAOException("GetPage router failed", e);
        }
        return routers.toArray(new RouterEntity[routers.size()]);
    }

    /**
     * @param newElement
     * @throws DAOException
     */
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
            throw new DAOException("Create router failed", e);
        }
    }

    /**
     * @param readElement
     * @throws DAOException
     */
    public void read(Entity readElement) throws DAOException {
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
            else {
                throw new DAOException("For reading router incorrectly chosen field, try id or SN");
            }

            String search = "select * from" + nameTable + "where " + field + "=" + value;
            PreparedStatement search_router = connection.prepareStatement(search);

            ResultSet resultSet = search_router.executeQuery();

            if(resultSet.first()) {
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.setIsActive(resultSet.getBoolean("In_Service"));
                router.setCityId(resultSet.getInt("City_id"));
            }
            else {
                resultSet.close();
                search_router.close();
                throw new DAOException("This router does not exist");
            }
                resultSet.close();
                search_router.close();
        }catch (SQLException e){
            throw new DAOException("Read router failed", e);
        }
    }

    /**
     * @param updateElement
     * @throws DAOException
     */
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
            throw new DAOException("Update router failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws DAOException
     */
    public void delete(Entity deleteElement) throws DAOException {
        try {
            RouterEntity router = (RouterEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, router.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete router failed", e);
        }
    }
    public static RouterDAO getInstance() throws DAOException {
        return new RouterDAO();
    }
}
