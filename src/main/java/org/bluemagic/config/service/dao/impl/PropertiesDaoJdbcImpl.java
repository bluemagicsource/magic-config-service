package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesDaoJdbcImpl extends JdbcDaoSupport implements PropertiesDao {

	private static final String SELECT_PROPERTY_VALUE = "SELECT VALUE FROM PROPERTIES WHERE PROPERTYKEY=?";

	@Override
	public String getPropertyValue(String propertyKey){

		try{
			// Try to select the propertyKey from the table. If the propertyKey is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_PROPERTY_VALUE, new Object[] { propertyKey }, String.class);
		} catch {

			// Means the propertyKey did not exist.
			return null;
		}
	}
}
