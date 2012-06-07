package org.bluemagic.config.service.repository;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.service.ServiceTag;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.PropertiesTagDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.bluemagic.config.service.dao.impl.helper.PropertyDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatabaseRepositoryTest {

	private DatabaseRepository repository;
	
	@Before
	public void setUp() {
		
		repository = new DatabaseRepository();
	}
	
	@Test
	public void testGetPropertyDetails() {
		
		String baseUrl = "http://www.bluemagicsource.org/";
		repository.setBaseUrl(baseUrl);
		repository.setPropertiesDao(new StubPropertiesDao());
		repository.setPropertiesTagDao(new StubPropertiesTagDao());
		
		URI key = null;
		
		try {
			
			key = new URI("abc/def?tags=123,456");
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
		
		PropertyDetails details = repository.getPropertyDetails(key);
		
		System.out.println(details);
		System.out.println(details.getProperty());
		System.out.println(details.getTagDetails());
		
		List<String> uris = new ArrayList<String>();
		uris.add("http://www.bluemagicsource.org/tags/0");
		uris.add("http://www.bluemagicsource.org/tags/2");
		uris.add("http://www.bluemagicsource.org/tags/4");
		
		for (URI uri : details.getTagDetails()) {
			
			Assert.assertTrue(uris.contains(uri.toString()));
		}
	}
	
	@Test
	public void testGetCompeletePropertyDetails() {
		
		String baseUrl = "http://www.bluemagicsource.org/";
		repository.setBaseUrl(baseUrl);
		repository.setPropertiesDao(new StubPropertiesDao());
		repository.setPropertiesTagDao(new StubPropertiesTagDao());
		
		URI key = null;
		
		try {
			
			key = new URI("abc/def?tags=123,456");
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
		
		CompletePropertyDetails details = repository.getCompletePropertyDetails(key);
		
		System.out.println(details);
		System.out.println(details.getProperty());
		System.out.println(details.getTagDetails());
		System.out.println(details.getAttributes());
		
		List<String> uris = new ArrayList<String>();
		uris.add("http://www.bluemagicsource.org/tags/0");
		uris.add("http://www.bluemagicsource.org/tags/2");
		uris.add("http://www.bluemagicsource.org/tags/4");
		
		for (URI uri : details.getTagDetails()) {
			
			Assert.assertTrue(uris.contains(uri.toString()));
		}
		
		List<String> expectedValues = new ArrayList<String>();
		expectedValues.add("service:creationUser=sean");
		expectedValues.add("service:odometer=12");
		expectedValues.add("service:lastAccessedUser=dan");
		expectedValues.add("service:lastModifiedUser=jack");
		
		List<Tag> tags = (List<Tag>) details.getAttributes();
		
		for (Tag tag : tags) {
			
			Assert.assertTrue(expectedValues.contains(tag.toString()));
		}
	}
	
	@Test
	public void test() {
		
		ServiceTag serviceTag = new ServiceTag("odometer", "1");
		
		System.out.println(serviceTag);
		
		ServiceTag serviceTag2 = new ServiceTag("creationUser", "stdobbe");
		
		System.out.println(serviceTag2);
	}
	
	private class StubPropertiesDao implements PropertiesDao {

		@Override
		public String getPropertyValue(String propertyKey) {
			throw new UnsupportedOperationException();
		}

		@Override
		public PropertyDto getProperty(String key) {
			
			PropertyDto serviceProperty = new PropertyDto();
			serviceProperty.setId(1);
			
			String original = null;
			int query = key.indexOf("?");
			
			if (query > -1) {
				
				original = key.substring(0, query);
			} else {
				
				original = key;
			}
			
			URI originalURI = null;
			URI locatedURI = null;
			
			try {
				
				originalURI = new URI(original);
				locatedURI = new URI(key);
			} catch (Throwable t) {
				
				throw new RuntimeException(t.getMessage(), t);
			}
			
			LocatedProperty property = new LocatedProperty(originalURI, locatedURI, "abc", StubPropertiesDao.class);
			serviceProperty.setProperty(property);
			
			return serviceProperty;
		}

		@Override
		public CompletePropertyDto getCompleteProperty(String key) {
			
			CompletePropertyDto serviceProperty = new CompletePropertyDto();
			serviceProperty.setId(1);
			
			String original = null;
			int query = key.indexOf("?");
			
			if (query > -1) {
				
				original = key.substring(0, query);
			} else {
				
				original = key;
			}
			
			URI originalURI = null;
			URI locatedURI = null;
			
			try {
				
				originalURI = new URI(original);
				locatedURI = new URI(key);
			} catch (Throwable t) {
				
				throw new RuntimeException(t.getMessage(), t);
			}
			
			LocatedProperty property = new LocatedProperty(originalURI, locatedURI, "abc", StubPropertiesDao.class);
			serviceProperty.setProperty(property);
			
			// Set the service tags
			Collection<Tag> attributes = new ArrayList<Tag>();
			
			String creationUser = "sean";
			if (creationUser != null) {
				
				ServiceTag creationUserTag = new ServiceTag("creationUser", creationUser);
				attributes.add(creationUserTag);
			}
//			
//			Timestamp creationDatetime = rs.getTimestamp("CREATION_DATETIME");
//			if (creationDatetime != null) {
//				
//				ServiceTag creationDatetimeTag = new ServiceTag("creationDateTime", creationDatetime.toString());
//				attributes.add(creationDatetimeTag);
//			}
			
			int odometer = 12;
			ServiceTag odometerTag = new ServiceTag("odometer", Integer.toString(odometer));
			attributes.add(odometerTag);
			
			String lastAccessedUser = "dan";
			if (lastAccessedUser != null) {
				
				ServiceTag lastAccessedUserTag = new ServiceTag("lastAccessedUser", lastAccessedUser);
				attributes.add(lastAccessedUserTag);
			}
			
//			Timestamp lastAccessedDatetime = rs.getTimestamp("LAST_ACCESSED_DATETIME");
//			if (lastAccessedDatetime != null) {
//				
//				ServiceTag lastAccessedDatetimeTag = new ServiceTag("lastAccessedDatetime", lastAccessedDatetime.toString());
//				attributes.add(lastAccessedDatetimeTag);
//			}
			
			String lastModifiedUser = "jack";
			if (lastModifiedUser != null) {
				
				ServiceTag lastModifiedUserTag = new ServiceTag("lastModifiedUser", lastModifiedUser);
				attributes.add(lastModifiedUserTag);
			}
//			
//			Timestamp lastModifiedDatetime = rs.getTimestamp("LAST_MODIFIED_DATETIME");
//			if (lastModifiedDatetime != null) {
//				
//				ServiceTag lastModifiedDatetimeTag = new ServiceTag("lastModifiedDatetime", lastModifiedDatetime.toString());
//				attributes.add(lastModifiedDatetimeTag);
//			}
			
			
			serviceProperty.setAttributes(attributes);
			
			return serviceProperty;
		}

		@Override
		public boolean insertProperty(String propertyKey, String propertyValue) {
			// TODO Auto-generated method stub
			return false;
		}
		
	}
	
	private class StubPropertiesTagDao implements PropertiesTagDao {

		@Override
		public List<Integer> getTagIds(int propertyId) {
			
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(0);
			ids.add(2);
			ids.add(4);
			
			return ids;
		}
		
	}
}
