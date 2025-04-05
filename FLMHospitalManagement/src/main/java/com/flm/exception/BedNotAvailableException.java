package com.flm.exception;

public class BedNotAvailableException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public BedNotAvailableException(String message) {
		super(message);
	}

}
