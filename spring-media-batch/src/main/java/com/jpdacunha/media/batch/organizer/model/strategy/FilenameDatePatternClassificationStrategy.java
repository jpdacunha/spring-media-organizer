package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.core.utils.RegexpUtils;

public abstract class FilenameDatePatternClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(FilenameDatePatternClassificationStrategy.class);
	
	private String[] fileNamePatterns;
	private String[] fileNameEmbeddedDatePatterns;
	private String[] dateFormaterPatterns;
	
	public FilenameDatePatternClassificationStrategy(String[] fileNamePatterns, String[] fileNameEmbeddedDatePatterns, String[] dateFormaterPatterns) {
		super();
		this.fileNamePatterns = fileNamePatterns;
		this.fileNameEmbeddedDatePatterns = fileNameEmbeddedDatePatterns;
		this.dateFormaterPatterns = dateFormaterPatterns;
	}

	public Path getClassificationPath(File toOrganize, File destDir) {
		
		if (destDir != null && toOrganize != null) {
			
			if (toOrganize.isFile()) {
				
				if (toOrganize.exists()) {
				
					log.info("FilenameDatePatternClassificationStrategy >>> " 
							+ Arrays.toString(this.fileNamePatterns) + " " 
							+ Arrays.toString(this.fileNameEmbeddedDatePatterns)  + " " 
							+ Arrays.toString(this.dateFormaterPatterns)  + " " 
					);
					
					String fileName = toOrganize.getName();
					
					for (String pattern : this.fileNamePatterns) {
						
						if (RegexpUtils.match(pattern, fileName) == true) {
							
							log.debug("File identified by [" + fileName + "] MATCH fileName pattern [" + pattern + "]");
							
							for (String datePattern : this.fileNameEmbeddedDatePatterns) {
								
								String extractedDate = RegexpUtils.extractFirst(datePattern, fileName);
								
								if (!StringUtils.isEmpty(extractedDate)) {
									
									log.debug("Extracted date as string [" + extractedDate + "]");
									
								} else {
									log.debug("Unable to extract date from [" + fileName + "] with pattern ["+ datePattern + "]");
								}
								
							}
							
						} else {
							log.debug("File identified by [" + fileName + "] DOES NOT MATCH fileName pattern [" + pattern + "]");
						}
						
					}
					
				} else {
					log.warn("File identified by [" + toOrganize.getAbsolutePath() + "] does not exists.");
				}
				
			} else {
				log.warn("File identified by [" + toOrganize.getAbsolutePath() + "] is not a simple file. Maybe a directory ?");
			}
			
			
		} else {
			log.warn("Missing mandatory parameter destDir:[" + destDir + "], toOrganize:[" + toOrganize + "]");
		}
		return null;
		
	}

	public String[] getFileNamePatterns() {
		return fileNamePatterns;
	}

	public String[] getFileNameEmbeddedDatePatterns() {
		return fileNameEmbeddedDatePatterns;
	}

	public String[] getDateFormaterPatterns() {
		return dateFormaterPatterns;
	}

}
