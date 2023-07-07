package com.jpdacunha.media.batch.core.service.test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.jpdacunha.media.batch.core.service.exception.CursorException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CursorServiceCRUDTest {
	
	private static Logger log = LoggerFactory.getLogger(CursorServiceCRUDTest.class);
	
	private final static String ROOT_PATH = "src/test/resources/spring-media-batch/";
	
	private final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private CursorService service;
	
	@Autowired
	private DatabaseServiceRepository repository;
	
	@Before
	public void init() {
		
		service.cleanAllCursors();
		
	}
	
	@Test(expected = CursorException.class)
	public void createCursor_invalidPath() {
		
		service.createCursor(null);
		
	}
	
	@Test(expected = CursorException.class)
	public void createCursor_invalidPath1() {
		
		service.createCursor("");
		
	}
	
	@Test(expected = CursorException.class)
	public void createCursor_inexistingPath() {
		
		service.createCursor(ROOT_PATH + "inexisting");
		
	}
	
	@Test()
	public void createCursor_nominal() {
		
		Cursor cursor = service.createCursor(ROOT_PATH + "createCursor_nominal/test");
		
		Assert.assertTrue(cursor.getId() != null);
		
	}
	
	@Test
	public void findByPath_nominal() {
		
		String testedPath = ROOT_PATH + "findByPath_nominal/test";
		
		Cursor cursor1 = new Cursor(testedPath, new Date());
		repository.insert(cursor1);
		
		Cursor cursor = service.findByPath(testedPath);
		
		Assert.assertTrue(cursor != null && cursor.getPath().equals(testedPath));
		
	}
	
	@Test
	public void findByPath_error() {
		
		String testedPath = ROOT_PATH + "findByPath_error/test";
		
		Cursor cursor = service.findByPath(testedPath);
		
		Assert.assertTrue(cursor == null);
		
	}
	
	@Test
	public void cursorExists_error() {
		
		String testedPath = ROOT_PATH + "cursorExists_error/test";
		
		boolean exists = service.cursorExists(testedPath);
		
		Assert.assertTrue(exists == false);
		
	}
	
	@Test
	public void cursorExists_nominal() {
		
		String testedPath = ROOT_PATH + "cursorExists_nominal/test";
		
		Cursor cursor1 = new Cursor(testedPath, new Date());
		repository.insert(cursor1);
		
		boolean exists = service.cursorExists(testedPath);
		
		Assert.assertTrue(exists == true);
		
	}
	
	
	@Test
	public void createOrUpdate_nominal_create() {
		
		String testedPath = ROOT_PATH + "createOrUpdate_nominal_create/test";
		
		boolean exists = service.cursorExists(testedPath);
		
		Assert.assertTrue(exists == false);
		
		Cursor cursor = service.createOrUpdateCursor(testedPath);
		
		exists = service.cursorExists(cursor.getPath());
		
		Assert.assertTrue(exists == true);
	
	}
	
	@Test
	public void createOrUpdate_nominal_update() {
		
		String testedPath = ROOT_PATH + "createOrUpdate_nominal_update/test";
		
		File testedFile = new File(testedPath);
		
		String absolutePath = testedFile.getAbsolutePath();
		
		Cursor cursor = service.createCursor(absolutePath);
		
		boolean exists = service.cursorExists(absolutePath);
		
		Assert.assertTrue(exists == true);
		
		//Updating first time
		cursor = service.createOrUpdateCursor(absolutePath);
		Date beforeDate = cursor.getLastExecutionDate();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}
		
		//Updating second time
		cursor = service.createOrUpdateCursor(absolutePath);
		Date afterDate = cursor.getLastExecutionDate();
		
		log.debug(sdfDate.format(afterDate) + " >= " + sdfDate.format(beforeDate));
		
		Assert.assertTrue(afterDate.after(beforeDate));
		
	}

}
