package org.bluemagic.config.service.dao;

/**
 * The tag table is a way to keep track of all of the tags. Note that the type should be public or private. 
 * The default value for all tags is public. A private tag is stripped off of the URI to make a unique URI. 
 * Note that some examples of private tags are username and security tags. 
 *
 * Consequently, we do not want to use the type as a way to identify and return the tags (a tag cannot exist as both public and private). 
 **/

public interface TagDao {

	public int getTagId(String key, String value, String type);

	public String getTagById(int tag_id);

	public int insertTag(String key, String value, String type);

	public boolean deleteTagById(int tag_id);
}