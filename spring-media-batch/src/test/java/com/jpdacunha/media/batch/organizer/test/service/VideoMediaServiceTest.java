package com.jpdacunha.media.batch.organizer.test.service;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;

public class VideoMediaServiceTest extends VideoRootTest {

	public boolean classifyByYearDirEquality(MediaBatchYamlConfiguration configuration, MediaService mediaService, String testName) {
		String rootDestinationPath = configuration.getPaths().getDestinationRootDirVideo();
		return classifyByYearDirEquality(rootDestinationPath, configuration, mediaService, testName);
	}

}
