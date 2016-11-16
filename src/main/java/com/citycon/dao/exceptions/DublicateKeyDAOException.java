package com.citycon.dao.exceptions;

/**
 * Is thrown when trying to add new element that already exists.
 *
 * @author Mike
 * @version 1.0
 */
public class DublicateKeyDAOException extends DAOException {
	public DublicateKeyDAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public DublicateKeyDAOException(String message) {
		super(message);
	}
	public DublicateKeyDAOException(Throwable cause) {
		super(cause);
	}
	public DublicateKeyDAOException() {
		super();
	}
}