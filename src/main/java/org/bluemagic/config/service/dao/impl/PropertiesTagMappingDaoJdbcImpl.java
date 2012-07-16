package org.bluemagic.config.service.dao.impl;

import java.util.Collection;

import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesTagMappingDaoJdbcImpl extends JdbcDaoSupport implements PropertiesTagMappingDao {

	private static final String MAP_TAG = "INSERT INTO PROPERTIES_TAG_MAPPING (PROPERTY_ID, TAG_ID) VALUES (?, ?)";
    
	/**
	 * Inserts a single property to tag mapping based on propertyId and tagId
	 */
	@Override
	public boolean insertPropertyToTagMapping(int propertyId, int tagId) {
		boolean success = false;
		
		int rowsUpdated = getJdbcTemplate().update(MAP_TAG, propertyId, tagId);
		
		if (rowsUpdated == 1) {
			success = true;
		} 
		
		return success;
	}
	
	/**
	 * Get a list of tags associated with a property
	 */
    @Override
    public Collection<Tag> getTagsByProperty(String propertyKey){
    	// Needs to be implemented
    	throw new UnsupportedOperationException();
    }

}