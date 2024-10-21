package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.core.utils.ArrayHelper;
import com.jpdacunha.media.batch.core.utils.DateUtils;
import com.jpdacunha.media.batch.core.utils.RegexpUtils;
import com.jpdacunha.media.batch.organizer.model.DateDescriptor;

public abstract class FilenameDatePatternClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(FilenameDatePatternClassificationStrategy.class);
	
	private PatternConfigurations patternConfigurations;
	
	public FilenameDatePatternClassificationStrategy(String[] fileNamePatterns, String[] fileNameEmbeddedDatePatterns, String[] dateFormaterPatterns) {
		
		super();
		this.patternConfigurations = new PatternConfigurations(fileNamePatterns, fileNameEmbeddedDatePatterns, dateFormaterPatterns);

	}
	
	public class PatternConfigurations {
		
		private List<Triplet<String, String, SimpleDateFormat>> configurations = new ArrayList<>(); 
		
		public PatternConfigurations(String[] fileNamePatterns, String[] fileNameEmbeddedDatePatterns,String[] dateFormaterPatterns) {
			
			super();
			
			if (ArrayHelper.sameLength(fileNamePatterns, fileNameEmbeddedDatePatterns, dateFormaterPatterns)) {
				
				int i = 0;
				for (String pattern : fileNamePatterns) {
					
					SimpleDateFormat sdf = new SimpleDateFormat(dateFormaterPatterns[i]);
					Triplet<String, String, SimpleDateFormat> triplet = new Triplet<>(pattern, fileNameEmbeddedDatePatterns[i], sdf);
					configurations.add(triplet);
					i++;
					
				}
				
				if (log.isDebugEnabled()) {
					log.debug("Registered Tuples of pattern configuration : [" + Arrays.toString(this.configurations.toArray()) + "]");
				}
				
			} else {
				log.warn("Invalid configuration detected : no initilization will be done");
			}
			
		}
		
		public List<Triplet<String, String, SimpleDateFormat>> getConfigurations() {
			return configurations;
		}
		
	}

	public Path getClassificationPath(File toOrganize, File destDir) {
		
		if (destDir != null && toOrganize != null) {
			
			if (toOrganize.isFile()) {
				
				if (toOrganize.exists()) {
				
					String fileName = toOrganize.getName();
					
					for (Triplet<String, String, SimpleDateFormat> patternConfig : this.patternConfigurations.getConfigurations()) {
						
						log.debug("Processing [" + patternConfig + "]");
						
						String fileNamePattern = patternConfig.getValue0();
						String fileNameEmbeddedDatePattern = patternConfig.getValue1();
						SimpleDateFormat sdf = patternConfig.getValue2();
						
						if (RegexpUtils.match(fileNamePattern, fileName) == true) {
							
							log.debug("File identified by [" + fileName + "] MATCH fileName pattern [" + fileNamePattern + "]");
							
							String extractedDate = RegexpUtils.extractFirst(fileNameEmbeddedDatePattern, fileName);
							
							if (!StringUtils.isEmpty(extractedDate)) {
								
								log.debug("Extracted date as string [" + extractedDate + "]");
								
								Date date = DateUtils.toDate(sdf, extractedDate);
								
								DateDescriptor dateDescriptor = new DateDescriptor(date.getTime(), ClassificationStrategy.DEFAULT_LOCALE);
								
								String yearMonthPath = dateDescriptor.getYearMonthAsStringPath(destDir);
								
								return Path.of(yearMonthPath);
								
							} else {
								log.debug("Unable to extract date from [" + fileName + "] with pattern ["+ fileNameEmbeddedDatePattern + "]");
							}
							
						} else {
							
							log.debug("File identified by [" + fileName + "] DOES NOT MATCH fileName pattern [" + fileNamePattern + "]");
							
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

}
