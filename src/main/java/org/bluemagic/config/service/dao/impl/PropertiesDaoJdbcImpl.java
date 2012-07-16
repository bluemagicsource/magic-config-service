package org.bluemagic.config.service.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDtoRowMapper;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDtoRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class PropertiesDaoJdbcImpl extends JdbcDaoSupport implements PropertiesDao {

	private static final Log LOG = LogFactory.getLog(PropertiesDaoJdbcImpl.class);
	
	private static final String SELECT_PROPERTY = "SELECT ID, KEY, VALUE FROM PROPERTIES WHERE KEY=?";
	
	private static final String SELECT_COMPLETE_PROPERTY = "SELECT ID, KEY, VALUE, VERSION, "
			                                             + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
			                                             + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
			                                             + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
			                                             + "FROM PROPERTIES WHERE KEY=?";
	
	private static final String DELETE_PROPERTY_BY_ID = "DELETE FROM PROPERTIES WHERE ID=?";
	
	
	private static final String UPDATE_PROPERTY_BY_ID = "UPDATE PROPERTIES " +
			"SET KEY = ?, " +
			"VALUE = ?, " +
			"LAST_MODIFIED_DATETIME = sysdate, " +
			"LAST_MODIFIED_USER = ? " +
			"WHERE ID = ?";
				
	
	private static final String INSERT_PROPERTY_VALUE = "INSERT INTO PROPERTIES " +
			"(KEY, VALUE, CREATION_USER, CREATION_DATETIME, LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER) " +
			"VALUES (?, ?, ?, sysdate, sysdate, ?)";
	
	private static final String SELECT_ODOMETER = "SELECT ODOMETER FROM PROPERTIES WHERE ID=?";
	
	private static final String UPDATE_LAST_ACCESSED_DATE_AND_ODOMETER = "UPDATE PROPERTIES " +
			"SET LAST_ACCESSED_DATETIME = sysdate, " +
			"LAST_ACCESSED_USER = ?, " +
			"ODOMETER = ? " +
			"WHERE ID = ?";
	
	

	/**
	 * Inserts a new property into the property table 
	 * Properties are made up of key and value pairs
	 * 
	 * The creation user/date and last modified user/date will be automatically updated
	 * @return id of newly inserted row or -1 if unable to insert row
	 */
	@Override
	public int insertProperty(final String key, final String value, final String user) {
		int id = -1;
		
		// Used to keep track of auto-generated property id
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		int rowsUpdated = getJdbcTemplate().update(new PreparedStatementCreator() {
	        public PreparedStatement createPreparedStatement(
	            Connection connection) throws SQLException {
	                PreparedStatement ps = connection.prepareStatement(INSERT_PROPERTY_VALUE);
	                ps.setString(1, key);
	                ps.setString(2, value);
	                ps.setString(3, user);
	                ps.setString(4, user);
	                return ps;
	            }
	        }, keyHolder);
		
		if (rowsUpdated == 1) {
			// Row was inserted, get auto-generated property id
			id = keyHolder.getKey().intValue();
		} 
		
		return id;
	}
	
	/**
	 * Updates a property based on property id 
	 * @return true if row has been updated
	 */
	@Override
	public boolean updatePropertyById(int id, String key, String value, String user) {
		boolean success = false;
		
		int rowsUpdated = getJdbcTemplate().update(UPDATE_PROPERTY_BY_ID,
				 key, value, user, id);
		
		if (rowsUpdated == 1) {
			success = true;
		} 
		
		return success;
	}
	
	/**
	 * Delete a property based on property id 
	 * @return true if row has been deleted
	 */
	@Override
	public boolean deletePropertyById(int id) {
		boolean success = false;
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_PROPERTY_BY_ID, id);
		
		if (rowsUpdated == 1) {
			success = true;
		} 
		
		return success;
	}

	/**
	 * Gets a property value based on property key
	 * 
	 * Automatically updated last accessed date and increments odometer
	 * 
	 * @TODO Handle error if updating last accessed date and odometer fails -DB
	 */
	@Override
	public String getPropertyValue(String key, String user) {

		try {
			// Select property id and value based on property key
			// Property id will be used to update odometer and last accessed date
			PropertyDto property = getJdbcTemplate().queryForObject(SELECT_PROPERTY, 
																	new Object[] { key }, 
																	new PropertyDtoRowMapper());
			
			// If result is not null, update last accessed date and odometer
			if (property != null) {
				propertyHasBeenAccessed (property.getId(), user);
			}
			
			return (String) property.getProperty().getValue();
		} catch (Throwable t) {

			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the key did not exist.
			return null;
		}
	}
	
	/**
	 * Gets brief details about the property based on property key.
	 * Can access property id, key, value
	 * @return PropertyDto object or null if not found
	 */
	@Override
	public PropertyDto getProperty(String key) {
		
		try {
			
			return getJdbcTemplate().queryForObject(SELECT_PROPERTY, new Object[] { key }, new PropertyDtoRowMapper());
		} catch (Throwable t) {
			
			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the key was not found.
			return null;
		}
	}

	/**
	 * Gets complete details about the property based on property key.
	 * @return CompletePropertyDto object or null if not found
	 */
	@Override
	public CompletePropertyDto getCompleteProperty(String key) {
		
		try {
			
			return getJdbcTemplate().queryForObject(SELECT_COMPLETE_PROPERTY, 
					                                new Object[] { key }, 
					                                new CompletePropertyDtoRowMapper());
		} catch (Throwable t) {
			
			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the key was not found.
			return null;
		}
	}
	
	/**
	 * When a property has been accessed update the last accessed date,
	 * last accessed user, and the odometer
	 * @param id - property id
	 * @return true if row has been updated
	 */

	private boolean propertyHasBeenAccessed (int id, String user) {
		boolean success = false;
		
		// Get the current odometer
		int odometer = getJdbcTemplate().queryForInt(SELECT_ODOMETER, new Object[] { id });
		
		int rowsUpdated = getJdbcTemplate().update(UPDATE_LAST_ACCESSED_DATE_AND_ODOMETER,
													user, 
													odometer+1,
													id);
		
		if (rowsUpdated == 1) {
			success = true;
		}
		
		return success;
	}
	
}
