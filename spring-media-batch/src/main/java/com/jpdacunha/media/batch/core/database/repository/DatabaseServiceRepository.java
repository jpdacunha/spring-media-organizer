package com.jpdacunha.media.batch.core.database.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jpdacunha.media.batch.core.database.mapper.CursorRowMapper;
import com.jpdacunha.media.batch.core.database.model.Cursor;

@Repository
public class DatabaseServiceRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public List<Cursor> findAllCursors() {

        String sql = "SELECT * FROM BATCH_CURSOR";

        return jdbcTemplate.query(sql, new CursorRowMapper());

    }
	
	public Cursor findCursorByPath(String absolutePath) {

        String sql = "SELECT * FROM BATCH_CURSOR WHERE PATH = ?";

        return jdbcTemplate.queryForObject(sql, new Object[]{absolutePath}, new CursorRowMapper());

    }

}
