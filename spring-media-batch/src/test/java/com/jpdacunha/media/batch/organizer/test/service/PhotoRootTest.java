package com.jpdacunha.media.batch.organizer.test.service;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;

public abstract class PhotoRootTest extends RootTest {

	public boolean classifyByYearDirEquality(MediaBatchYamlConfiguration configuration, MediaService mediaService, String testName) {
		String rootDestinationPath = configuration.getPaths().getDestinationRootDirPhoto();
		return classifyByYearDirEquality(rootDestinationPath, configuration, mediaService, testName);
	}
	
}
