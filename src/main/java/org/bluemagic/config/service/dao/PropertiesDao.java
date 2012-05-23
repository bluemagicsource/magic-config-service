package org.bluemagic.config.service.dao;

import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;

public interface PropertiesDao {

	public String getPropertyValue(String propertyKey);

	public PropertyDetails getPropertyDetails(String propertyWithTags);

	public CompletePropertyDetails getCompletePropertyDetails(String propertyWithTags);
}
