package org.bluemagic.config.service.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.PropertiesTagDao;
import org.bluemagic.config.service.dao.UserDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.bluemagic.config.service.utils.PropertyUtils;
import org.bluemagic.config.service.utils.TagUtils;

/**
 * The database repository has been designed to interface with our 
 * data acccess layer (DAOs) to perform the various CRUD activities.
 **/
public class DatabaseRepository implements DetailsRepository {

	private PropertiesDao propertiesDao;
	private PropertiesTagDao propertiesTagDao;
	private UserDao userDao;
	private String baseUrl;
	
    /**
     * Not 100% sure what the purpose of this method is so please add documentation
     * either here or on the interface level -- Brian E. Walsh
     *
     * @param  uri - Requested uri
     * @return boolean - true when the incoming uri is supported
     **/
	@Override
	public boolean supports(URI uri) {
		return true;
	}

    /**
     * The put method inserts a new data record or updates
     * an existing data record's value
     *
     * @param  key - Unique uri (note that our URIs should already
     *         be normalized prior to reaching this interface.
     * @param  value -
     * @return return ?
     **/
	@Override
	public Object put(URI key, Object value) {

		// Parse out all the tags...
		Map<String, String> tags = TagUtils.parseTags(key);
		
		String user = getUser(tags);

		if (user != null) {
			
			// Check to see if the user exists.  If they don't, then add them to the
			// user's table.
			int userId = userDao.getUserId(user);
			
			// If the user doesn't exist yet, insert them into the user's table
			if (userId == -1) {
				
				userDao.insertUser(user);
			}
			
			// Remove user from tags...
			removeUserFromTags(tags);
		}
		
		// Go through and check if the tags exist yet.
		// If the tags don't exist insert them into the tags table.
		
		
		// Insert/Update property in the properties table.
		
		
		// Add the appropriate keys to the PROPERTIES_TAGS_MAPPING
		
		
		return null;
	}

    /**
     * @param  key - Unique uri
     * @return Object The current value corresponding to the key or MissingProperty
     *         if the value does not exist in the repository
     **/
	@Override
	public Object get(URI key) {
		
		// Parse out all the tags...
		Map<String, String> tags = TagUtils.parseTags(key);
		
		String user = getUser(tags);
		
		String propertyWithTags = getNormalizedProperty(key, tags);
		
		// Go to the database and try to retrieve this property.
		String result = propertiesDao.getPropertyValue(propertyWithTags, user);
		
		return result;
	}

	@Override
	public PropertyDetails getPropertyDetails(URI key) {
		
		// Parse out all the tags...
		Map<String, String> tags = TagUtils.parseTags(key);
		
		String propertyWithTags = getNormalizedProperty(key, tags);
		
		// Go to the database and try to retrieve the property details.
		PropertyDto serviceProperty = propertiesDao.getProperty(propertyWithTags);
		
		// Now use the ID from the properties table to get all the tag id's associated with it.
		int propertyId = serviceProperty.getId();
		List<Integer> tagIds = propertiesTagDao.getTagIds(propertyId);
		
		
		// Do we want to fail everything if we can't make one of the tags?
		try {
			
			Collection<URI> tagDetails = new ArrayList<URI>();
			
			// Create the Tag URI's.   baseUrl + "/tags/" + id
			for (Integer tagId : tagIds) {

				tagDetails.add(new URI(baseUrl + "tags/" + tagId.toString()));
			}
			
			// Add the baseUrl to the property key's.
			LocatedProperty locatedProperty = serviceProperty.getProperty();			
			LocatedProperty property = PropertyUtils.appendBase(locatedProperty, baseUrl + "properties/");
			
			PropertyDetails result = new PropertyDetails(property, tagDetails);
			
			return result;
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}		
	}

