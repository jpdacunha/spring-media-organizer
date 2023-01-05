package com.jpdacunha.media.batch.organizer.test.tests;

import org.apache.commons.io.filefilter.IOFileFilter;

import com.jpdacunha.media.batch.core.filter.impl.ImageFileFilter;
import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;
import com.jpdacunha.media.batch.organizer.test.core.RootTest;

public abstract class PhotoRootTest extends RootTest {

	public boolean classifyByYearDirEquality(MediaBatchYamlConfiguration configuration, MediaService mediaService, String testName) {
		
		String rootDestinationPath = configuration.getPaths().getDestinationRootDirPhoto();
		IOFileFilter fileFilter = new ImageFileFilter();
		return classifyByYearDirEquality(fileFilter, rootDestinationPath, configuration, mediaService, testName);
		
	}
	
}
