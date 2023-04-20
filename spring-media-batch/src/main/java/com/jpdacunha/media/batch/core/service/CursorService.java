package com.jpdacunha.media.batch.core.service;

import java.io.File;
import java.util.List;

import com.jpdacunha.media.batch.core.database.model.Cursor;

public interface CursorService {

	public void cleanDatabaseCursors();
	
	public Cursor createCursor(String path);
	
	public List<Cursor> getAll();

	public void cleanAllCursors();

	public void registerCursors(File startDir);

}
