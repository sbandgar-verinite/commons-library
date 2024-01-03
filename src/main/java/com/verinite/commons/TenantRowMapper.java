package com.verinite.commons;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.verinite.commons.model.Tenant;


public class TenantRowMapper implements RowMapper<Tenant> {
	@Override
	public Tenant mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Tenant(rs.getInt("id"), rs.getString("tenant_code"), rs.getString("tenant_name"));
	}
}