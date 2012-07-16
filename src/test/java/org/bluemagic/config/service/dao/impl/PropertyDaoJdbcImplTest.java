package org.bluemagic.config.service.dao.impl;

import java.util.Collection;

import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.service.ServiceTag;
import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.impl.helper.CompletePropertyDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertyDaoJdbcImplTest {

	private PropertiesDao propertiesDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET PROPERTY DAO FROM CONTEXT
		propertiesDao = ctx.getBean(PropertiesDaoJdbcImpl.class);
	}
	
	@Test
	public void testPropertyDoesntExist() {
		
		String value = propertiesDao.getPropertyValue("test","testUser");
		
		// MAKE SURE THE PROPERTY DOESN'T EXIST
		Assert.assertNull(value);
	}
/*
	@Test
	public void testupdatePropertyById() {
		String value = propertiesDao.insertProperty("system", "abc", "testUser", "P1");
		
		
		// MAKE SURE THE PROPERTY DOESN'T EXIST
		Assert.assertNull(value);
	}
*/
	@Test
	public void testGetPropertyValue() {
		
		propertiesDao.insertProperty("1", "test1", "testUser");
		propertiesDao.insertProperty("2", "test2", "testUser");
		propertiesDao.insertProperty("3", "test3", "testUser");
		
		String value = propertiesDao.getPropertyValue("3", "testUser2");
		
		Assert.assertEquals("test3", value);
	}
	
	/*
	@Test
	public void testDeletePropertyById() {
		
	        boolean inserted = PropertiesDao.insertProperty("system","test", "public");
		boolean deleted = PropertiesDao.deletePropertyById(1);
		
		Assert.assertTrue(deleted);
		
		// Try to pull the property out, should return null
		String value = PropertiesDao.getPropertyValue(1);
		
		Assert.assertNull(value);
	}
	
	}
*/

	
	@Test
	public void testOdometerIncrementing() {
		
		propertiesDao.insertProperty("1", "test1", "testUser");
		
		CompletePropertyDto property = propertiesDao.getCompleteProperty("1");
		Collection<Tag> attributes = property.getAttributes();
		
		// Odometer should be at 0
		ServiceTag emptyOdometer = new ServiceTag("odometer","0");
		Assert.assertTrue(attributes.contains(emptyOdometer));
		
		String value = propertiesDao.getPropertyValue("1", "testUser2");
		String value2 = propertiesDao.getPropertyValue("1", "testUser3");
		
		// Odometer should now be at 2
		property = propertiesDao.getCompleteProperty("1");
		attributes = property.getAttributes();
		ServiceTag odometer = new ServiceTag("odometer","2");
		Assert.assertTrue(attributes.contains(odometer));
	}
}
