package com.jpdacunha.media.batch.organizer.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.utils.FileSystemUtils;


public abstract class AbstractBaseTest {
	
	private static Logger log = LoggerFactory.getLogger(AbstractBaseTest.class);
	
	protected final static String ROOT_PATH = "src//test//resources//spring-media-batch//";
	
	protected final static String EXPECTED_DIR_NAME = "expected";
	
	public abstract String getDirTestName();
	
	public abstract String getDescription();
	
	//protected Config config;
	
	@Before
	public void setUp() {
		
		log.info(" TESTING CASE :>  " + getDescription());
		
		//Config config = ConfigLoader.load(TEXT_EXPORTER_ROOT_PATH + getDirTestName() + "//config.properties");
		//this.config=config;
		
	}
	
	@Test
    public void rootTest() {
    	
    	
    	/*File expected = new File(ROOT_PATH + getDirTestName() + "//" + EXPECTED_DIR_NAME);    	
    	File generated = config.getPathToExport();
    	    	
    	boolean contentEquals = FileSystemUtils.isDirEquals(expected, generated);
    	
    	displayResult(config);
    	
    	Assert.assertTrue(contentEquals);*/
    	
    }
    
    /*public void displayResult(Config config) {
    	
    	log.info(" RESULT CASE :>  " + getDescription());
    	log.info("  Output : ");
    	FileSystemUtils.displayDirectoryContent(config.getPathToExport());
    	log.info("  ZipPath : ");
    	FileSystemUtils.displayDirectoryContent(config.getZipPathFile());
    	
    }*/
    
    @After
    public void cleanUp() {
    	
    	/*if (config != null) {
    	
	    	File output = config.getPathToExport();
	    	
	    	FileSystemUtils.recreateDir(output);
	    	
	    	File outputZip = config.getZipPathFile();
	    	
	    	FileUtils.recreateDir(outputZip);
	    	
    	}*/
    	
    	log.info(" END CASE :>  " + getDescription());
    	
    }

}
