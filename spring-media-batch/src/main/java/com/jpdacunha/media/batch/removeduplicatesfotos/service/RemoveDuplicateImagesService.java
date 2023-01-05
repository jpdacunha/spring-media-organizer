package com.jpdacunha.media.batch.removeduplicatesfotos.service;

import java.io.File;

import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImageshException;

public interface RemoveDuplicateImagesService {
	
	public void removeDuplicateFotos() throws RemoveDuplicateImageshException;
	
	public void removeDuplicates(File startDir, boolean dryRun);

}
