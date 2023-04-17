package com.jpdacunha.media.batch.core.database.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.table.BatchCursorTable;

public class CursorRowMapper implements RowMapper<Cursor> {

	@Override
	public Cursor mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Cursor cursor = new Cursor(
				rs.getString(BatchCursorTable.ID), 
				rs.getString(BatchCursorTable.PATH), 
				rs.getDate(BatchCursorTable.LAST_EXECUTION_DATE), 
				rs.getDate(BatchCursorTable.CREATION_DATE)
		);
		return cursor;
		
	}

}
