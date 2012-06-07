package org.bluemagic.config.service.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDtoRowMapper;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDtoRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesDaoJdbcImpl extends JdbcDaoSupport implements PropertiesDao {

	private static final Log LOG = LogFactory.getLog(PropertiesDaoJdbcImpl.class);
	
	private static final String SELECT_PROPERTY = "SELECT ID, KEY, VALUE FROM PROPERTIES WHERE KEY=?";
	
	private static final String SELECT_COMPLETE_PROPERTY = "SELECT ID, KEY, VALUE, VERSION, "
			                                             + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
			                                             + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
			                                             + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
			                                             + "FROM PROPERTIES WHERE KEY=?";
	
	private static final String INSERT_PROPERTY_VALUE = "INSERT INTO PROPERTIES " +
			"(KEY, VALUE, CREATION_USER, CREATION_DATETIME, LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER) " +
			"VALUES (?, ?, ?, sysdate, sysdate, ?)";
	
	private static final String UPDATE_LAST_ACCESSED_DATE_AND_ODOMETER = "UPDATE PROPERTIES " +
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
	public boolean insertProperty(String propertyKey, String propertyValue, String user) {

		int rowsUpdated = getJdbcTemplate().update(INSERT_PROPERTY_VALUE,
				propertyKey, propertyValue, user, user);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getPropertyValue(String propertyKey, String user) {

		try {
			// Select property id and value based on property key
			// RowMap fields from property table to property object
			Property property = getJdbcTemplate().queryForObject(SELECT_PROPERTY,
					new Object[] { propertyKey }, 
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
