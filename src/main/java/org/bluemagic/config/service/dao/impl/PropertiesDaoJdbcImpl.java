package org.bluemagic.config.service.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDtoRowMapper;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDtoRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

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
			"VALUE = ?," +
			"LAST_MODIFIED_DATETIME = sysdate, " +
			"LAST_MODIFIED_USER = ?, " +
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
	 */
	@Override
	public boolean insertProperty(String key, String value, String user) {

		int rowsUpdated = getJdbcTemplate().update(INSERT_PROPERTY_VALUE,
				key, value, user, user);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
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
	/*
	@Override
	public boolean updatePropertyById(int property_id, String key, String value, String user) {
		int rowsUpdated = getJdbcTemplate().update(UPDATE_PROPERTY_BY_ID,
				 key, value, user, property_id);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	@Override
	public boolean deletePropertyById(int property_id) {
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_PROPERTY_BY_ID, property_id);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
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
