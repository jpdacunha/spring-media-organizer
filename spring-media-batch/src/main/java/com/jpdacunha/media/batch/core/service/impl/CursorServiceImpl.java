package com.jpdacunha.media.batch.core.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.repository.DatabaseServiceRepository;
import com.jpdacunha.media.batch.core.filter.impl.YearDirectoryFileFilter;
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
	public File getWorkDirFromRegisteredCursors(File startDir) {
			
		File cursorFile = null;
		
		FileFilter fileFilter = new YearDirectoryFileFilter();
		
		List<File> cursorList = (List<File>) FileUtils.listFilesAndDirs(startDir, (IOFileFilter)fileFilter, (IOFileFilter)fileFilter);
		
		if (cursorList.size() == 0) {
			log.info("No files in specified directories.");
		} else {
			
			if (log.isDebugEnabled()) {
				log.debug("Found [" + cursorList.size() + "] possible cursors in [" + startDir.getAbsolutePath() + "]");
			}
			
			// Starting for searching of never used cursors ...
			for (final Iterator<File> cursorListIterator = cursorList.listIterator(); cursorListIterator.hasNext();) {
				
				File file = cursorListIterator.next();
				String absolutePath = file.getAbsolutePath();
				
				
				if (log.isDebugEnabled()) {
					log.debug("Found [" + absolutePath + "]");
				}
				
				/* 
				 * Per default listFilesAndDirs includes the enclosing directory 
				 * (For listFilesAndDirs JAVADOC says : "The resulting collection includes the starting directory and any subDirectories that match the directory filter." 
				 * For this reason we test again to ensure only matching pattern files are included
				 */
				if (fileFilter.accept(file)) {
					
					boolean exists = cursorExists(absolutePath);
					if (!exists) {
						cursorFile = file;
						log.info("Returning [" + cursorFile.getAbsolutePath() + "] as current cursor"); 
						break;
					}
					
				}
				
			}
			
		}
		
		if (cursorFile == null) {
			
			//If no cursor found moving to older instead
			Cursor olderCursor = findOlderCursor();
			
			if (olderCursor != null) {
				
				cursorFile = new File(olderCursor.getPath());
				log.info("Using existing cursor [" + cursorFile.getAbsolutePath() + "] as current cursor");
				
			} else {
				log.error("No elligible path found : returning null");
			}
			
		} else {
			
			if (log.isDebugEnabled()) {
				log.debug("Returning [" + cursorFile.getAbsolutePath() + "] as current cursor"); 
			}
			
		}
		
		return cursorFile;
		
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
	public boolean cursorExists(String path) {
		
		Cursor cursor = findByPath(path);
		
		if(log.isDebugEnabled()) {
			log.debug(" > Searching for [" + path + "] found cursor [" + cursor +  "]");
		}
		
		return cursor != null;
		
	}
	
	@Override
	public Cursor findOlderCursor() {
		
		return repository.findOlderCursor();
		
	}
	
	@Override
	public Cursor findByPath(String path) {
		
		if (path == null || (path != null && path.equals(""))) {
			
			throw new CursorException("Invalid null or empty path");
			
		} else {
			
			return repository.findCursorByPath(path);
			
		}
		
	}
	
	@Override
	public Cursor createOrUpdateCursor(String path) {
		
		if(log.isDebugEnabled()) {
			log.debug(" Create or update cursor for [" + path + "]");
		}
		
		Cursor cursor = null;
		boolean cursorExists = cursorExists(path);
		
		if (cursorExists) {
			cursor = updateLastExecutionDate(path);	
		} else {
			cursor = createCursor(path);
		}
		return cursor;
		
	}
	
	@Override
	public Cursor updateLastExecutionDate(String path) {
		
		Cursor cursor = null;
		if (path == null) {
			
			throw new CursorException("Invalid null or empty path");
			
		} else {
			
			repository.updateLastExecutionDate(path);
			cursor = findByPath(path);
			
		}
		
		return cursor;
		
	}
	
	@Override
	public  Cursor createCursor(String path) {
		
		if (path == null || (path != null && path.equals(""))) {
			
			throw new CursorException("Invalid null or empty path");
			
		} else {
			
			File file = new File(path);
			
			if (file.exists()) {
			
				if (file.isDirectory()) {
					
					Cursor cursor = new Cursor(file.getAbsolutePath(), new Date());
					
					repository.insert(cursor);
					
					return repository.findCursorByPath(cursor.getPath());
					
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

}