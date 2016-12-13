package com.citycon.statistic.repositories;

import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserStatisticRepository extends  AbstractRepository {
    private final String TABLE_NAME = "User";
    private NamedParameterJdbcOperations namedParameterDao;

    @Autowired
    public UserStatisticRepository(NamedParameterJdbcOperations namedParameterDao, JdbcOperations dao) {
        super(dao);
        this.namedParameterDao = namedParameterDao;
    }

    public Long getCount() {
        return super.getCount(TABLE_NAME);
    }

    public Long getCount(String group) {
        String query = "SELECT COUNT(*) FROM User WHERE User.Group=:group";

        Map<String,String> queryParams = Collections.singletonMap("group", group);

        Long count = namedParameterDao.queryForObject(query, queryParams, Long.class);
        return count;
    }
    public UserEntity[] getFirstUsers() {
        String query = "SELECT Name, Login, `E-mail`, create_date, User.Group from User WHERE " +
                "create_date = (SELECT MIN(create_date) FROM User)";

        List<UserEntity> firstUsersList = dao.query(query, new ResultSetToUserMapper());
        UserEntity[] firstUsersArray = new UserEntity[firstUsersList.size()];
        return firstUsersList.toArray(firstUsersArray);
    }

    public UserEntity[] getLastUsers() {
        String query = "SELECT Name, Login, `E-mail`, create_date, User.Group from User WHERE " +
                "create_date = (SELECT MAX(create_date) FROM User)";

        List<UserEntity> lastUsersList = dao.query(query, new ResultSetToUserMapper());
        UserEntity[] lastUsersArray = new UserEntity[lastUsersList.size()];
        return lastUsersList.toArray(lastUsersArray);
    }


    private class ResultSetToUserMapper implements RowMapper<UserEntity> {
        @Override
        public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserEntity user = new UserEntity();
            user.setName(rs.getString("Name"));
            user.setLogin(rs.getString("Login"));
            user.setEmail(rs.getString("E-mail"));
            user.setCreateDate(rs.getDate("create_date"));
            user.setGroup(rs.getString("Group"));
            return user;
        }
    }
}
