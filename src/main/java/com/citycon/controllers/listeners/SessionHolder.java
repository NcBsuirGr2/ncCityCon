package com.citycon.controllers.listeners;

import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Aimed to take control over all app sessions. Is used to update user immediately
 *  after editing.
 * 
 * @author Mike
 * @version 1.0
 */
public class SessionHolder implements HttpSessionListener {
    private static Set<HttpSession> sessions;
    private static Logger logger;

    public SessionHolder() {
        logger = LoggerFactory.getLogger("com.citycon.controllers.listeners.SessionHolder");
        sessions = Collections.synchronizedSet(new HashSet<HttpSession>());
    }
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession newSession = event.getSession();
        if(newSession.getAttribute("user") != null) {
            logger.trace("Created new session for user {}", ((UserEntity)newSession.getAttribute("user")).getLogin());
        } else {
            logger.trace("Created new session for null user");
        }        
        sessions.add(newSession);
        return;
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession deleteSession = event.getSession();
        if(deleteSession.getAttribute("user") != null) {
            logger.trace("Destroyed session for user {}", ((UserEntity)deleteSession.getAttribute("user")).getLogin());
        } else {
            logger.trace("Destroyed session for null user");
        }
        sessions.remove(deleteSession);
        return;
    }

    public static void updateUser(UserEntity userToUpdate) {
        Iterator<HttpSession> i = sessions.iterator();
        logger.debug("Updating user sessions");
        try {
            while(i.hasNext()) {
                HttpSession currentSession = i.next();
                if (currentSession.getAttribute("user") != null) {
                    UserEntity user = (UserEntity)currentSession.getAttribute("user");

                    logger.debug("Check update for {}", user.getLogin());
                    if(user.getId() == userToUpdate.getId()) {
                        ORMUser updateWrapper = new ORMUser();
                        updateWrapper.setEntity(user);
                        updateWrapper.read();
                        logger.debug("Updated session of {}", user.getLogin());
                        return;
                    }
                }
            }
        //NullPointer and ClassCast can be thrown
        } catch (Exception e) {
            logger.warn("Exception during updating user", e);
        }
    }
    public static void deleteUser(UserEntity userToDelete) {
        Iterator<HttpSession> i = sessions.iterator();
        try {
            while(i.hasNext()) {
                HttpSession currentSession = i.next();
                if (currentSession.getAttribute("user") != null) {
                    UserEntity user = (UserEntity)currentSession.getAttribute("user");
                    if(user.getId() == userToDelete.getId()) {
                        boolean success = sessions.remove(currentSession);
                        currentSession.invalidate();
                        return;
                    }
                }
            }
        //NullPointer and ClassCast can be thrown
        } catch (Exception e) {
            logger.warn("Exception during updating user", e);
        }
    }
}