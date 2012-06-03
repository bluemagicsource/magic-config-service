package org.bluemagic.config.service.dao;

public interface PropertiesTagMappingDao {

	public String getPropertyValue(String propertyKey);
	
	public String getTagById(int tag_id, int property_id);
	
	public boolean deleteTagById(int tag_id);
}