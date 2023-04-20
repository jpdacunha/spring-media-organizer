package com.jpdacunha.media.batch.core.service.impl;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.repository.DatabaseServiceRepository;
import com.jpdacunha.media.batch.core.service.CursorService;
import com.jpdacunha.media.batch.core.service.exception.CursorException;

@Service
public class CursorServiceImpl implements CursorService {
	
	private static Logger log = LoggerFactory.getLogger(CursorServiceImpl.class);
	
	@Autowired
	private DatabaseServiceRepository repository;
	
	@Override
	public void cleanAllCursors() {
		repository.removeAll();
	}
	
	@Override
	public void cleanDatabaseCursors() {
		
		List<Cursor> cursors = repository.findAllCursors();
		
		if (cursors.size() == 0) {
			log.info("No defined cursors found.");
		} else {
			
			log.info("Found [" + cursors.size() + "] in database");
			
			for (Cursor cursor : cursors) {
				
				String path = cursor.getPath();
				File file = new File(path);
				
				if (!file.exists()) {
					
					int rows = repository.remove(cursor.getId());
					
					if (rows == 1) {
						log.info("Cursor identified by [" + path + "] sucessfully removed from database");
					}
					
				} else {
					log.debug("Cursor identified by [" + path + "] still exists.");
				}
				
			}
			
		}
		
	}

	@Override
	public Cursor createCursor(String path) {
		
		if (path == null || (path != null && path.equals(""))) {
			
			throw new CursorException("Invalid null or empty path");
			
		} else {
			
			File file = new File(path);
			
			if (file.exists()) {
			
				if (file.isDirectory()) {
					
					Cursor cursor = new Cursor(path, new Date());
					
					repository.insert(cursor);
					
					return repository.findCursorByPath(path);
					
				} else {
					throw new CursorException("Invalid path : provided path is not a directory");
				}
				
			} else {
				throw new CursorException("[" + file.getAbsolutePath() + "] does not exists.");
			}
			
		}
		
	}

	@Override
	public List<Cursor> getAll() {
		return repository.findAllCursors();
	}

	@Override
	public void registerCursors(File startDir) {
		// TODO Auto-generated method stub
		
	}
	
}
