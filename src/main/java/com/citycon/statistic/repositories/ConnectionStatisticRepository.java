package com.citycon.statistic.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionStatisticRepository extends AbstractRepository {
    private NamedParameterJdbcOperations namedParameterDao;
    private final String TABLE_NAME = "RouterConnection";

    @Autowired
    public ConnectionStatisticRepository(NamedParameterJdbcOperations namedParameterDao,
                                   JdbcOperations dao) {
        super(dao);
        this.namedParameterDao = namedParameterDao;
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }
}
