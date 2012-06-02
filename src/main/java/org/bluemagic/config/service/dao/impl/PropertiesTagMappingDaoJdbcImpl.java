package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesTagMappingDaoJdbcImpl extends JdbcDaoSupport implements PropertiesTagMappingDao{
	
	private static final String INSERT_TAG = "INSERT INTO TAGS (KEY,VALUE) VALUES (?, ?)";
	
	private static final String INSERT_TAG = "INSERT INTO PROPERTIES (KEY,VALUE) VALUES (?, ?)";
	
	private static final String DELETE_TAG_BY_ID = "DELETE FROM TAGS WHERE ID=?";
	
	private static final String DELETE_PROPERTY_BY_ID = "DELETE FROM PROPERTY WHERE ID=?";
	
	@Override
	public boolean insertUser(String user) {
		
		int rowsUpdated = getJdbcTemplate().update(INSERT_USER, user);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deleteTagById(int tag_id) {
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_TAG_BY_ID, tag_id);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean deletePropertyandTagById(int tag_id, int Property_id) {
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_TAG_BY_ID, tag_id);
		int rowsUpdated = getJdbcTemplate().update(DELETE_PROPERTY_BY_ID, property_id);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
