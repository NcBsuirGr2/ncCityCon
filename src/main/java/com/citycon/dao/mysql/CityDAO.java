package com.citycon.dao.mysql;

import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.CityEntity;
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

public class CityDAO extends MySQLDAO {
    private static volatile CityDAO instance;

    private CityDAO() throws DAOException {
        super();
        nameTable = " City ";
    }

    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
        ArrayList<CityEntity> cities = new ArrayList();
        try {
            String search = "select * from" + nameTable + "limit ?,?";

            PreparedStatement search_cities = connection.prepareStatement(search);
            search_cities.setInt((int)1, page*itemsPerPage);
            search_cities.setInt((int)2, itemsPerPage);

            ResultSet resultSet =  search_cities.executeQuery();

            while(resultSet.next()) {
                CityEntity city = new CityEntity();
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("Name"));
                city.setCountryName(resultSet.getString("Country"));
                cities.add(city);
            }
            resultSet.close();
            search_cities.close();
        }catch (SQLException e){
            throw new DAOException("GetPage city failed\n" + e.toString());
        }
        return (CityEntity[]) cities.toArray();
    }

    public void create(Entity newElement) throws DAOException {
        try{
            CityEntity city = (CityEntity) newElement;

            String insert = "insert into" + nameTable +
                    "(`Name`, `Country`)" +
                    " values (?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        }catch (SQLException e){
            throw new DAOException("Create city failed");
        }
    }

    public void read(Entity readElement) throws DAOException {
        CityEntity city = (CityEntity)readElement;
        try {
            String search = "select * from" + nameTable + "when id=?";

            PreparedStatement search_city = connection.prepareStatement(search);
            search_city.setInt((int)1, readElement.getId());

            ResultSet resultSet =  search_city.executeQuery();

            while(resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                city.setName(resultSet.getString("Name"));
                city.setCountryName(resultSet.getString("Country"));
            }
            resultSet.close();
            search_city.close();
        }catch (SQLException e){
            throw new DAOException("Read city failed\n" + e.toString());
        }
    }

    public void update(Entity updateElement) throws DAOException {
        try {
            CityEntity city = (CityEntity) updateElement;

            String update = "update" + nameTable + "set `Name`=?, `Country`=? where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, city.getName());
            preparedStatement.setString(2, city.getCountryName());;
            preparedStatement.setInt(3, city.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Update city failed");
        }
    }

    public void delete(Entity deleteElement) throws DAOException {
        try {
            CityEntity city = (CityEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, city.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete city failed");
        }
    }
    public static CityDAO getInstance() throws DAOException {
        return new CityDAO();
    }
    
//    public static CityDAO getInstance() throws DAOException {
//        CityDAO localInstance = instance;
//        if (localInstance == null) {
//            synchronized (CityDAO.class) {
//                localInstance = instance;
//                if (localInstance == null) {
//                    try {
//                        instance = localInstance = new CityDAO();
//                    } catch (DAOException e) {
//                        throw new DAOException("Dummy");
//                    }
//                }
//            }
//        }
//        return localInstance;
//    }
}
