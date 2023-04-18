package com.jpdacunha.media.batch.removeduplicatesfotos.service;

import java.io.File;

import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImagesException;

public interface RemoveDuplicateImagesService {
	
	public static final String DUPLICATE_EXTENSION = ".duplicate";
	
	public void removeDuplicateFotos() throws RemoveDuplicateImagesException;
	
	public void removeDuplicates(File startDir, boolean dryRun);

}
