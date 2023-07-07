package com.jpdacunha.media.batch.core.service.test;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.repository.DatabaseServiceRepository;
import com.jpdacunha.media.batch.core.service.CursorService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CursorServicegetWorkDirFromRegisteredCursorsTest {
	
	private static Logger log = LoggerFactory.getLogger(CursorServicegetWorkDirFromRegisteredCursorsTest.class);
	
	private final static String ROOT_PATH = "src/test/resources/spring-media-batch/";
	
	@Autowired
	private CursorService service;
	
	@Autowired
	private DatabaseServiceRepository repository;
	
	@Before
	public void init() {
		
		service.cleanAllCursors();
	
	}
	
	@After
	public void after() {
		
		List<Cursor> cursors = repository.findAllCursors();
		
		for (Cursor cursor: cursors) {
			
			log.debug("Cursor : " + cursor);
			
		}
		
	}
	
	
	@Test
	public void getWorkDirFromRegisteredCursors_nominal_filteringDirs() {
		
		String testedPath = ROOT_PATH + "getWorkDirFromRegisteredCursors_nominal_filteringDirs";
		
		File returnedFile = service.getWorkDirFromRegisteredCursors(new File(testedPath));
		
		Assert.assertEquals("2022", returnedFile.getName());
		
	}
	
	@Test
	public void getWorkDirFromRegisteredCursors_error_missingcursor() {
		
		String testedPath = ROOT_PATH + "getWorkDirFromRegisteredCursors_error_missingcursor";
		
		File returnedFile = service.getWorkDirFromRegisteredCursors(new File(testedPath));
		
		Assert.assertTrue(returnedFile == null);
		
	}
	
	@Test
	public void getWorkDirFromRegisteredCursors_nominal_nextPath() {
		
		String testedPath = ROOT_PATH + "getWorkDirFromRegisteredCursors_nominal_nextPath";
		
		service.createCursor(testedPath + "/2000");
		service.createCursor(testedPath + "/2021");
		
		File returnedFile = service.getWorkDirFromRegisteredCursors(new File(testedPath));
		
		Assert.assertEquals("2022", returnedFile.getName());
		
	}
	
	@Test
	public void getWorkDirFromRegisteredCursors_nominal_firtsInDatabase() {
		
		String testedPath = ROOT_PATH + "getWorkDirFromRegisteredCursors_nominal_firtsInDatabase";
		
		service.createCursor(testedPath + "/2000");
		service.createCursor(testedPath + "/2021");
		service.createCursor(testedPath + "/2022");
		service.createCursor(testedPath + "/2023");
		service.createCursor(testedPath + "/2024");
		
		File returnedFile = service.getWorkDirFromRegisteredCursors(new File(testedPath));
		
		Assert.assertEquals("2000", returnedFile.getName());
		
	}

}
