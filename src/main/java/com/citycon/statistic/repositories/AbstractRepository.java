package com.citycon.statistic.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRepository {
    protected JdbcOperations dao;

    public AbstractRepository(JdbcOperations dao) {
        this.dao = dao;
    }

    protected int getCount(String tableName) {
        String query = "SELECT COUNT(*) FROM "+tableName;

        int count = dao.queryForObject(query, Integer.class);
        return count;
    }
}
