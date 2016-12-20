package com.citycon.model.systemunits.orm;

import com.citycon.dao.exceptions.DAOException;
import com.citycon.dao.interfaces.DAO;
import com.citycon.dao.interfaces.UsersOfGroup;
import com.citycon.model.systemunits.entities.UserEntity;

import javax.validation.Valid;
import java.util.Set;

/**
 * ORM wrapper for the <code>UserEntity</code>. This class must be used in servlets to
 * manipulate CRUD operations for the plain entity through the concrete DAO.
 * DAO object is obtained from <code>DAOAbstractFactory</code>, incapsulated in superclass 
 * <code>ORMEnitity</code>. DAO initialization is lazy, so feel free to instantiate ORMCity objects.
 *
 * @author Mike
 * @version 0.4
 * @see  UserEntity, ORMEntity
 */
public class ORMUser extends ORMEntity {
	DAO dao;

	@Valid
	UserEntity user = new UserEntity();

	//Get-set interface for incapsulated object
	public UserEntity getEntity() {
		return user;
	}

	public void setEntity(UserEntity user) {
		this.user = user;
	}

	//ORM interface for incapsulated object

	public void create() throws DAOException {
		if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.create(user);
	}
    public void read() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.read(user);
    }
    public void update() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
	   	dao.update(user);
    }
    public void delete() throws DAOException {
    	if (dao == null) {
			dao = daoFactory.getUserDAO();
		}
		dao.delete(user);
    }

	public static Set<String> getSortingParameters() throws DAOException {
		DAO staticDAO = daoFactory.getUserDAO();
		return staticDAO.getSortingParameters();
	}

    /**
	 * Get any page of users from DAO layer. 
	 *
	 * @param  page 			number of page to show
	 * @param  itemsPerPage		number of items to show on one page
	 * @param  sortBy 			field to sort by
	 * @param  asc 				sorting in asc order if true
	 * @return cityEntity[] 	the array of users on demanded page.
	 * @throws DAOException 	if any DAO error occurs
	 */
    public static UserEntity[] getPage(int page, int itemsPerPage, 
    							String sortBy, boolean asc) throws DAOException {

    	DAO staticDAO = daoFactory.getUserDAO();
        return (UserEntity[])staticDAO.getPage(page, itemsPerPage, sortBy, asc);
    }
    
    /**
     *	Retrieves total nuber of users from DAO layer.
     * 
     * @return int 				nuber of Users
     * @throws DAOException 	if any DAO internal error occur
     */
    public static int getCount() throws DAOException {
    	DAO staticDAO = daoFactory.getUserDAO();
        return staticDAO.count_element();
    }

	/**
	 * @param group
	 * @return
	 * @throws DAOException
	 */
	public static int getCount(String group) throws DAOException{
		DAO staticDAO = daoFactory.getUserDAO();
		UsersOfGroup usersOfGroup = (UsersOfGroup) staticDAO;
		return usersOfGroup.count_element(group);
	}
}