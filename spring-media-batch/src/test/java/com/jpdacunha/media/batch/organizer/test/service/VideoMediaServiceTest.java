package com.jpdacunha.media.batch.organizer.test.service;

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
public class VideoMediaServiceTest extends VideoRootTest {
	
	private static Logger log = LoggerFactory.getLogger(PhotoMediaServiceTest.class);
	
	@Autowired
	private MediaService mediaService;
	
	@Autowired
	private MediaBatchYamlConfiguration configuration;

	@Test
	public void VideoClassifyByYearNominal1() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
			
	}
	
	@Test
	public void VideoClassifyByYearNominalVariousExtensions() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		boolean result = super.classifyByYearDirEquality(configuration, mediaService, testName);
		
		Assert.assertTrue(result);
			
	}

}
