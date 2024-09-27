package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;
import com.jpdacunha.media.batch.organizer.model.DateDescriptor;
import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;

public class LastModififiedClassificationStrategy implements ClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	private MediaBatchYamlConfiguration configuration;
	
	public LastModififiedClassificationStrategy(MediaBatchYamlConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public Path getClassificationPath(File toOrganize, File destDir) throws UnrelevantClassificationStrategyException {
		
		if (destDir != null && toOrganize != null) {
			
			log.info("LastModififiedClassificationStrategy >>> " + Arrays.toString(configuration.getFileNamePatterns().getPhotoFileNamePatterns()) + " " + Arrays.toString(configuration.getFileNamePatterns().getPhotoDatePatterns()));
			
			DateDescriptor dateDescriptor = new DateDescriptor(toOrganize.lastModified(), ClassificationStrategy.DEFAULT_LOCALE);
			
			String yearMonthDirName = dateDescriptor.getYear() + File.separator + StringUtils.capitalize(dateDescriptor.getMonthName());
			
			String yearMonthPath = destDir.getAbsolutePath() + File.separator + yearMonthDirName;
			
			return Path.of(yearMonthPath);
			
		} else {
			log.warn("Missing mandatory parameter destDir:[" + destDir + "], toOrganize:[" + toOrganize + "]");
		}
		return null;
		
	}


}
