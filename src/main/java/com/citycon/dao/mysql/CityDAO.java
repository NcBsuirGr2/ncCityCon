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
     * @throws DAOException
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
     * @throws InternalDAOException
     */
    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) 
                                            throws InvalidDataDAOException, InternalDAOException {

        if(false ) {
            throw new InvalidDataDAOException();
        }
        ArrayList<CityEntity> cities = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_cities = connection.prepareStatement(search);
            search_cities.setInt(1, (page-1)*itemsPerPage);
            search_cities.setInt(2, itemsPerPage);

            ResultSet resultSet =  search_cities.executeQuery();

            while (resultSet.next()){
                CityEntity city = new CityEntity();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("Name"));
                city.setCountryName(resultSet.getString("Country"));
                cities.add(city);
            }
            resultSet.close();
            search_cities.close();
        }catch (SQLException e){
            throw new InternalDAOException("GetPage city failed", e);
        }
        return cities.toArray(new CityEntity[cities.size()]);
    }

    /**
     * @param newElement
     * @throws DublicateKeyDAOException,InternalDAOException
     */
    public void create(Entity newElement) throws DublicateKeyDAOException, InternalDAOException, InvalidDataDAOException {
        if(false) {
            throw new InvalidDataDAOException();
        }
        try {
            CityEntity city = (CityEntity) newElement;

            String insert = "insert into" + nameTable +
                    "(`Name`, `Country`)" +
                    " values (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);

            try{
                preparedStatement.setString(1, city.getName());
                preparedStatement.setString(2, city.getCountryName());

                preparedStatement.executeUpdate();
                preparedStatement.close();
            }catch (SQLException e){
                throw new DublicateKeyDAOException("Create city failed", e);
            }
        } catch (SQLException e) {
            throw new InternalDAOException("Create city failed", e);
        }
    }

    /**
     * @param readElement
     * @throws  InternalDAOException, InvalidDataDAOException
     */
    public void read(Entity readElement) throws InternalDAOException, InvalidDataDAOException {

        if(false) {
            throw new InvalidDataDAOException();
        }

        CityEntity city = (CityEntity)readElement;

        if (city.getId() != 0) {
            try {
                String search = "select * from" + nameTable + "where id=?";

                PreparedStatement search_city = connection.prepareStatement(search);
                try {
                    search_city.setInt(1, readElement.getId());

                    ResultSet resultSet = search_city.executeQuery();

                    if(resultSet.first()) {
                        city.setName(resultSet.getString("Name"));
                        city.setCountryName(resultSet.getString("Country"));
                    }
                    resultSet.close();
                    search_city.close();
                } catch (SQLException e) {
                    throw new InvalidDataDAOException("Read city failed", e);
                }
            } catch (SQLException e) {
                throw new InternalDAOException("Read city failed", e);
            }
        }
        else{
            throw new InvalidDataDAOException("For reading city incorrectly chosen field, try id");
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
            CityEntity city = (CityEntity) updateElement;

            String update = "update" + nameTable + "set `Name`=?, `Country`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            try {
                preparedStatement.setString(1, city.getName());
                preparedStatement.setString(2, city.getCountryName());
                
                preparedStatement.setInt(3, city.getId());

                preparedStatement.executeUpdate();

                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Update city failed", e);
            }
        } catch (SQLException e) {
            throw new InternalDAOException("Update city failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws InvalidDataDAOException, InternalDAOException
     */
    public void delete(Entity deleteElement) throws InvalidDataDAOException, InternalDAOException {
        try {
            CityEntity city = (CityEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);
            try {
                preparedStatement.setInt(1, city.getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new InvalidDataDAOException("Delete city failed", e);
            }
        } catch (SQLException e) {
            throw new InternalDAOException("Delete city failed", e);
        }
    }
    /**
     * @throws InternalDAOException
     */
    public static CityDAO getInstance() throws InternalDAOException {
        return new CityDAO();
    }
}
