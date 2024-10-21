package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.model.DateDescriptor;

public class LastModififiedClassificationStrategy implements ClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(LastModififiedClassificationStrategy.class);
	
	private MediaBatchYamlConfiguration configuration;
	
	public LastModififiedClassificationStrategy(MediaBatchYamlConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public Path getClassificationPath(File toOrganize, File destDir) {
		
		if (destDir != null && toOrganize != null) {
			
			log.info("LastModififiedClassificationStrategy >>> " 
					+ Arrays.toString(configuration.getFileNamePatterns().getPhotoFileNamePatterns()) + " " 
					+ Arrays.toString(configuration.getFileNamePatterns().getPhotoFileNameEmbeddedDatePatterns())  + " " 
					+ Arrays.toString(configuration.getFileNamePatterns().getPhotoDateFormaterPatterns())  + " " 
			);
			
			DateDescriptor dateDescriptor = new DateDescriptor(toOrganize.lastModified(), ClassificationStrategy.DEFAULT_LOCALE);
		
			String yearMonthPath = dateDescriptor.getYearMonthAsStringPath(destDir);
			
			return Path.of(yearMonthPath);
			
		} else {
			log.warn("Missing mandatory parameter destDir:[" + destDir + "], toOrganize:[" + toOrganize + "]");
		}
		return null;
		
	}


}
