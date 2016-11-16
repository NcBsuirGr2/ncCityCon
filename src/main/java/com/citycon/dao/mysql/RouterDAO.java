package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DublicateKeyDAOException;
import com.citycon.dao.exceptions.InternalDAOException;
import com.citycon.dao.exceptions.InvalidDataDAOException;
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
        return this.getPage(page, itemsPerPage, sortBy, asc, 0);
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @param cityId
     * @return
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public RouterEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc, int cityId)
                                                throws InvalidDataDAOException, InternalDAOException {

        PreparedStatement search_routers = null;
        ResultSet resultSet = null;

        ArrayList<RouterEntity> routers = new ArrayList();
        String search = "";

        if (cityId != 0){
            search = "select * from" + nameTable + "limit ?,? where City_id=" + String.valueOf(cityId);
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
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        RouterEntity router= null;

        PreparedStatement preparedStatement = null;

        String insert = "insert into" + nameTable +
                "(`Name`, `SN`, `Port`, `In_Service`, `City_id`)" +
                " values (?, ?, ?, ?, ?)";

        try{
            router = (RouterEntity) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in create router wasn't created", e);
        }

        try {
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException("Create router failed", e);
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
    public void read(Entity readElement)throws InternalDAOException, InvalidDataDAOException {
        RouterEntity router = null;

        PreparedStatement search_router = null;
        ResultSet resultSet = null;

        try {
            router = (RouterEntity) readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        String field = "";
        String value = "";

        if(router.getId() != 0) {
            field = "id";
            value = String.valueOf(router.getId());
        }
        else if(router.getSN() != null){
            field = "SN";
            value = "'" + router.getSN() + "'";
        }
        else{
            throw new InvalidDataDAOException("For reading router incorrectly chosen field, try id or SN");
        }

        String search = "select * from" + nameTable + "where " + field + "=" + value;

        try {
            search_router = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in read router wasn't created", e);
        }

        try {
            resultSet = search_router.executeQuery();

            if(resultSet.first()) {
                router.setId(resultSet.getInt("id"));
                router.setName(resultSet.getString("Name"));
                router.setSN(resultSet.getString("SN"));
                router.setPortsNum(resultSet.getInt("Port"));
                router.isActive(resultSet.getBoolean("In_Service"));
                router.setCityId(resultSet.getInt("City_id"));
            }
            else {
                throw new InvalidDataDAOException("Router not found");
            }
        }catch (SQLException e){
            throw new InternalDAOException("Read router failed", e);
        }
        finally {
            if (search_router!=null){
                try {
                    search_router.close();
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

    /**
     * @param updateElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        RouterEntity router = null;

        String update = "update" + nameTable +
                "set `Name`=?, `SN`=?, `Port`=?, `In_Service`=?, `City_id`=? " +
                "where `id`=?";

        PreparedStatement preparedStatement = null;

        try {
            router = (RouterEntity) updateElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in update router wasn't created", e);
        }

        try {
            preparedStatement.setString(1, router.getName());
            preparedStatement.setString(2, router.getSN());
            preparedStatement.setInt(3, router.getPortsNum());
            preparedStatement.setBoolean(4, router.isActive());
            preparedStatement.setInt(5, router.getCityId());
            preparedStatement.setInt(6, router.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException("Update router failed", e);
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
     * @param deleteElement
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException {
        RouterEntity router = null;

        PreparedStatement preparedStatement = null;

        String delete = "delete from" + nameTable + "where `id`=?";

        try {
            router = (RouterEntity) deleteElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }


        try {
            preparedStatement = connection.prepareStatement(delete);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in delete router wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, router.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidDataDAOException("Delete router failed", e);
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
    public static RouterDAO getInstance() throws InternalDAOException {
        return new RouterDAO();
    }
}
