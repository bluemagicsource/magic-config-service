package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;
import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class HistoricalPropertiesDaoJdbcImpl extends JdbcDaoSupport implements HistoricalPropertiesDao {


	private static final String SELECT_HISTORICAL_PROPERTIES_VALUE = "SELECT VALUE FROM HISTORICAL PROPERTIES WHERE KEY=?";
	private static final String INSERT_HISTORICAL_PROPERTIES_VALUE = "INSERT INTO HISTORICAL PROPERTIES (KEY, VALUE) VALUES (?,?)";
	
	@Override
	public boolean inserthistoricalproperty(String historicalpropertyKey, String historicalpropertyValue) {
	    
	    int rowsUpdated = getJdbcTemplate().update(INSERT_HISTORICAL_PROPERTIES_VALUE, historicalpropertyKey, historicalpropertyValue);
	    if (rowsUpdated == 1) {
		return true;
	    } else {
		return false;
	    }
	}
	

@Override
	public String gethistoricalpropertyvalue(String historicalpropertyKey){

		try{
			// Try to select the HistoricalpropertyKey from the table. If the propertyKey is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_HISTORICAL_PROPERTIES_VALUE, new Object[] { historicalpropertyKey }, String.class);
		} catch (Throwable t) {

			// Means the HistoricalpropertyKey did not exist.
			return null;
		}
	}

	@Override
	public PropertyDetails getPropertyDetails(String historicalpropertyWithTags) {
		throw new UnsupportedOperationException();
	}

	@Override
	public CompletePropertyDetails getCompletePropertyDetails(String historicalpropertyWithTags) {
		throw new UnsupportedOperationException();
	}
}
