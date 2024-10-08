package com.jpdacunha.media.batch.organizer.model;

import java.io.File;
import java.util.Locale;

import lombok.ToString;

@ToString(callSuper=false, includeFieldNames=true)
public class MediaDescriptor {
	
	public Locale getLocale() {
		return dateDescriptor.getLocale();
	}

	private String name;
	private File file;
	private String originalPath;

	private DateDescriptor dateDescriptor ;
	
	public MediaDescriptor(File file, Locale locale) {
		super();
		this.file = file;
		this.name = file.getName();
		this.originalPath = file.getAbsolutePath();
		this.dateDescriptor = new DateDescriptor(file.lastModified(), locale);	
	}

	public String getName() {
		return name;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public DateDescriptor getDateDescriptor() {
		return dateDescriptor;
	}

}
