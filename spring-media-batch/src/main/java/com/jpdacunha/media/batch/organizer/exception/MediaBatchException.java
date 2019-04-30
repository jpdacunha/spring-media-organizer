package com.jpdacunha.media.batch.organizer.exception;

import java.io.IOException;

public class MediaBatchException extends Exception {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3673352368797824380L;

	public MediaBatchException(String msg) {
		super(msg);
	}

	public MediaBatchException(IOException e) {
		super(e);
	}

}
