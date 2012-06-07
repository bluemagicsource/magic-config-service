package org.bluemagic.config.service.utils;

import java.net.URI;

import org.bluemagic.config.api.property.LocatedProperty;

public class PropertyUtils {

	public static LocatedProperty appendBase(LocatedProperty locatedProperty, String baseUrl) {
		
		try {
			
			// The base property without tags.
			String original = locatedProperty.getOriginal().toString();
			URI originalKey = null;
			
			if (baseUrl.endsWith("/")) {
				
				originalKey = new URI(baseUrl + original);
			} else {
				
				originalKey = new URI(baseUrl + "/" + original);
			}
			
			// The found property
			String found = locatedProperty.getLocation().toString();
			URI foundKey = null;
			
			if (baseUrl.endsWith("/")) {
				
				foundKey = new URI(baseUrl + found);
			} else {
				
				foundKey = new URI(baseUrl + "/" + found);
			}
			
			return new LocatedProperty(originalKey, foundKey, locatedProperty.getValue(), locatedProperty.getLocatorClass());
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
	}
}