	@Override
	public CompletePropertyDetails getCompletePropertyDetails(URI key) {
		
		// Parse out all the tags...
		Map<String, String> tags = TagUtils.parseTags(key);
		
		String propertyWithTags = getNormalizedProperty(key, tags);
		
		// Go to the database and try to retrieve the property details.
		CompletePropertyDto completePropertyDto = propertiesDao.getCompleteProperty(propertyWithTags);
		
		// Now use the ID from the properties table to get all the tag id's associated with it.
		int propertyId = completePropertyDto.getId();
		List<Integer> tagIds = propertiesTagDao.getTagIds(propertyId);
		
		
		// Do we want to fail everything if we can't make one of the tags?
		try {
			
			Collection<URI> tagDetails = new ArrayList<URI>();
			
			// Create the Tag URI's.   baseUrl + "/tags/" + id
			for (Integer tagId : tagIds) {

				tagDetails.add(new URI(baseUrl + "tags/" + tagId.toString()));
			}
			
			// Add the baseUrl to the property key's.
			LocatedProperty locatedProperty = completePropertyDto.getProperty();			
			LocatedProperty property = PropertyUtils.appendBase(locatedProperty, baseUrl + "properties/");
			
			CompletePropertyDetails result = new CompletePropertyDetails(property, tagDetails, completePropertyDto.getAttributes());
			
			return result;
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
	}
	
	private String getNormalizedProperty(URI key, Map<String, String> tags) {

		// Remove user
		removeUserFromTags(tags);
		
		// Get the property without the tags.
		String propertyWithoutTags = TagUtils.getPropertyWithoutTags(key, baseUrl);
		
		// Add reordered tags back to the property.  This will normalize all the tags
		// so they are in alphabetical order.
		String propertyWithTags = TagUtils.reassemble(propertyWithoutTags, tags);
		return propertyWithTags;
	}

	private void removeUserFromTags(Map<String, String> tags) {
		
		// Grab the single tags, so we can try to get the user from it.
		String singleTags = tags.get("tags");
		
		if (singleTags != null) {
			
			// Remove the user from the tags
			String singleTagsWithoutUser = TagUtils.removeUserFromSingleTags(singleTags);
			
			if (singleTagsWithoutUser != null && !singleTagsWithoutUser.trim().isEmpty()) {
				
				tags.put("tags", singleTagsWithoutUser);
			} else {
				
				// User was the only tag, and since it's been stripped out, we can remove the single tags.
				tags.remove("tags");
			}
		}
	}
	
	private String getUser(Map<String, String> tags) {
		
		String user = null;
		
		// Grab the single tags, so we can try to get the user from it.
		String singleTags = tags.get("tags");
		
		if (singleTags != null) {
			
			// If there is a user in the tags, get it out.
			user = TagUtils.parseUserFromSingleTags(singleTags);
		}
		
		return user;
	}

    /**
     * Removes the item from the repository.
     *
     * @param  key - Unique uri
     * @return Object value removed
     **/
	@Override
	public Object remove(URI key) {
		// TODO Auto-generated method stub
		return null;
	}

    /**
     * Clears the entire repository.
     **/
	@Override
	public void clear() {
        throw new UnsupportedOperationException("Database repository implementation does not support clear");
    }
	
    /**
     * Returns the total number of properties in the repository
     **/
	@Override
	public int size() {
        throw new UnsupportedOperationException("Database repository implementation does not support clear");
    }

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		
		if (baseUrl.endsWith("/")) {
			
			this.baseUrl = baseUrl;
		} else {
			this.baseUrl = baseUrl + "/";
		}
	}

	public PropertiesDao getPropertiesDao() {
		return propertiesDao;
	}

	public void setPropertiesDao(PropertiesDao propertiesDao) {
		this.propertiesDao = propertiesDao;
	}

	public PropertiesTagDao getPropertiesTagDao() {
		return propertiesTagDao;
	}

	public void setPropertiesTagDao(PropertiesTagDao propertiesTagDao) {
		this.propertiesTagDao = propertiesTagDao;
	}
}
