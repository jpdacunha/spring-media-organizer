package com.jpdacunha.media.batch.removeduplicatesfotos.model;

import java.io.File;

public class DuplicatePhotosModel {
	
	private File file;
	private File duplicateFile;
	
	public DuplicatePhotosModel(File file, File duplicateFile) {
		super();
		this.file = file;
		this.duplicateFile = duplicateFile;
	}

	public File getFile() {
		return file;
	}

	public File getDuplicateFile() {
		return duplicateFile;
	}
	
}
