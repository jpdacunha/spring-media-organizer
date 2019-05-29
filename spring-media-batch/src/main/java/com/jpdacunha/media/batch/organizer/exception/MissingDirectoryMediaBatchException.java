package com.jpdacunha.media.batch.organizer.exception;

import java.io.File;

public class MissingDirectoryMediaBatchException extends MediaBatchException {

	public static String createMessage(File file) {
		
		String message = "Invalid null directory.";
		
		if (file !=null)  {
			message = "[" + file.getAbsoluteFile() + "] does not exists or is not a directory.";
		}
		
		return message;
		
	}

	public MissingDirectoryMediaBatchException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
