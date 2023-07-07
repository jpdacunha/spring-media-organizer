package com.jpdacunha.media.batch.core.database.repository;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpdacunha.media.batch.core.database.mapper.CursorRowMapper;
import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.database.template.JdbcTemplateExtended;

@Repository
public class DatabaseServiceRepository {
	
	private static Logger log = LoggerFactory.getLogger(DatabaseServiceRepository.class);
	
	private static final String UPDATE_FROM_BATCH_CURSOR_WHERE_ID = "UPDATE BATCH_CURSOR SET LAST_EXECUTION_DATE = ? WHERE PATH = ?";
	private static final String DELETE_FROM_BATCH_CURSOR_WHERE_ID = "DELETE FROM BATCH_CURSOR WHERE ID = ?";
	private static final String SELECT_FROM_BATCH_CURSOR_WHERE_PATH = "SELECT * FROM BATCH_CURSOR WHERE PATH = ?";
	private static final String SELECT_OLDER_CURSOR_FROM_BATCH_CURSOR = "SELECT * FROM BATCH_CURSOR ORDER BY LAST_EXECUTION_DATE LIMIT 1;";
	private static final String SELECT_FROM_BATCH_CURSOR = "SELECT * FROM BATCH_CURSOR";
	private static final String INSERT_INTO_BATCH_CURSOR = "INSERT INTO BATCH_CURSOR (ID, PATH, CREATION_DATE, LAST_EXECUTION_DATE) VALUES (?, ?, ?, ?)";
	private static final String TRUNCATE_TABLE_BATCH_CURSOR = "TRUNCATE TABLE BATCH_CURSOR";

	
	@Autowired
    private JdbcTemplateExtended jdbcTemplate;
	
	public List<Cursor> findAllCursors() {

        String sql = SELECT_FROM_BATCH_CURSOR;

        return jdbcTemplate.query(sql, new CursorRowMapper());

    }
	
	public Cursor findOlderCursor() {

        String sql = SELECT_OLDER_CURSOR_FROM_BATCH_CURSOR;

        return jdbcTemplate.queryForNullableObject(sql, new CursorRowMapper());

    }
	
	public Cursor findCursorByPath(String absolutePath) {

        String sql = SELECT_FROM_BATCH_CURSOR_WHERE_PATH;

        return jdbcTemplate.queryForNullableObject(sql, new Object[]{absolutePath}, new CursorRowMapper());

    }
	
	@Transactional
	public int remove(String id) {
		
		String deleteQuery = DELETE_FROM_BATCH_CURSOR_WHERE_ID;
		
		return jdbcTemplate.update(deleteQuery, id);
		
	}
	
	@Transactional
	public int removeAll() {
		
		String deleteQuery = TRUNCATE_TABLE_BATCH_CURSOR;
		
		return jdbcTemplate.update(deleteQuery);
		
	}
	
	@Transactional 
	public int updateLastExecutionDate(String path) {
		
		String update = UPDATE_FROM_BATCH_CURSOR_WHERE_ID;
		
		int nbRows = jdbcTemplate.update(update, new Date(), path);

		return nbRows;
		
	}
	
	@Transactional 
	public int insert(Cursor cursor) {
		
		String insert = INSERT_INTO_BATCH_CURSOR;
		
		int nbRows = 0;
		
		if (cursor != null) {
			
			nbRows = jdbcTemplate.update(insert, cursor.getId(), cursor.getPath(), cursor.getCreationDate(), cursor.getLastExecutionDate());
			
		} else {
			log.error("Cannot insert null object");
		}
		
		return nbRows;
		
	}

}
