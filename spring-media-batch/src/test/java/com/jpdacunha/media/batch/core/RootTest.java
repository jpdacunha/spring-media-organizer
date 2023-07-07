package com.jpdacunha.media.batch.core;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.service.MediaService;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

public abstract class RootTest {
	
	private static final String DOWN_ARROW = Character.toString((char)0x2BC6);
	private static Logger log = LoggerFactory.getLogger(RootTest.class);
	
	public boolean classifyByYearDirEquality(
			IOFileFilter fileFilter,
			String rootDestinationPath, 
			MediaBatchYamlConfiguration configuration, 
			MediaService mediaService, 
			String nameofCurrMethod
		) {
		
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
	
	public boolean removeImageDuplicates(
			RemoveDuplicatesFotosYamlConfiguration configuration, 
			RemoveDuplicateImagesService service, 
			String nameofCurrMethod,
			boolean dryRun
		) {
		
		String rootDestinationPath = "src/test/resources/spring-media-batch";
		
		File srcDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "source");
		File expectedDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "expected");
		
		service.removeDuplicates(srcDir, dryRun);
		
		boolean result = FileSystemUtils.isDirEquals(expectedDir, srcDir);
		
		if (!result) {
			displayResult(expectedDir, srcDir);
		} 
		
		//Refill sources dir with content from resources dir
		File resourcesDir = new File(rootDestinationPath + File.separator + nameofCurrMethod + File.separator + "resources");
		FileSystemUtils.copyDirectory(resourcesDir, srcDir);
		
		return result;	
		
	}
	
	public boolean removeImageDuplicates(
			RemoveDuplicatesFotosYamlConfiguration configuration, 
			RemoveDuplicateImagesService service, 
			String nameofCurrMethod
		) {
		
		return removeImageDuplicates(configuration, service, nameofCurrMethod, false);
	
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
