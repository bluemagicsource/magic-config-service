package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesDaoJdbcImpl extends JdbcDaoSupport implements PropertiesDao {

	private static final String SELECT_PROPERTY_VALUE = "SELECT VALUE FROM PROPERTIES WHERE KEY=?";
	private static final String INSERT_PROPERTY_VALUE = "INSERT INTO PROPERTIES (KEY, VALUE) VALUES (?,?)";

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

			// Means the propertyKey did not exist.
			return null;
		}
	}

	@Override
	public PropertyDetails getPropertyDetails(String propertyWithTags) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletePropertyDetails getCompletePropertyDetails(String propertyWithTags) {
		throw new UnsupportedOperationException();
	}
}
