package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;
import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;

public class FilenameDatePatternClassificationStrategy implements ClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	private MediaBatchYamlConfiguration configuration;
	
	public FilenameDatePatternClassificationStrategy(MediaBatchYamlConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public Path getClassificationPath(File toOrganize, File destDir) throws UnrelevantClassificationStrategyException {
		
		log.info("FilenameClassificationStrategy >>> " + Arrays.toString(configuration.getFileNamePatterns().getPhotoFileNamePatterns()) + " " + Arrays.toString(configuration.getFileNamePatterns().getPhotoDatePatterns()));
		return null;
		
	}

}
