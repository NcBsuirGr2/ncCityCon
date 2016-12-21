package com.citycon.controllers;

import com.citycon.model.systemunits.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConfirmationHolder {
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

    public void add(String reglink, UserEntity user) {
        logger.debug("Added reglink {} for user {}", reglink, user);
        users.put(reglink, user);
    }
    public boolean contains(String reglink) {
        return users.containsKey(reglink);
    }
    public boolean contains(UserEntity user) {
        return users.containsValue(user);
    }
    public UserEntity pop(String reglink) {
        logger.debug("Popped reglink {}", reglink);
        return users.remove(reglink);
    }
}
