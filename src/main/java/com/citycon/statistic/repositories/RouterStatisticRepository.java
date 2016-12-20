package com.citycon.statistic.repositories;

import com.citycon.model.systemunits.entities.RouterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RouterStatisticRepository extends AbstractRepository {
    private final String TABLE_NAME = "Router";
    private Logger logger;
    private NamedParameterJdbcOperations namedDao;

    @Autowired
    public RouterStatisticRepository(JdbcOperations dao, NamedParameterJdbcOperations namedDao) {
        super(dao);
        this.namedDao = namedDao;
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
    public Long getConnectionsCount(RouterEntity router) {
        try {
            String query = "SELECT COUNT(1) FROM RouterConnection rc " +
                    "JOIN Router r on (rc.ID_From=r.ID or rc.ID_To=r.ID) WHERE r.SN=:SN";

            Map<String, String> paramMap = Collections.singletonMap("SN", router.getSN());
            Long result = namedDao.queryForObject(query, paramMap, Long.class);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get total count of connection for router {}", router, e);
            throw e;
        }
    }
    public Long getInactiveConnectionsCount(RouterEntity router) {
        try {
            String query = "SELECT COUNT(DISTINCT rc.ID) FROM RouterConnection rc " +
                    "JOIN (SELECT Router.ID FROM Router WHERE In_Service=0) r on (rc.ID_From=r.ID or rc.ID_To=r.ID) " +
                    "WHERE ((SELECT Router.ID FROM Router WHERE SN=:SN) IN (rc.ID_To,rc.ID_From))";
            Map<String, String> paramMap = Collections.singletonMap("SN", router.getSN());
            Long result = namedDao.queryForObject(query, paramMap, Long.class);
            return result;
        } catch(DataAccessException e) {
            logger.warn("Cannot get total count of connection for router {}", router, e);
            throw e;
        }
    }
    public List<Map<String, Object>> getConnectedRouters(RouterEntity router, boolean active) {
        try {
            String query = "SELECT r.SN, r2.SN as SN, count(*) AS connectionsCount FROM Router r " +
                    "JOIN RouterConnection rc ON ((rc.ID_From = r.ID OR rc.ID_To = r.ID)) " +
                    "JOIN Router r2 ON (r.ID<>r2.ID and (r2.ID=rc.ID_From OR r2.ID=rc.ID_To)) ";
            if (active) {
                query += "WHERE (r2.In_Service=1 and r.In_Service=1) GROUP BY r.SN, r2.SN HAVING r.SN=:SN";
            } else {
                query += "WHERE (r2.In_Service=0 or r.In_Service=0) GROUP BY r.SN, r2.SN HAVING r.SN=:SN";
            }
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("SN", router.getSN());
            List<Map<String, Object>> connectedCities = namedDao.queryForList(query, paramMap);
            return connectedCities;
        } catch(DataAccessException e) {
            logger.warn("Cannot get connections of router {}", router ,e);
            throw e;
        }
    }
    public boolean isActive(RouterEntity router) {
        try {
            String query = "SELECT Router.In_Service FROM Router where SN=:SN";

            Map<String, String> paramMap = Collections.singletonMap("SN", router.getSN());
            boolean active = namedDao.queryForObject(query, paramMap, Boolean.class);
            return active;
        } catch(DataAccessException e) {
            logger.warn("Cannot check if router is in service {}", router ,e);
            throw e;
        }
    }
    public String getName(RouterEntity router) {
        try {
            String query = "SELECT Router.Name FROM Router where SN=:SN";

            Map<String, String> paramMap = Collections.singletonMap("SN", router.getSN());
            String name = namedDao.queryForObject(query, paramMap, String.class);
            return name;
        } catch(DataAccessException e) {
            logger.warn("Cannot check if router is in service {}", router ,e);
            throw e;
        }
    }
}
