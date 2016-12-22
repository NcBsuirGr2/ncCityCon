package com.citycon.statistic.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

/**
 * Provides statistic about connections in CityCon system
 *
 * @author Mike, Alex
 * @version 2.0
 */
@Repository
public class ConnectionStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "RouterConnection";
    private Logger logger;

    @Autowired
    public ConnectionStatisticRepository(JdbcOperations dao) {
        super(dao);
        logger = LoggerFactory.getLogger("com.citycon.statistic.repositories.ConnectionStatisticRepository");
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    /**
     *
     * @return Long count of inactive connections
     */
    public Long getInacitveConnectionsCount() {
        try {
            String query = "SELECT COUNT(1) FROM RouterConnection WHERE ID_From IN (SELECT ID FROM Router WHERE In_Service = 0) " +
                    "OR ID_To IN (SELECT ID FROM Router WHERE In_Service=0)";

            Long inactiveConnectionsCount = dao.queryForObject(query, Long.class);
            return inactiveConnectionsCount;
        } catch(DataAccessException e) {
            logger.warn("Cannot get inactive connections count", e);
            throw e;
        }
    }
}
