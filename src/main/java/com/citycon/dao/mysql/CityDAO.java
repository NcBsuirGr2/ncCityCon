package com.citycon.dao.mysql;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.CityEntity;
import com.citycon.model.systemunits.entities.Entity;

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
    private CityDAO() throws DAOException {
        super();
        nameTable = " City ";
    }

    /**
     * @param page
     * @param itemsPerPage
     * @param sortBy
     * @param asc
     * @return
     * @throws DAOException
     */
    public CityEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws DAOException {
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
            throw new DAOException("GetPage city failed", e);
        }
        return cities.toArray(new CityEntity[cities.size()]);
    }

    /**
     * @param newElement
     * @throws DAOException
     */
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
            throw new DAOException("Create city failed", e);
        }
    }

    /**
     * @param readElement
     * @throws DAOException
     */
    public void read(Entity readElement) throws DAOException {
        CityEntity city = (CityEntity)readElement;
        if (city.getId() != 0) {
            try {
                String search = "select * from" + nameTable + "where id=?";

                PreparedStatement search_city = connection.prepareStatement(search);
                search_city.setInt(1, readElement.getId());

                ResultSet resultSet = search_city.executeQuery();

                if(resultSet.first()) {
                    city.setName(resultSet.getString("Name"));
                    city.setCountryName(resultSet.getString("Country"));
                }
                else{
                    resultSet.close();
                    search_city.close();
                    throw new DAOException("This city does not exist");
                }
                resultSet.close();
                search_city.close();
            } catch (SQLException e) {
                throw new DAOException("Read city failed", e);
            }
        }
        else{
            throw new DAOException("For reading city incorrectly chosen field, try id");
        }
    }

    /**
     * @param updateElement
     * @throws DAOException
     */
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
            throw new DAOException("Update city failed", e);
        }
    }

    /**
     * @param deleteElement
     * @throws DAOException
     */
    public void delete(Entity deleteElement) throws DAOException {
        try {
            CityEntity city = (CityEntity) deleteElement;

            String delete = "delete from" + nameTable + "where `id`=?";

            PreparedStatement preparedStatement = connection.prepareStatement(delete);

            preparedStatement.setInt(1, city.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new DAOException("Delete city failed", e);
        }
    }
    public static CityDAO getInstance() throws DAOException {
        return new CityDAO();
    }
}
