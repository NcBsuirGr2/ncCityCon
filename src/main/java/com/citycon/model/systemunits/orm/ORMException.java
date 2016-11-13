package com.citycon.model.systemunits.orm;


/**
 * Represents exceptions during ORM operations. 
 *
 * @author Mike
 * @version 0.1
 */
public class ORMException extends Exception {
	public ORMException(String message, Throwable cause) {
		super(message, cause);
	}
	public ORMException(String message) {
		super(message);
	}
	public ORMException(Throwable cause) {
		super(cause);
	}
	public ORMException() {
		super();
	}
}