package org.bluemagic.config.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bluemagic.config.service.dao.TagDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TagDaoJdbcImpl extends JdbcDaoSupport implements TagDao {

	private static final String SELECT_TAG_ID = "SELECT ID FROM TAGS WHERE KEY=? AND VALUE=? AND TYPE=?";

	private static final String SELECT_TAG_BY_ID = "SELECT VALUE FROM TAGS WHERE ID=?";

	private static final String INSERT_TAG = "INSERT INTO TAGS (KEY,VALUE,TYPE) VALUES (?, ?, ?)";

	private static final String DELETE_TAG_BY_ID = "DELETE FROM TAGS WHERE ID=?";

	/**
	 * Get a specific tag id from the tag table by using the key value pair
	 * 
	 * @param key
	 *            tag key
	 * @param value
	 *            tag value
	 * @param type
	 *            tag type can be (public, private, user defined value)
	 * @return int tag id or -1 if tag does not exist
	 */
	@Override
	public int getTagId(String key, String value, String type) {

		try {

			// Try to select the tag id from the table. If the tag is not found,
			// the result will be -1.
			return getJdbcTemplate().queryForObject(SELECT_TAG_ID,
					new Object[] { key, value, type }, Integer.class);
		} catch (EmptyResultDataAccessException erdae) {

			// Means the tag did not exist.
			return -1;
		}
	}

	/**
	 * Get a specific tag from the tag table by using the tag id
	 */
	@Override
	public String getTagById(int tag_id) {

		try {

			// Try to select the tag value from the table. If the tag is not
			// found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_TAG_BY_ID,
					new Object[] { tag_id }, String.class);
		} catch (EmptyResultDataAccessException erdae) {

			// Means the tag did not exist.
			return null;
		}
	}

	/**
	 * Insert a tag into the tag table using the specified key and value
	 * @return id of newly inserted tag or -1 if unable to insert tag
	 */
	@Override
	public int insertTag(final String key, final String value, final String type) {
		int id = -1;
		
		// Used to keep track of auto-generated tag id
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int rowsUpdated = getJdbcTemplate().update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(
	            Connection connection) throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(INSERT_TAG);
	                ps.setString(1, key);
	                ps.setString(2, value);
	                ps.setString(3, type);
	                return ps;
	            }
	        }, keyHolder);
		
		if (rowsUpdated == 1) {
			// Row was inserted, get auto-generated tag id
			id = keyHolder.getKey().intValue();
		} 
		
		return id;
	}

	/**
	 * Delete a tag based on specified tag id
	 */
	@Override
	public boolean deleteTagById(int id) {
		boolean success = false;
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_TAG_BY_ID, id);

		if (rowsUpdated == 1) {
			success = true;
		} 
		
		return success;
	}

}
