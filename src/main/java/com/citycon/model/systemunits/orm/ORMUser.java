package com.citycon.model.systemunits.orm;

import com.citycon.model.systemunits.entities.Entity;
import com.citycon.model.systemunits.entities.UserEntity;
import com.citycon.dao.DAO;
import com.citycon.dao.exceptions.DAOException;
import com.citycon.model.Grant;

import java.sql.Date;

/**
 * ORM wrapper for the <code>UserEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 *
 * @author Mike
 * @version 0.4
 * @see  UserEntity
 */
public class ORMUser extends ORMEntity {
	DAO dao;
	UserEntity user = new UserEntity();

	public ORMUser() throws DAOException {
		dao = daoFactory.getUserDAO();
	}

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
	public String getGroup() {
		return user.getGroup();
	}
	public Date getCreateDate() {
		return user.getCreateDate();
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
	public void setGroup(String group) {
		user.setGroup(group);
	}
	public void setCreateDate(Date createDate) {
		user.setCreateDate(createDate);
	}

	//ORM interface for incapsulated object

	public void create() throws DAOException {
			dao.create(user);
	}
    public void read() throws DAOException {
    		dao.read(user);
    }
    public void update() throws DAOException {
 		   	dao.update(user);
    }
    public void delete() throws DAOException {
    		dao.delete(user);
    }

    public UserEntity getEntity()  {
		return user;
	}

    /**
	 * Get any page of users from DAO layer. 
	 *
	 * @param  page number of page to show
	 * @param  itemsPerPage number of items to show on one page
	 * @param  sortBy field to sort by
	 * @param  asc sorting in asc order if true
	 * @return cityEntity[] the array of users on demanded page.
	 */
    public static UserEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {
    	DAO staticDAO = daoFactory.getUserDAO();
        return (UserEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
}