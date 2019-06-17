package com.jpdacunha.media.batch.organizer.test.service;

import org.apache.commons.io.filefilter.IOFileFilter;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.filter.impl.VideoFileFilter;
import com.jpdacunha.media.batch.organizer.service.MediaService;

public abstract class VideoRootTest extends RootTest {

	public boolean classifyByYearDirEquality(MediaBatchYamlConfiguration configuration, MediaService mediaService, String testName) {
		
		String rootDestinationPath = configuration.getPaths().getDestinationRootDirVideo();
		IOFileFilter fileFilter = new VideoFileFilter();
		
		return classifyByYearDirEquality(fileFilter, rootDestinationPath, configuration, mediaService, testName);
		
	}
	
}
