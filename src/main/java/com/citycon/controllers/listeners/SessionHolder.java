package com.citycon.controllers.listeners;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.model.systemunits.orm.ORMUser;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Aimed to hold control over all app sessions. Is used to update user immediately
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

    /**
     * Allows to get existing user session by user login. Aimed to unite all user interaction.
     *
     * @param userLogin         String representing user login
     * @return HttpSession      if there is one and <code>null</code> otherwise
     */
    public static HttpSession getUserSession(String userLogin) {
        for (HttpSession session : sessions) {
            if (session.getAttribute("user") != null) {
                try {

                    UserEntity user = (UserEntity) session.getAttribute("user");
                    if (user.getLogin().equals(userLogin)) {
                        return session;
                    }
                } catch (Exception e) {
                    logger.warn("Cannot get session by user login: unexpected exception", e);
                }
            }
        }
        return null;
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

    /**
     * Aimed to update user session immediately after editing.
     *
     * @param userToUpdate     used to get user by id
     */
    public static void updateUser(UserEntity userToUpdate) {
        Iterator<HttpSession> i = sessions.iterator();
        try {
            while(i.hasNext()) {
                HttpSession currentSession = i.next();
                if (currentSession.getAttribute("user") != null) {
                    UserEntity user = (UserEntity)currentSession.getAttribute("user");

                    if(user.getId() == userToUpdate.getId()) {
                        ORMUser updateWrapper = new ORMUser();
                        updateWrapper.setEntity(user);
                        try {
                            updateWrapper.read();
                            logger.trace("Update session of {}", user.getLogin());
                        } catch (DAOException e) {
                            logger.error("Fault during updating session for user {}", user.getLogin());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected exception during updating user session", e);
        }
    }
    /**
     * Aimed to delte user session immediately after deleting.
     *
     * @param userToDelete      used to select user by id
     */
    public static boolean deleteUser(UserEntity userToDelete) {
        Iterator<HttpSession> i = sessions.iterator();
        try {
            while(i.hasNext()) {
                HttpSession currentSession = i.next();
                if (currentSession.getAttribute("user") != null) {
                    UserEntity user = (UserEntity)currentSession.getAttribute("user");
                    if(user.getId() == userToDelete.getId()) {
                        currentSession.invalidate();
                        logger.trace("Delete session for user with id {}", user.getId());
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected exception during deleting user session", e);
        }
        return false;
    }

    /**
     * Allows to get all users, that are online
     *
     * @return usersOnline      online users
     */
    public static List<String> getUsersOnline() {
        List<String> usersOnline = new LinkedList<>();
        Iterator<HttpSession> sessionsIterator = sessions.iterator();
        while(sessionsIterator.hasNext()) {
            HttpSession nextSession = sessionsIterator.next();
            if (nextSession.getAttribute("user") != null) {
                try {
                    UserEntity user = (UserEntity)nextSession.getAttribute("user");
                    usersOnline.add(user.getLogin());
                } catch (ClassCastException e) {
                    logger.warn("Cannot cast session \"user\" object to UserEntity", e);
                }
            }
        }
        return usersOnline;
    }
}