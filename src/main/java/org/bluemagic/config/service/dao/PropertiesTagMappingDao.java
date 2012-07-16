package org.bluemagic.config.service.dao;

import java.util.Collection;

import org.bluemagic.config.api.tag.Tag;

public interface PropertiesTagMappingDao {

	public boolean insertPropertyToTagMapping(int propertyId, int tagId);

	public Collection<Tag> getTagsByProperty(String propertyKey);

}