package org.bluemagic.config.service.dao;

import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;

public interface PropertiesDao {

    public boolean insertProperty(String propertyKey, String propertyValue, String user);

    public String getPropertyValue(String propertyKey, String user);

    public PropertyDetails getPropertyDetails(String propertyWithTags);

    public CompletePropertyDetails getCompletePropertyDetails(String propertyWithTags);
}
