package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.DAOException;
import com.citycon.model.Grant;

/**
 * ORM wrapper for the <code>UserEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.4
 * @see  UserEntity
 */
public class ORMUser extends ORMEntity {
	DAO dao = daoFactory.getUserDAO();
	UserEntity user = new UserEntity();

	//Get-set interface for incapsulated object
	
	public int getId() {
		return user.getId();
	}
	public void setId(int id) {
		user.setId(id);
	}

	public String getName() {
		return user.getName();
	}
	public String getLogin() {
		return user.getLogin();
	}
	public String getPassword() {
		return user.getPassword();
	}
	public String getEmail() {
		return user.getEmail();
	}
	public Grant getGrant() {
		return user.getGrant();
	}

	public void setName(String name) {
		user.setName(name);
	}
	public void setLogin(String login) {
		user.setLogin(login);
	}
	public void setPassword(String password) {
		user.setPassword(password);
	}
	public void setEmail(String email) {
		user.setEmail(email);
	}
	public void setGrant(Grant grant) {
		user.setGrant(grant);
	}


	//ORM interface for incapsulated object

	public int create() throws ORMException {
		int userId = -1;
		try {
			userId = dao.create(user);
		} catch(DAOException cause) {
			throw new ORMException("Cannot create user", cause);
		}
		return userId;
	}
    public int read() throws ORMException {
    	int userId = -1;
    	try {
    		userId = dao.read(user);
    	} catch(DAOException cause) {
    		throw new ORMException("Cannot read user", cause);
    	}
    	return userId;
    }
    public int update() throws ORMException {
    	int userId = -1;
    	try {
 		   	userId = dao.update(user);
    	} catch(DAOException cause) {
    		throw new ORMException("Cannot update user", cause);
    	}
    	return userId;
    }
    public void delete() throws ORMException {
    	try {
    		dao.delete(user);
    	} catch(DAOException cause) {
    		throw new ORMException("Cannot delete user", cause);
    	}
    }

     /**
	 * Get any page of users from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return id the id of deleted element.
	 * @throws ORMException if error occurs during delete operation
	 */
    public static UserEntity[] getPage(int page, int itemsPerPage, String sortBy, boolean asc) throws ORMException {
    	DAO staticDAO = daoFactory.getUserDAO();
        UserEntity[] users = null;
        try {
        	Entity[] temp = staticDAO.getPage(page, itemsPerPage, sortBy, asc);
        	users = (UserEntity[])temp;
        } catch (DAOException cause) {
        	throw new ORMException("Cannot get users page", cause);
        } catch (ClassCastException exception) {
        	throw new ORMException("Cannot cast Entity[] from DAO to UserEntity[]", exception);
        }
        return users;
    }
}