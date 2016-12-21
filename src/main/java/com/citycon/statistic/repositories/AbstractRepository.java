package com.citycon.statistic.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;

/**
 * Provides common functionality for statistic repositories.
 *
 * @author Mike, Alex
 * @version 1.2
 */
public abstract class AbstractRepository {
    protected JdbcOperations dao;
    private Logger logger;

    public AbstractRepository(JdbcOperations dao) {
        this.dao = dao;
        logger = LoggerFactory.getLogger("com.citycon.statistic.repositories.AbstractRepository");
    }

    /**
     * Returns count of elements in concrete table.
     *
     * @param tableName     table name
     * @return count        Long count of elements
     */
    protected Long getCount(String tableName) {
        return getCount(tableName, 0);
    }

    protected Long getCount(String tableName, int id){
        try {
            String query = "SELECT COUNT(1) FROM "+tableName;

            if(id > 0){
                query += " WHERE id= " + id;
            }

            Long count = dao.queryForObject(query, Long.class);
            return count;
        } catch(DataAccessException e) {
            logger.warn("Cannot get count of {}", tableName, e);
            throw e;
        }
    }
}
