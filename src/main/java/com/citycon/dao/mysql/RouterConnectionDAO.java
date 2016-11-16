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
     * @throws DAOException
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
     * @throws InternalDAOException
     */
    public RouterConnectionEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws InternalDAOException {
        ArrayList<RouterConnectionEntity> routerConnections = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_users = connection.prepareStatement(search);
            search_users.setInt(1, (page-1)*itemsPerPage);
            search_users.setInt(2, itemsPerPage);

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
            throw new InternalDAOException("GetPage routerConnection failed", e);
        }
        return routerConnections.toArray(new RouterConnectionEntity[routerConnections.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException,InternalDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException,InternalDAOException {
        try{
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) newElement;

            String insert = "insert into" + nameTable +
                    "`ID_From`, `ID_To`" +
                    " values (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            try {
                preparedStatement.setInt(1, routerConnection.getFirstRouterId());
                preparedStatement.setInt(2, routerConnection.getSecondRouterId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e){
                throw new DublicateKeyDAOException("Create routerConnection failed", e);
            }

        }catch (SQLException e){
            throw new InternalDAOException("Create routerConnection failed", e);
        }
    }

    /**
     * @param readElement
     * @throws NotFoundDAOException, InternalDAOException, InvalidDataDAOException
     */
    public void read(Entity readElement) throws NotFoundDAOException, InternalDAOException, InvalidDataDAOException {
        RouterConnectionEntity  routerConnection  = (RouterConnectionEntity)readElement;
        if(routerConnection.getId() != 0) {
            try {
                String search = "select * from" + nameTable + "where id=?";

                PreparedStatement search_routerConnection = connection.prepareStatement(search);
                try {
                    search_routerConnection.setInt(1, readElement.getId());

                    ResultSet resultSet = search_routerConnection.executeQuery();

                    if(resultSet.first()) {
                        routerConnection.setId(resultSet.getInt("id"));
                        routerConnection.setFirstRouterId(resultSet.getInt("ID_From"));
                        routerConnection.setSecondRouterId(resultSet.getInt("ID_To"));
                    }
                    else {
                        resultSet.close();
                        search_routerConnection.close();
                        throw new NotFoundDAOException("This router connection does not exist");
                    }
                    resultSet.close();
                    search_routerConnection.close();
                }catch (SQLException e) {
                    throw new InvalidDataDAOException("Read router connection failed", e);
                }

            } catch (SQLException e) {
                throw new InternalDAOException("Read router connection failed", e);
            }
        }
        else{
            throw new InvalidDataDAOException("For reading router connection incorrectly chosen field, try id");
        }
    }

    /**
     * @param updateElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void update(Entity updateElement) throws InvalidDataDAOException, InternalDAOException {
        try {
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) updateElement;

            String update = "update" + nameTable + "set `ID_From`=?, `ID_To`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            try {
                preparedStatement.setInt(1, routerConnection.getFirstRouterId());
                preparedStatement.setInt(2, routerConnection.getSecondRouterId());
                preparedStatement.setInt(3, routerConnection.getId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Update routerConnection failed", e);
            }

        } catch (SQLException e) {
            throw new InternalDAOException("Update routerConnection failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException {
        try {
            RouterConnectionEntity routerConnection = (RouterConnectionEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            try {
                preparedStatement.setInt(1, routerConnection.getId());
                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Delete routerConnection failed", e);
            }


        } catch (SQLException e) {
            throw new InternalDAOException("Delete routerConnection failed", e);
        }
    }
    /**
     * @throws InternalDAOException
     */
    public static RouterConnectionDAO getInstance() throws InternalDAOException {
        return new RouterConnectionDAO();
    }
}
