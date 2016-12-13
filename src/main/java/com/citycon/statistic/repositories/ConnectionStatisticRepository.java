package com.citycon.statistic.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class ConnectionStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "RouterConnection";

    @Autowired
    public ConnectionStatisticRepository(JdbcOperations dao) {
        super(dao);
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    public Long getInacitveConnectionsCount() {
        String query = "SELECT COUNT(1) FROM RouterConnection WHERE ID_From IN (SELECT ID FROM Router WHERE In_Service = 0) " +
                "OR ID_To IN (SELECT ID FROM Router WHERE In_Service=0)";

        Long inactiveConnectionsCount = dao.queryForObject(query, Long.class);
        return inactiveConnectionsCount;
    }
}
