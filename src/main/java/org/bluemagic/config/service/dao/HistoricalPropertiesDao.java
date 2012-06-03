package org.bluemagic.config.service.dao;


import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;
import java.lang.String;  	

public interface HistoricalPropertiesDao {

	 public boolean inserthistoricalproperty(String historicalpropertyKey, String historicalpropertyValue);

	    public String gethistoricalpropertyvalue(String historicalpropertyKey);

	    public PropertyDetails getPropertyDetails(String historicalpropertyWithTags);

	    public CompletePropertyDetails getCompletePropertyDetails(String historicalpropertyWithTags);
	
    // Delete method is omitted
	
}