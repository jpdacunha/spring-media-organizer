package com.jpdacunha.media.batch.organizer.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;
import com.jpdacunha.media.batch.organizer.service.MediaService;
import com.jpdacunha.media.batch.organizer.service.MetaDatasReaderService;

@Service
public class MediaServiceImpl implements MediaService {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	@Autowired
	private MetaDatasReaderService metaDatasReaderService;
	
	
	public void classifyByYear(File startDir, File destDir, FileFilter fileFilter) throws MediaBatchException {
		
		log.debug("Starting classifyByYear ...");
		
		if (startDir == null || destDir == null) {
			throw new MediaBatchException("Missing required parameter");
		}
		
		if (!startDir.isDirectory()) {
			throw new MediaBatchException(startDir.getAbsolutePath() + " is not a valid source directory");
		}
		
		if (!destDir.isDirectory()) {
			throw new MediaBatchException(destDir.getAbsolutePath() + " is not a valid destination directory");
		}
		
		if (startDir.exists()) {
			
			//Non recursive list of files
			File[] searched = startDir.listFiles(fileFilter);
			
			for (File file : searched) {
				
				
				//Les dates de modification ne sont pas conservées en cas de copie de fichier
				//Les données EXIF ne sont pas renseignées dans les photos que nous avons. Il faut se baser sur la date de modif qui peut être lue sans librairy particulière.
				log.debug("Starting classification for [" + file.getName() + "] ...");
				
				String modifiedDate = metaDatasReaderService.readModifiedDateAsString(file);
				log.debug("  :> " + modifiedDate);
				log.debug("  :> " + new Date(file.lastModified()));
				log.debug("Done.");
				
			}
			
		} else {
			log.error(startDir.getAbsolutePath() + " does not exists");
		}
		
		log.debug("End.");
		
	}

}
