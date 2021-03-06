package com.citycon.statistic.repositories;

import com.citycon.clientService.ClientService;
import com.citycon.clientService.UserModel;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides access to merge users statistics.
 *
 * @author Alex
 * @version 2.0
 */
@Repository
public class UserMergeRepository extends AbstractRepository {
    private NamedParameterJdbcOperations namedParameterDao;
    private Logger logger;

    @Autowired
    public UserMergeRepository(JdbcOperations dao) {
        super(dao);
        this.namedParameterDao = namedParameterDao;
        logger = LoggerFactory.getLogger("com.citycon.statistic.repositories.UserMergeRepository");
    }

    /**
     * Merge users from OrderExpress and return information for statistic of merge
     *
     * @return          Map of information for statistic of merge
     */
    public Map<String, Integer> merge(){
        Map<String, Integer> statistic = new HashMap<>();
        UserModel[] users_from_service = ClientService.getServiceUsers();
        List<UserModel> valid_users = null;

        if(users_from_service == null){
            statistic.put("count_users_from_service", 0);
            statistic.put("count_valid_users_from_service", 0);
        }
        else {
            statistic.put("count_users_from_service", users_from_service.length);

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            valid_users = new ArrayList<>();

            for (UserModel userModel : users_from_service) {
                if (validator.validate(userModel).size() == 0) {
                    valid_users.add(userModel);
                }
            }

            statistic.put("count_valid_users_from_service", valid_users.size());
        }

        String query = "SELECT Login, Pass, `E-mail` FROM User";
        List<UserModel> users_from_DB = dao.query(query, new ResultSetToUserMapper());

        if (users_from_DB == null){
            statistic.put("count_users_from_database", 0);
        }
        else {
            statistic.put("count_users_from_database", users_from_DB.size());

            if (valid_users != null) {
                for (UserModel user : users_from_DB) {
                    if (valid_users.contains(user)) {
                        valid_users.remove(user);
                    }
                }
            }
        }

        int count_merged = 0;

        if(valid_users != null) {
            for (UserModel userModel : valid_users) {
                try {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setLogin(userModel.getUsername());
                    userEntity.setPassword(userModel.getPassword());
                    userEntity.setEmail(userModel.getEmail());
                    userEntity.setName("StorkExpress");
                    userEntity.setGroup("guest");
                    ORMUser ormUser = new ORMUser();
                    ormUser.setEntity(userEntity);
                    ormUser.create();
                    count_merged++;
                } catch (DAOException e) {
                    logger.warn("DAO exception during updating users from remote service",e);
                }
            }
        }

        statistic.put("count_merged_users_from_service", count_merged);

        return statistic;
    }

    private class ResultSetToUserMapper implements RowMapper<UserModel> {
        @Override
        public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserModel user = new UserModel();
            user.setUsername(rs.getString("Login"));
            user.setEmail(rs.getString("E-mail"));
            user.setPassword(rs.getString("Pass"));
            return user;
        }
    }
}
