package org.bluemagic.config.service.dao;

public interface PropertiesTagMappingDao {

	public boolean insert(int property_id, int tag_id);
	
	public boolean deletebyPropertyandtagid(int property_id, int tag_id);
	
	public boolean deletebyPropertyid(int property_id, int tag_id);
	
	
}