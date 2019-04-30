package com.jpdacunha.media.batch.organizer.service;

import java.io.File;
import java.io.FileFilter;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;

public interface MediaService {
	
	public void classifyByYear(File startDir, File destDir, FileFilter fileFilter) throws MediaBatchException;

}
