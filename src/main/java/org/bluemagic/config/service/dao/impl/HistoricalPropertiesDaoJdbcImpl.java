package org.bluemagic.config.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDtoRowMapper;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDtoRowMapper;
import org.springframework.jdbc.core.RowMapper;
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
			                                             + "VALUES (SELECT ID, KEY, VALUE,"
					                                     + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
					                                     + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
					                                     + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
					                                     + "FROM PROPERTIES WHERE KEY =?)";
	
	private static final String UPDATE_LAST_ACCESSED_DATE_AND_ODOMETER = "UPDATE HISTORICAL_PROPERTIES " +
			"SET LAST_ACCESSED_DATETIME = sysdate, " +
			"LAST_ACCESSED_USER = ?, " +
			"ODOMETER = ODOMETER+1 " +
			"WHERE ID = ?";

	/**
	 * Inserts a new property into the property table 
	 * Properties are made up of key and value pairs
	 * 
	 * The creation user/date and last modified user/date will be automatically updated
	 */
	@Override
	public boolean insertHistoricalProperty(int historicalId, String historicalPropertyKey, String historicalPropertyValue, java.sql.Timestamp historicalCreationDateTime, String historicalCreationUser, int historicalOdometer, java.sql.Timestamp historicalLastAccessedDateTime, String historicalLastAccessedUser, java.sql.Timestamp historicalLastModifiedDateTime, String historicalLastModifiedUser) {

		int rowsUpdated = getJdbcTemplate().update(INSERT_HISTORICAL_PROPERTY_VALUE, historicalId,
				historicalPropertyKey, historicalPropertyValue, historicalCreationDateTime, historicalCreationUser, historicalOdometer, historicalLastAccessedDateTime, historicalLastAccessedUser, historicalLastModifiedDateTime, historicalLastModifiedUser);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getHistoricalPropertyValue(String historicalPropertyKey, String user) {

		try {
			// Select property id and value based on property key
			// RowMap fields from property table to property object
			Property property = getJdbcTemplate().queryForObject(SELECT_PROPERTY,
					new Object[] { historicalPropertyKey }, 
					new RowMapper<Property>() {
			            public Property mapRow(ResultSet rs, int rowNum) throws SQLException {
			            	Property property = new Property();
			                property.setID(rs.getInt("ID"));
			                property.setValue(rs.getString("VALUE"));
			                return property;
			            }
			        });
			
			// If result is not null, update last accessed date and odometer
			if (property != null) {
				propertyHasBeenAccessed (property.getID(), user);
			}
			
			return property.getValue();
		} catch (Throwable t) {

			if (LOG.isErrorEnabled()) {
				LOG.error(t.getMessage(), t);
			}
			
			// Means the propertyKey did not exist.
			return null;
		}
	}

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
	 * @param propertyID
	 * @return true if row has been updated
	 */
	private boolean propertyHasBeenAccessed (int propertyID, String user) {
		int rowsUpdated = getJdbcTemplate().update(UPDATE_LAST_ACCESSED_DATE_AND_ODOMETER,
				user, propertyID);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Inner class to allow for row mapping with property table
	 */
	private class Property
	{
		private int id;
		private String value;
		
		public int getID() {
            return id;
	    }
	
	    public void setID(int id) {
	            this.id = id;
	    }
	    
	    public String getValue() {
            return value;
	    }
	
	    public void setValue(String value) {
	            this.value = value;
	    }
	 
	}
}

