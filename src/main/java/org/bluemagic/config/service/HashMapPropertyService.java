package org.bluemagic.config.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Very limited implementation of the PropertyService.  Takes in a mapping
 * of property names to property values at construction.  Currently no way
 * to add additional properties to the mapping.
 */
public class HashMapPropertyService implements PropertyService {

	private Map<String, String> propertyTable;
	
	// Allows us to pass in a map via Spring.
	public HashMapPropertyService(Map<String, String> propertyTable) {
		this.propertyTable = propertyTable;
	}
	
	// Sets three default values into the mapping.
	public HashMapPropertyService() {
		this.propertyTable = new HashMap<String, String>();
		this.propertyTable.put("some/prop", "BASE");
		this.propertyTable.put("some/prop?tags=production", "PRODUCTION");
		this.propertyTable.put("some/prop?tags=development", "DEVELOPMENT");
	}
	
	@Override
	public String getProperty(String property) {
		return propertyTable.get(property);
	}
}
