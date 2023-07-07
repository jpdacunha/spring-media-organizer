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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class CursorServiceTestCleanAllTest {
	
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
