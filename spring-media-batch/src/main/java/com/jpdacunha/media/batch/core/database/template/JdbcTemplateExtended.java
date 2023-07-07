package com.jpdacunha.media.batch.core.database.template;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class JdbcTemplateExtended extends JdbcTemplate {

    public JdbcTemplateExtended(DataSource datasource){
        super(datasource);
    }
    
    /**
     * Since query for object returns an exception if no object is found. This method allow to workaround issu describes here
     * https://stackoverflow.com/questions/10606229/jdbctemplate-query-for-string-emptyresultdataaccessexception-incorrect-result
     * @param <T>
     * @param sql
     * @param args
     * @param rowMapper
     * @return
     * @throws DataAccessException
     */
    public <T> T queryForNullableObject(String sql, @Nullable Object[] args, RowMapper<T> rowMapper) throws DataAccessException {

        List<T> results = query(sql, args, rowMapper);

        if (results == null || results.isEmpty()) {
            return null;
        }
        else if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
        else{
            return results.iterator().next();
        }
    	
    	
    }
    
    /**
     * Since query for object returns an exception if no object is found. This method allow to workaround issu describes here
     * https://stackoverflow.com/questions/10606229/jdbctemplate-query-for-string-emptyresultdataaccessexception-incorrect-result
     * @param <T>
     * @param sql
     * @param rowMapper
     * @return
     * @throws DataAccessException
     */
    public <T> T queryForNullableObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        List<T> results = query(sql, rowMapper);

        if (results == null || results.isEmpty()) {
            return null;
        }
        else if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size());
        }
        else{
            return results.iterator().next();
        }
    }

    public <T> T queryForNullableObject(String sql, Class<T> requiredType) throws DataAccessException {
        return queryForObject(sql, getSingleColumnRowMapper(requiredType));
    }

}
