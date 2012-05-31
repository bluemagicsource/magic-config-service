package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.TagDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class TagDaoJdbcImpl extends JdbcDaoSupport implements TagDao {
	
	private static final String SELECT_TAG_ID = "SELECT ID FROM TAGS WHERE KEY=? AND VALUE=?";

	private static final String SELECT_TAG_BY_ID = "SELECT VALUE FROM TAGS WHERE ID=?";
	
	private static final String INSERT_TAG = "INSERT INTO TAGS (KEY,VALUE) VALUES (?, ?)";
	
	private static final String DELETE_TAG_BY_ID = "DELETE FROM TAGS WHERE ID=?";
	
	@Override
	public int getTagId(String key, String value) {
		
		try {
			
			// Try to select the tag id from the table.  If the tag is not found, the result will be -1.
			return getJdbcTemplate().queryForObject(SELECT_TAG_ID, new Object[] { key, value }, Integer.class);
		} catch (EmptyResultDataAccessException erdae) {
			
			// Means the tag did not exist.
			return -1;
		}
	}
	
	@Override
	public String getTagById(int tag_id) {
		
		try {
			
			// Try to select the tag value from the table.  If the tag is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_TAG_BY_ID, new Object[] { tag_id }, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			
			// Means the tag did not exist.
			return null;
		}
	}

	@Override
	public boolean insertTag(String key, String value) {
		
		int rowsUpdated = getJdbcTemplate().update(INSERT_TAG, key, value);
		
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

}