package com.citycon.dao.exceptions;

/**
 * Represents internal exception in DAO layer. Can be caused during geting connection
 * in the MySQL DAO or trying to open not-existing file in the XML DAO.
 *
 * @author Mike
 * @version 1.0
 */
public class InternalDAOException extends DAOException {
	public InternalDAOException(String message, Throwable cause) {
		super(message, cause);
	}
	public InternalDAOException(String message) {
		super(message);
	}
	public InternalDAOException(Throwable cause) {
		super(cause);
	}
	public InternalDAOException() {
		super();
	}
}