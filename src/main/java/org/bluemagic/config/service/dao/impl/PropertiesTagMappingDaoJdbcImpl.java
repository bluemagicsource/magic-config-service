package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesTagMappingDaoJdbcImpl extends JdbcDaoSupport implements PropertiesTagMappingDao {

	private static final String SELECT_PROPERTY_VALUE = "SELECT VALUE FROM PROPERTIES_TAG_MAPPING WHERE KEY=?";
	
	private static final String INSERT_TAG = "INSERT INTO PROPERTIES_TAG_MAPPING (KEY,VALUE) VALUES (?, ?)";
	
	private static final String INSERT_PROPERTY_VALUE = "INSERT INTO PROPERTIES (KEY, VALUE) VALUES (?,?)";
	
	private static final String SELECT_TAG_BY_ID = "SELECT VALUE FROM PROPERTIES_TAG_MAPPING WHERE ID=?";
	
	private static final String DELETE_TAG_BY_ID = "DELETE FROM PROPERTIES_TAG_MAPPING WHERE ID=?";
	
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
	public boolean insertTag(String key, String value) {
		
		int rowsUpdated = getJdbcTemplate().update(INSERT_TAG, key, value);
		
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
	public String getTagById(int tag_id) {
		
		try {
			
			// Try to select the tag value from the table.  If the tag is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_TAG_BY_ID, new Object[] { tag_id }, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			
			// Means the tag did not exist.
			return null;
		}
	}


	@Override
	public boolean deleteTagById(int tag_id) {
		
		int rowsUpdated = getJdbcTemplate().update(DELETE_TAG_BY_ID, tag_id);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}

}
