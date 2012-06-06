package org.bluemagic.config.service.dao;

public interface PropertiesTagMappingDao {

	public boolean insertTag(String key, String value);
	
	public boolean insertProperty(String propertyKey, String propertyValue);
	
	public String getPropertyValue(String propertyKey);
	
	public String getTagById(int tag_id);
	
	public boolean deleteTagById(int tag_id);
}