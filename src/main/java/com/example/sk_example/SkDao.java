package com.example.sk_example;

import com.example.sk_example.dto.SkDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SkDao {

    private final JdbcTemplate jdbcTemplate;

    public SkDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Integer increaseCurrentById(SkDto skDto) {
        String increaseQuery = "update sk_example_table set obj['current'] = " +
                "(((obj->>'current')::Integer + ? )::varchar)::jsonb where id = ? " +
                "returning obj['current']";
        try {
            return jdbcTemplate.queryForObject(increaseQuery, Integer.class, skDto.getAdd(), skDto.getId());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
