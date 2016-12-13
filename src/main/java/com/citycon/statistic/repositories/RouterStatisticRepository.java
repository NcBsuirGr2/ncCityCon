package com.citycon.statistic.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class RouterStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "Router";
    private Logger logger;

    @Autowired
    public RouterStatisticRepository(JdbcOperations dao) {
        super(dao);
        logger = LoggerFactory.getLogger("com.citycon.statistic.repositories.RouterStatisticRepository");
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    public Long getCountInActiveRouters() {
        try {
            String query = "SELECT COUNT(1) FROM Router WHERE In_Service=0";

            Long result = dao.queryForObject(query, Long.class);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get count of inactive routers", e);
            throw e;
        }
    }
    public Long getCountPorts() {
        try {
            String query = "SELECT SUM(Port) FROM Router";

            Long result = dao.queryForObject(query, Long.class);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get total count of ports", e);
            throw e;
        }
    }
}
