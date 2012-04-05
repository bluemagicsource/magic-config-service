package org.bluemagic.config.service;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

import org.bluemagic.config.util.UriUtils;
import org.springframework.beans.factory.annotation.Required;

/**
 * Allows us to read properties from a file.  By default, you can only put in
 * the base property and it's value.  However, if you would like to add tagged
 * properties into your files, then you need to set the boolean 'useTags' and format
 * them properly.
 * 
 * Example:
 * - In your '.properties' file, you can add tags like so:
 *        some/prop=BASE
 *        some/prop?tags~production=PRODUCTION
 *        some/prop?tags~development=DEVELOPMENT
 *        some/prop?system~production,tags~new=NEW-PRODUCTION
 * 
 * The tag values are escaped with a '~'.
 * 
 * NOTE:  This implementation picks up changes to the properties file dynamically.
 */
public class FilePropertyService implements PropertyService {

	private long lastModified;
	private Properties properties;
	private File file;
	private boolean useTags = false;
	
	@Override
	public String getProperty(String property) {
		try {
			// If the properties have never been loaded, or they've been recently updated
			// then load the properties from disk.
			if (this.properties == null || this.lastModified < this.file.lastModified()) {
				this.properties = loadProperties();
			}
			
			if (useTags) {
				return properties.getProperty(property.replace('=', '~'));
			} else {
				return properties.getProperty(property);				
			}
		} catch (Exception e) {
			return null;
		}
	}

	private Properties loadProperties() throws Exception {
		
		Properties properties = new Properties();
		
		// XML FILE
		if (this.file.getAbsolutePath().endsWith(".xml")) {
			properties.loadFromXML(new FileInputStream(file));
		} else {
			// STANDARD PROPERTIES FILE
			properties.load(new FileInputStream(file));
		}
		return properties;
	}

	@Required
	public void setUri(URI uri) {
		if ("file".equals(uri.getScheme())) {
			this.file = new File(uri);
		} else {
			// FILE ON THE CLASSPATH
			String schemeSpecificPart = uri.getSchemeSpecificPart().replace("//", "");
			URL url = this.getClass().getClassLoader().getResource(schemeSpecificPart);							
			this.file = new File(UriUtils.urlToUri(url));
		}
		
		this.lastModified = this.file.lastModified();
	}

	public boolean isUseTags() {
		return useTags;
	}

	public void setUseTags(boolean useTags) {
		this.useTags = useTags;
	}
}
