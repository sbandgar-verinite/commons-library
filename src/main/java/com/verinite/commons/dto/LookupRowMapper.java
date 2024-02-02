package com.verinite.commons.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class LookupRowMapper implements RowMapper<Object> {
	@Override
	public LookupResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new LookupResponse(rs.getLong("id"), rs.getString("key"), rs.getString("label"), rs.getString("value"));
	}
}