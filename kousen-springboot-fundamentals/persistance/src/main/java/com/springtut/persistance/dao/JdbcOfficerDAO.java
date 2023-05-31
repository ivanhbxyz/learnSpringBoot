package com.springtut.persistance.dao;

import com.springtut.persistence.entities.Officer;
import com.springtut.persistence.entities.Rank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@SuppressWarnings({"ConstantConditions", "SqlResolve", "SqlNoDataSourceInspection"})


@Repository
// You CAN only Autowire if you are autowiring into a Spring component
public class JdbcOfficerDAO implements OfficerDAO {
	
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertOfficer;

    
    private final RowMapper<Officer> officerMapper =
            (ResultSet rs, int rowNum) -> 
    			new Officer(rs.getInt("id"), // Java 8 lambda expression
                    Rank.valueOf(rs.getString("rank")),
                    rs.getString("first_name"),
                    rs.getString("last_name"));

	
    
    // Realize if all you have is ONE constructor in a class
    // all of its arguments are autowired automatically
    @Autowired
    public JdbcOfficerDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        insertOfficer = new SimpleJdbcInsert(jdbcTemplate) // SimpleJdbc generates the insert statement
                .withTableName("officers")
                .usingGeneratedKeyColumns("id");
    }

    /*
    @Override
    public Officer save(Officer officer) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(officer);
        Integer newId = (Integer) insertOfficer.executeAndReturnKey(source);
        officer.setId(newId);
        return officer;
    }*/
    @Override
    public Officer save(Officer officer) {
    	Map<String, Object> parameter = new HashMap<>();
    	parameter.put("rank", officer.getRank());
    	parameter.put("first_name", officer.getFirstName());
    	parameter.put("last_name", officer.getLastName());
    	Integer newId = (Integer) insertOfficer.executeAndReturnKey(parameter);
    	officer.setId(newId);
    	return officer;
    }
    
    
    
    
    
    public Optional<Officer> findById(Integer id) {
    	if(!existsById(id)) return Optional.empty();
    	
    	return Optional.of(jdbcTemplate.queryForObject(
    			"SELECT * FROM officers WHERE id=?", 
    			new RowMapper<Officer>() {
    				@Override
    				public Officer mapRow(ResultSet rs, int rowNum) throws SQLException {
    					return new Officer(rs.getInt("id"),
    							Rank.valueOf(rs.getString("rank")),
    							rs.getString("first_name"),
    							rs.getString("last_name"));
    				}
    			},
    			id));
    }
    
    /*
    @Override
    public Optional<Officer> findById(Integer id) {
        try (Stream<Officer> stream =
                     jdbcTemplate.queryForStream(
                             "select * from officers where id=?",
                             officerMapper,
                             id)) {
            return stream.findAny();
        }
    }
    */

    /*
    // Alternative 1: extra SQL call to verify row exists
    @SuppressWarnings("unused")
    public Optional<Officer> findById1(Integer id) {
        if (!existsById(id)) return Optional.empty();
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                "SELECT * FROM officers WHERE id=?",
                officerMapper,
                id));
    }

    // Alternative 2: catch the exception when row doesn't exist
    @SuppressWarnings("unused")
    public Optional<Officer> findById2(Integer id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    "SELECT * FROM officers WHERE id=?",
                    officerMapper,
                    id));
        } catch (IncorrectResultSizeDataAccessException e) {
            return Optional.empty();
        }
    }
    */

    /*
    @Override
    public List<Officer> findAll() {
        return jdbcTemplate.query("SELECT * FROM officers", officerMapper);
    }
    */

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(
        		"select count(*) from officers", Long.class);
    }

    @Override
    public void delete(Officer officer) {
        jdbcTemplate.update(
        			"DELETE FROM officers WHERE id=?", officer.getId());
    }

    @Override
    public boolean existsById(Integer id) {
        return jdbcTemplate.queryForObject(
                "SELECT EXISTS(SELECT 1 FROM officers where id=?)", Boolean.class, id);
    }

	@Override
	public List<Officer> findAll() {
		return jdbcTemplate.query("SELECT * FROM officers", officerMapper);
	}
	
}






