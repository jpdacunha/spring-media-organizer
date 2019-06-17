package com.jpdacunha.media.batch.organizer.test.service;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.filter.impl.ImageFileFilter;
import com.jpdacunha.media.batch.organizer.service.MediaService;
import com.jpdacunha.media.batch.organizer.utils.FileSystemUtils;

public abstract class RootTest {
	
	private static final String DOWN_ARROW = Character.toString((char)0x2BC6);
	private static Logger log = LoggerFactory.getLogger(PhotoMediaServiceTest.class);
	
	public boolean classifyByYearDirEquality(String rootDestinationPath, MediaBatchYamlConfiguration configuration, MediaService mediaService, String nameofCurrMethod) {
		
		IOFileFilter fileFilter = new ImageFileFilter();
		
		//File destDir = new File(configuration.getDestinationRootDir());
		File destDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "result");
		File startDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "source");
		File expectedDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "expected");
		
		mediaService.classifyByYear(startDir, destDir, fileFilter, false);
		
		boolean result = FileSystemUtils.isDirEquals(expectedDir, destDir);
		
		if (!result) {
			displayResult(expectedDir, destDir);
		} 
		
		//Cleaning result directory
		FileSystemUtils.recreateDir(destDir);
		
		//Refill sources dir with content from resources dir
		File resourcesDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "resources");
		FileSystemUtils.copyDirectory(resourcesDir, startDir);
		
		//Refill result with files if exists
		File resourcesResultDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "resources-result");
		FileSystemUtils.copyDirectory(resourcesResultDir, destDir);
		
		return result;
		
	}
	
	public void displayResult(File expected, File result) {

		log.info("ERROR DURING TEST EXECUTION");
		log.info(" ");
		log.info("  ## Expected : #################### " + DOWN_ARROW);
		log.info(" ");
		FileSystemUtils.displayDirectoryContent(expected);
		log.info(" ");
		log.info("  ## Result : ###################### " + DOWN_ARROW);
		log.info(" ");
		FileSystemUtils.displayDirectoryContent(result);

	}

}
