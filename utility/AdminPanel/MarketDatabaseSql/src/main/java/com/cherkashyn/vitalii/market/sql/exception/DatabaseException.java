package com.cherkashyn.vitalii.market.sql.exception;

import com.cherkashyn.vitalii.market.exception.StoreException;

/**
 * subtype of {@link StoreException} - {@link SQLException} wrapper
 */
public class DatabaseException extends StoreException{

	private static final long serialVersionUID = 1L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public DatabaseException(String message) {
		super(message);
	}

	public DatabaseException(Throwable cause) {
		super(cause);
	}
	
	

}
