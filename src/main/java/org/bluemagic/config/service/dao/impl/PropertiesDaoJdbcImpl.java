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

	private static final String SELECT_PROPERTY_VALUE = "SELECT VALUE FROM PROPERTIES WHERE KEY=?";
	
	private static final String INSERT_PROPERTY_VALUE = "INSERT INTO PROPERTIES (KEY, VALUE) VALUES (?,?)";
	
	private static final String SELECT_PROPERTY = "SELECT ID, KEY, VALUE FROM PROPERTIES WHERE KEY=?";
	
	private static final String SELECT_COMPLETE_PROPERTY = "SELECT ID, KEY, VALUE, VERSION, "
			                                             + "CREATION_USER, CREATION_DATETIME, ODOMETER, "
			                                             + "LAST_ACCESSED_DATETIME, LAST_ACCESSED_USER, "
			                                             + "LAST_MODIFIED_DATETIME, LAST_MODIFIED_USER "
			                                             + "FROM PROPERTIES WHERE KEY=?";

        @Override
	public boolean insertProperty(String propertyKey, String propertyValue) {
	    
	    int rowsUpdated = getJdbcTemplate().update(INSERT_PROPERTY_VALUE, propertyKey, propertyValue);
	    if (rowsUpdated == 1) {
		return true;
	    } else {
		return false;
	    }
	}


	@Override
	public String getPropertyValue(String propertyKey){

		try{
			// Try to select the propertyKey from the table. If the propertyKey is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_PROPERTY_VALUE, new Object[] { propertyKey }, String.class);
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
}
