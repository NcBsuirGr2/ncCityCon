package com.citycon.statistic.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class RouterStatisticRepository extends AbstractRepository {
    private NamedParameterJdbcOperations namedParameterDao;
    private final String TABLE_NAME = "Router";

    @Autowired
    public RouterStatisticRepository(NamedParameterJdbcOperations namedParameterDao,
                                   JdbcOperations dao) {
        super(dao);
        this.namedParameterDao = namedParameterDao;
    }

    public int getCount() {
        return super.getCount(TABLE_NAME);
    }

    public int getCountriesCount() {
        String query = "SELECT COUNT(DISTINCT Country) FROM "+TABLE_NAME;

        int count = dao.queryForObject(query, Integer.class);
        return count;
    }

    public Long getCountActiveRouters() {
        String query = "SELECT COUNT(1) FROM Router WHERE In_Service=1";

        Long result = dao.queryForObject(query, Long.class);
        return result;
    }
    public Long getCountPorts() {
        String query = "SELECT SUM(Port) FROM Router";

        Long result = dao.queryForObject(query, Long.class);
        return result;
    }
}
