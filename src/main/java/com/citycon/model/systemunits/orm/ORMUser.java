package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.dao.DAO;
import com.citycon.model.Grant;

/**
 * ORM wrapper for the <code>UserEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 1.2
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

	public int create() {
		return dao.create(user);
	}
    public int read() {
    	return dao.read(user);
    }
    public int update() {
    	return dao.update(user);
    }
    public void delete() {
    	dao.delete(user);
    }
}