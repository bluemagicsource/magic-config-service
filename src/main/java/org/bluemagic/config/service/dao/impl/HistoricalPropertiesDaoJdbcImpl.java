package org.bluemagic.config.service.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDtoRowMapper;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDtoRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class HistoricalPropertiesDaoJdbcImpl extends JdbcDaoSupport implements HistoricalPropertiesDao {

	private static final Log LOG = LogFactory.getLog(HistoricalPropertiesDaoJdbcImpl.class);
	
	private static final String SELECT_PROPERTY = "SELECT ID, KEY, VALUE FROM HISTORICAL_PROPERTIES WHERE KEY=?";
	
	private static final String SELECT_COMPLETE_PROPERTY = "SELECT ID, KEY, VALUE, "
			                                             + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
			                                             + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
			                                             + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
			                                             + "FROM HISTORICAL_PROPERTIES WHERE KEY=?";
	
	private static final String INSERT_HISTORICAL_PROPERTY_VALUE = "INSERT INTO HISTORICAL_PROPERTIES (ID, KEY, VALUE,"
			                                             + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
			                                             + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
			                                             + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER) "
			                                             + "SELECT ID, KEY, VALUE,"
					                                     + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
					                                     + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
					                                     + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
					                                     + "FROM PROPERTIES WHERE KEY=?";
	

	/**
	 * Inserts an existing property into the historical properties.
	 * @return false if unable to insert row
	 */
	@Override
	public boolean insertHistoricalProperty(String historicalPropertyKey) {
		boolean success = false;
		
		int rowsUpdated = getJdbcTemplate().update(INSERT_HISTORICAL_PROPERTY_VALUE, historicalPropertyKey);
		
		if (rowsUpdated == 1) {
			success = true;
		} 
		
		return success;
	}

	/**
	 * Get a historical property value based on historical property key
	 */
	@Override
	public String getHistoricalPropertyValue(String historicalPropertyKey, String user) {

		try {
			PropertyDto property = getJdbcTemplate().queryForObject(SELECT_PROPERTY, 
					new Object[] { historicalPropertyKey }, 
					new PropertyDtoRowMapper());
			
			return (String) property.getProperty().getValue();
		} catch (Throwable t) {

			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the propertyKey did not exist.
			return null;
		}
	}

	/**
	 * Get brief details about the historical property based on historical property key.
	 * Can access property id, key, value
	 * @return PropertyDto object or null if not found
	 */
	@Override
	public PropertyDto getHistoricalProperty(String historicalPropertyKey) {
		
		try {
			
			return getJdbcTemplate().queryForObject(SELECT_PROPERTY, 
													new Object[] { historicalPropertyKey }, 
													new PropertyDtoRowMapper());
		} catch (Throwable t) {
			
			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the key was not found.
			return null;
		}
	}

	/**
	 * Get complete details about the historical property based on historical property key.
	 * @return CompletePropertyDto object or null if not found
	 */
	@Override
	public CompletePropertyDto getCompleteHistoricalProperty(String historicalPropertyKey) {
		
		try {
			
			return getJdbcTemplate().queryForObject(SELECT_COMPLETE_PROPERTY, 
					                                new Object[] { historicalPropertyKey }, 
					                                new CompletePropertyDtoRowMapper());
		} catch (Throwable t) {
			
			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the key was not found.
			return null;
		}
	}
	
}

