package com.jpdacunha.media.batch.core.service.test;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class CursorServiceTest {
	
	private final static String ROOT_PATH = "src/test/resources/spring-media-batch/";
	
	@Autowired
	private CursorService service;
	
	@Autowired
	private DatabaseServiceRepository repository;
	
	@Before
	public void init() {
		
		service.cleanAllCursors();
		
		//Inserting inexisting paths to test cleanDatabaseCursors function
		Cursor cursor = new Cursor(ROOT_PATH + "/hello", new Date());
		repository.insert(cursor);
		
		Cursor cursor1 = new Cursor(ROOT_PATH + "/bonjour", new Date());
		repository.insert(cursor1);
		
		
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
		
		Cursor cursor = service.createCursor(ROOT_PATH + "createCursor_nominal");
		
		Assert.assertTrue(cursor.getId() != null);
		
	}
	
	@Test
	public void cleanDatabaseCursors_nominal() {
		
		service.createCursor(ROOT_PATH + "cleanDatabaseCursors_nominal");
		service.createCursor(ROOT_PATH + "cleanDatabaseCursors_nominal/test");
		service.createCursor(ROOT_PATH + "cleanDatabaseCursors_nominal/test1");
		
		List<Cursor> cursors = service.getAll();
		
		Assert.assertTrue(cursors.size() == 5);
		
		//See data-test.sql
		
		service.cleanDatabaseCursors();
		
		cursors = service.getAll();
		
		Assert.assertTrue(cursors.size() == 3);
		
	}

}
