package com.jpdacunha.media.batch.organizer.test.tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class PhotoMediaServiceTest extends PhotoRootTest {
	
	private static Logger log = LoggerFactory.getLogger(PhotoMediaServiceTest.class);
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private MediaBatchYamlConfiguration configuration;

	@Test
	public void PhotoClassifyByYearNominal1() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
			
	}

	@Test
	public void PhotoClassifyByYearNominalIgnoringNonImageFiles() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void PhotoClassifyByYearFileAlreadyExists() {
		
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
