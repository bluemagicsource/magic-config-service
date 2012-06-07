package org.bluemagic.config.service.dao;

import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;

public interface PropertiesDao {

    public boolean insertProperty(String propertyKey, String propertyValue, String user);

    public String getPropertyValue(String propertyKey, String user);

	public PropertyDto getProperty(String key);

	public CompletePropertyDto getCompleteProperty(String key);
}
