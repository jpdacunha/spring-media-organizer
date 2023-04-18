package com.jpdacunha.media.batch.removeduplicatesfotos.exception;

import java.io.IOException;

public class RemoveDuplicateImagesException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673352368797824380L;
	

	public RemoveDuplicateImagesException() {
		super();
	}

	public RemoveDuplicateImagesException(String msg) {
		super(msg);
	}

	public RemoveDuplicateImagesException(IOException e) {
		super(e);
	}

}
