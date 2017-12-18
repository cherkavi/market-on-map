package com.cherkashyn.vitalii.market.exception;

public class GeneralException extends Exception{

	private static final long serialVersionUID = 1L;

	public GeneralException() {
		super();
	}

	public GeneralException(String message, Throwable cause) {
		super(message, cause);
	}

	public GeneralException(String message) {
		super(message);
	}

	public GeneralException(Throwable cause) {
		super(cause);
	}

	
}
