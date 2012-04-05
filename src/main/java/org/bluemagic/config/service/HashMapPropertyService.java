package org.bluemagic.config.service;

import java.util.HashMap;
import java.util.Map;

public class HashMapPropertyService implements PropertyService {

	private Map<String, String> propertyTable;
	
	public HashMapPropertyService() {
		propertyTable = new HashMap<String, String>();
		propertyTable.put("some/prop", "BASE");
		propertyTable.put("some/prop?tags=production", "PRODUCTION");
		propertyTable.put("some/prop?tags=development", "DEVELOPMENT");
	}
	
	@Override
	public String getProperty(String property) {
		return propertyTable.get(property);
	}
}
