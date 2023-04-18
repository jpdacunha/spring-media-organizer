package com.jpdacunha.media.batch.core.service.exception;

import java.io.IOException;

public class CursorException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3673352368797824380L;
	

	public CursorException() {
		super();
	}

	public CursorException(String msg) {
		super(msg);
	}

	public CursorException(IOException e) {
		super(e);
	}

}
