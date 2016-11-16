package com.citycon.dao.exceptions;

/**
 * Is throw if DAO layer can not find element by request. 
 *
 * @author Mike
 * @version 1.0
 */
public class NotFoundDAOException extends DAOException {
	public NotFoundDAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public NotFoundDAOException(String message) {
		super(message);
	}
	public NotFoundDAOException(Throwable cause) {
		super(cause);
	}
	public NotFoundDAOException() {
		super();
	}
}