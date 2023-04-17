package com.jpdacunha.media.batch.removeduplicatesfotos.test.tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.repository.DatabaseServiceRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DatabaseBatchCursorTest {
	
	@Autowired
	private DatabaseServiceRepository repository;
	
	@Test
	public void findAllTest() {
		
		List<Cursor> cursors = repository.findAllCursors();
		
		Assert.assertTrue(cursors.size() == 1);
		
	}
	
}
