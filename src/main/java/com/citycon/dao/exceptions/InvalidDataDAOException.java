package com.citycon.dao.exceptions;

/**
 * Is trown when data, that DAO layer retreaves, is invalid. It can be invalid
 * Date representation or the negative ID field.
 *
 * @author Mike
 * @version 1.0
 */
public class InvalidDataDAOException extends DAOException {
	public InvalidDataDAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public InvalidDataDAOException(String message) {
		super(message);
	}
	public InvalidDataDAOException(Throwable cause) {
		super(cause);
	}
	public InvalidDataDAOException() {
		super();
	}
}