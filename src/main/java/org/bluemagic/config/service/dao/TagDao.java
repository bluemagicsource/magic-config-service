package org.bluemagic.config.service.dao;

public interface TagDao {

	public String getTagById(int tag_id);
	
	public boolean insertTag(String key, String value);
	
	public boolean updateTagById(int tag_id, String key, String value);
	
	public boolean deleteTagById(int tag_id);
}