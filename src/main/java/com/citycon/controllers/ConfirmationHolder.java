package com.citycon.controllers;

import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Contains users, that have not confirm their email yet. Singleton.
 *
 * @author Mike
 * @version 1.0
 */
public class ConfirmationHolder implements Serializable {
    private Map<String, UserEntity> users;
    private static ConfirmationHolder instance;
    private static Logger logger;

    private ConfirmationHolder() {
        users = Collections.synchronizedMap(new HashMap<String, UserEntity>());
    }

    public static ConfirmationHolder getInstance() {
        if (instance == null) {
            instance = new ConfirmationHolder();
        }
        logger = LoggerFactory.getLogger("com.citycon.controllers.ConfirmationHolder");
        return instance;
    }

    /**
     * Add user and his associated link to holder.
     *
     * @param reglink   link associated with user
     * @param user      user that have not confirm email yet
     */
    public void add(String reglink, UserEntity user) {
        logger.debug("Added reglink {} for user {}", reglink, user);
        users.put(reglink, user);
    }

    /**
     * Checks if there is unregistered user by his confirmation link.
     *
     * @param   reglink     link to find user
     * @return  true        if there is such user
     */
    public boolean contains(String reglink) {
        return users.containsKey(reglink);
    }

    /**
     * Checks if there is unregistered user.
     *
     * @param   user     user itself
     * @return  true     if holder contains such user
     */
    public boolean contains(UserEntity user) {
        return users.containsValue(user);
    }

    /**
     * Allows to delete user from <colde>ConfirmationHolder</colde>. Returns null if
     * there is no such user.
     *
     * @param   reglink     link, associated with user
     * @return  user
     */
    public UserEntity pop(String reglink) {
        logger.debug("Popped reglink {}", reglink);
        return users.remove(reglink);
    }
}
