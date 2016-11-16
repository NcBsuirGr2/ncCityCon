package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.*;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Vojts on 09.11.2016.
 */

public class CityDAO extends MySQLDAO {

    /**
     * @throws InternalDAOException
     */
    private CityDAO() throws InternalDAOException {
        super();
        nameTable = " City ";
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
    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc)
                                            throws InvalidDataDAOException, InternalDAOException {
        PreparedStatement search_cities = null;
        ResultSet resultSet = null;

        ArrayList<CityEntity> cities = new ArrayList();
        String search = "select * from" + nameTable + "limit ?,?";

        try {
            search_cities = connection.prepareStatement(search);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in cityPage wasn't created", e);
        }

        try {
            search_cities.setInt(1, (page - 1) * itemsPerPage);
            search_cities.setInt(2, itemsPerPage);

            resultSet = search_cities.executeQuery();

            try {
                while (resultSet.next()){
                    CityEntity city = new CityEntity();
                    city.setId(resultSet.getInt("id"));
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                    cities.add(city);
                }
            }catch (SQLException e){
                throw new InvalidDataDAOException("GetPage city failed", e);
            }
        }catch (SQLException e) {
            throw new InvalidDataDAOException("Put data to prepare statement in city invalid",e);
        }
        finally {
            if (search_cities!=null){
                try {
                    search_cities.close();
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

        return cities.toArray(new CityEntity[cities.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException
     * @throws InternalDAOException
     * @throws InvalidDataDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        CityEntity city = null;

        String insert = "insert into" + nameTable +
                "(`Name`, `Country`)" +
                " values (?, ?)";

        PreparedStatement preparedStatement = null;

        try {
            city = (CityEntity) newElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(insert);
        } catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in create city wasn't created", e);
        }

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.executeUpdate();
        } catch (SQLException e){
            throw new DublicateKeyDAOException("Create city failed", e);
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
        CityEntity city = null;

        PreparedStatement search_city = null;
        ResultSet resultSet= null;
        String search = "select * from" + nameTable + "where id=?";

        try {
            city = (CityEntity)readElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        if (city.getId() != 0) {
            try{
                search_city = connection.prepareStatement(search);
            }catch (SQLException e) {
                throw new InternalDAOException("Prepare statement in read user wasn't created", e);
            }
            try {
                search_city.setInt(1, readElement.getId());

                resultSet = search_city.executeQuery();

                if(resultSet.first()) {
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                }
                else{
                    throw new InvalidDataDAOException("City not found");
                }
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Read city failed", e);
            }
            finally {
                if (search_city!=null){
                    try {
                        search_city.close();
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
            throw new InvalidDataDAOException("For reading city incorrectly chosen field, try id");
        }
    }

    /**
     * @param updateElement
     * @throws DublicateKeyDAOException
     * @throws InvalidDataDAOException
     * @throws InternalDAOException
     */
    public void update(Entity updateElement) throws DublicateKeyDAOException, InvalidDataDAOException, InternalDAOException {
        CityEntity city = null;

        PreparedStatement preparedStatement = null;

        String update = "update" + nameTable + "set `Name`=?, `Country`=? where `id`=?";

        try {
            city = (CityEntity) updateElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(update);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in update city wasn't created", e);
        }

        try {
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.setInt(3, city.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DublicateKeyDAOException("Update city failed", e);
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
        CityEntity city = null;

        PreparedStatement preparedStatement = null;

        String delete = "delete from" + nameTable + "where `id`=?";

        try {
            city = (CityEntity) deleteElement;
        }catch (ClassCastException e) {
            throw new InvalidDataDAOException("Enter parameters are invalid", e);
        }

        try {
            preparedStatement = connection.prepareStatement(delete);
        }catch (SQLException e) {
            throw new InternalDAOException("Prepare statement in delete city wasn't created", e);
        }

        try {
            preparedStatement.setInt(1, city.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new InvalidDataDAOException("Delete city failed", e);
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
    public static CityDAO getInstance() throws InternalDAOException {
        return new CityDAO();
    }
}