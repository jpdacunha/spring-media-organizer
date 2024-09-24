package com.jpdacunha.media.batch.organizer.model.strategy;

import java.util.Arrays;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.UnrelevantClassificationStrategyException;

public class FilenameClassificationStrategy implements ClassificationStrategy {
	
	private MediaBatchYamlConfiguration configuration;
	
	public FilenameClassificationStrategy(MediaBatchYamlConfiguration configuration) {
		super();
		this.configuration = configuration;
	}

	@Override
	public String getClassificationPath() throws UnrelevantClassificationStrategyException {
		return "FilenameClassificationStrategy >>> " + Arrays.toString(configuration.getFileNamePatterns().getPhotoPatterns());
	}

}
