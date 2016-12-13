package com.citycon.statistic.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CityStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "City";

    @Autowired
    public CityStatisticRepository(JdbcOperations dao) {
        super(dao);
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    public Long getCountriesCount() {
        try {
            String query = "SELECT COUNT(DISTINCT Country) FROM "+TABLE_NAME;
            Long count = dao.queryForObject(query, Long.class);
            return count;
        } catch (DataAccessException e) {
            throw e;
        }
    }

    public List<Map<String, Object>> getDescCountryCities() {
        String query = "SELECT Country AS country, COUNT(Name) AS citiesCount FROM City " +
                "GROUP BY Country ORDER BY citiesCount DESC ";

        List<Map<String, Object>> result = dao.queryForList(query);
        return result;
    }

    public List<Map<String, Object>> getDescCountryRouters() {
        String query = "SELECT C.Country AS country, C.Name AS city, count(R.ID) AS routersCount FROM City C " +
                "JOIN Router R ON C.ID=R.City_id GROUP BY C.Country, C.Name ORDER BY routersCount DESC";

        List<Map<String, Object>> result = dao.queryForList(query);
        return result;
    }
}
