package org.bluemagic.config.service.dao;

public interface TagDao {

	public int getTagId(String key, String value);
	
	public String getTagById(int tag_id);
	
	public boolean insertTag(String key, String value);
	
	public boolean deleteTagById(int tag_id);
}