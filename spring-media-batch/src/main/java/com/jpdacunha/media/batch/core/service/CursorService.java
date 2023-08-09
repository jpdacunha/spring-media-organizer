package com.jpdacunha.media.batch.core.service;

import java.io.File;
import java.util.List;

import com.jpdacunha.media.batch.core.database.model.Cursor;

public interface CursorService {

	public void cleanDatabaseCursors();
	
	public List<Cursor> getAll();

	public void cleanAllCursors();

	public Cursor findByPath(String path);

	public boolean cursorExists(String path);

	public Cursor findOlderCursor();

	public File getWorkDirFromRegisteredCursors(File startDir);

	public Cursor createOrUpdateCursor(String path);

	public Cursor updateLastExecutionDate(String path);

	public Cursor createCursor(String path);

	public Cursor createOrUpdateCursor(File file);

}
