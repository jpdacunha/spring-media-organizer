package com.jpdacunha.media.batch.core.database.repository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jpdacunha.media.batch.core.database.mapper.CursorRowMapper;
import com.jpdacunha.media.batch.core.database.model.Cursor;

@Repository
public class DatabaseServiceRepository {
	
	private static Logger log = LoggerFactory.getLogger(DatabaseServiceRepository.class);
	
	private static final String DELETE_FROM_BATCH_CURSOR_WHERE_ID = "DELETE FROM BATCH_CURSOR WHERE ID = ?";
	private static final String SELECT_FROM_BATCH_CURSOR_WHERE_PATH = "SELECT * FROM BATCH_CURSOR WHERE PATH = ?";
	private static final String SELECT_FROM_BATCH_CURSOR = "SELECT * FROM BATCH_CURSOR";
	private static final String INSERT_INTO_BATCH_CURSOR = "INSERT INTO BATCH_CURSOR (ID, PATH, CREATION_DATE, LAST_EXECUTION_DATE) VALUES (?, ?, ?, ?)";
	private static final String TRUNCATE_TABLE_BATCH_CURSOR = "TRUNCATE TABLE BATCH_CURSOR";

	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Cursor> findAllCursors() {

        String sql = SELECT_FROM_BATCH_CURSOR;

        return jdbcTemplate.query(sql, new CursorRowMapper());

    }
	
	public Cursor findCursorByPath(String absolutePath) {

        String sql = SELECT_FROM_BATCH_CURSOR_WHERE_PATH;

        return jdbcTemplate.queryForObject(sql, new Object[]{absolutePath}, new CursorRowMapper());

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
