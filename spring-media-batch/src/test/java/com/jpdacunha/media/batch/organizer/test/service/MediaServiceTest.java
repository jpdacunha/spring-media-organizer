package com.jpdacunha.media.batch.organizer.test.service;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;
import com.jpdacunha.media.batch.organizer.filter.impl.ImageFileFilter;
import com.jpdacunha.media.batch.organizer.service.MediaService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class MediaServiceTest {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceTest.class);
	
	@Autowired
	private MediaService mediaService;
	
	@Value("${media-batch.paths.destination}")
	private String destDirPath;

	@Test
	public void classifyByYearNominal1() throws MediaBatchException {
		
		IOFileFilter fileFilter = new ImageFileFilter();
		
		File destDir = new File(destDirPath);
		File startDir = new File("src/test/resources/spring-media-batch/classifyByYearNominal1/source");
		
		mediaService.classifyByYear(startDir, destDir, fileFilter);
		
	}

	/*@Override
	public String getDirTestName() {
		return "MediaServiceTest";
	}

	@Override
	public String getDescription() {
		return "Test if the test infrastructure is ok";
	}*/

}
