package org.bluemagic.config.service.dao;

import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;

public interface HistoricalPropertiesDao {

	public String getpropertiesById(int property_id);
	
	public boolean insertproperty(String key, String value);
	
	public String getPropertyValue(String propertyKey);

    public PropertyDetails getPropertyDetails(String propertyWithTags);

    public CompletePropertyDetails getCompletePropertyDetails(String propertyWithTags);
	
    // Delete method is omitted
	
}