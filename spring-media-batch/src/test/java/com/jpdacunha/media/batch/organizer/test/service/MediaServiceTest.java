package com.jpdacunha.media.batch.organizer.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MediaServiceTest extends RootTest {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceTest.class);
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private MediaBatchYamlConfiguration configuration;

	@Test
	public void classifyByYearNominal1() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
			
	}

	@Test
	public void classifyByYearNominalIgnoringNonImageFiles() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
		
	}
	
	public MediaService getMediaService() {
		return mediaService;
	}

	public void setMediaService(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	public MediaBatchYamlConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(MediaBatchYamlConfiguration configuration) {
		this.configuration = configuration;
	}

}
