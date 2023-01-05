package com.jpdacunha.media.batch.removeduplicatesfotos.exception;

import java.io.IOException;

public class RemoveDuplicateImageshException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673352368797824380L;
	

	public RemoveDuplicateImageshException() {
		super();
	}

	public RemoveDuplicateImageshException(String msg) {
		super(msg);
	}

	public RemoveDuplicateImageshException(IOException e) {
		super(e);
	}

}
