package com.citycon.statistic.repositories;

import com.citycon.model.systemunits.entities.CityEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Provides access to city statistics
 *
 * @author Mike
 * @version 1.2
 */
@Repository
public class CityStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "City";
    private Logger logger;
    private NamedParameterJdbcOperations namedDao;

    @Autowired
    public CityStatisticRepository(JdbcOperations dao, NamedParameterJdbcOperations namedDao) {
        super(dao);
        this.namedDao = namedDao;
        logger = LoggerFactory.getLogger("com.citycon.statistic.repositories.CityStatisticRepository");
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    /**
     *
     * @return total count of countries
     */
    public Long getCountriesCount() {
        try {
            String query = "SELECT COUNT(DISTINCT Country) FROM "+TABLE_NAME;
            Long count = dao.queryForObject(query, Long.class);
            return count;
        } catch (DataAccessException e) {
            logger.warn("Cannot count countries", e);
            throw e;
        }
    }

    /**
     *
     * @return list of contries in desc order
     */
    public List<Map<String, Object>> getDescCountryCities() {
        try {
            String query = "SELECT Country AS country, COUNT(Name) AS citiesCount FROM City " +
                    "GROUP BY Country ORDER BY citiesCount DESC ";

            List<Map<String, Object>> result = dao.queryForList(query);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get countries with cities count", e);
            throw e;
        }
    }

    /**
     *
     * @return list of countries in desc order of their routers
     */
    public List<Map<String, Object>> getDescCountryRouters() {
        try {
            String query = "SELECT C.Country AS country, C.Name AS city, count(R.ID) AS routersCount FROM City C " +
                    "JOIN Router R ON C.ID=R.City_id GROUP BY C.Country, C.Name ORDER BY routersCount DESC";

            List<Map<String, Object>> result = dao.queryForList(query);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get cities with routers count", e);
            throw e;
        }
    }

    /**
     * Counts routers in concrete city
     *
     * @param city      city to count routers
     * @return          Long count of routers
     */
    public Long countRouters(CityEntity city) {
        try {
            String query = "SELECT COUNT(*) FROM Router WHERE City_id = " +
                    "(SELECT ID FROM City WHERE City.Name = :cityName AND City.Country = :countryName)";
            Map<String, String> namedParameters = new HashMap<>();
            namedParameters.put("cityName", city.getName());
            namedParameters.put("countryName", city.getCountryName());
            Long routersCount = namedDao.queryForObject(query, namedParameters, Long.class);
            logger.debug("Routers count for city {}:{}", city, routersCount);
            return routersCount;

        } catch(DataAccessException e) {
            logger.warn("Cannot get routers of city {}", city, e);
            throw e;
        }
    }

    /**
     * Count connections in concrete city
     *
     * @param city      city to count connections
     * @return             Long count of connections
     */
    public Long countConnections(CityEntity city) {
        try {
            String query = "SELECT count(*) FROM RouterConnection LEFT JOIN Router ON " +
                    "(RouterConnection.ID_from = Router.ID OR RouterConnection.ID_to = Router.ID) " +
                    "WHERE Router.ID IN (SELECT Router.ID FROM Router " +
                    "WHERE City_id = (SELECT ID FROM City WHERE City.Name=:cityName AND City.Country=:countryName))";
            Map<String, String> namedParameters = new HashMap<>();
            namedParameters.put("cityName", city.getName());
            namedParameters.put("countryName", city.getCountryName());
            Long connectionsCount = namedDao.queryForObject(query, namedParameters, Long.class);
            return connectionsCount;

        } catch(DataAccessException e) {
            logger.warn("Cannot get connections of city {}", city, e);
            throw e;
        }
    }

    /**
     * Return list of cities, that are connected with concrete city. Also returns the count
     * of connections between cities.
     *
     * @param city      city to find connections
     * @param active    find only active or inactive connections
     * @return          list on connected cities
     */
    public List<Map<String, Object>> getConnectedCities(CityEntity city, boolean active) {
        try {

            String query = "SELECT c.Country, c.Name, c2.Country AS country,c2.Name AS city,COUNT(*) AS connectionsCount FROM (City c " +
                    "JOIN Router r ON r.City_id=c.ID) " +
                    "JOIN RouterConnection rc ON (rc.ID_From = r.ID OR rc.ID_To = r.ID) " +
                    "JOIN Router r2 ON (r.ID<>r2.ID and (r2.ID=rc.ID_From OR r2.ID=rc.ID_To)) " +
                    "JOIN City c2 ON c2.ID=r2.City_id";

            if (active) {
                query += " WHERE (r.In_Service=1 and r2.In_Service=1) " +
                        "GROUP BY c.Country,c.Name,c2.Country,c2.Name HAVING c.Country=:countryName and c.Name=:cityName";
            } else {
                query += " WHERE (r.In_Service=0 or r2.In_Service=0) " +
                        "GROUP BY c.Country,c.Name,c2.Country,c2.Name HAVING c.Country=:countryName and c.Name=:cityName";
            }
            Map<String, String> namedParameters = new HashMap<>();
            namedParameters.put("cityName", city.getName());
            namedParameters.put("countryName", city.getCountryName());
            List<Map<String, Object>> connectedCities = namedDao.queryForList(query, namedParameters);
            return connectedCities;

        } catch(DataAccessException e) {
            logger.warn("Cannot get connections of city {}", city ,e);
            throw e;
        }
    }
}