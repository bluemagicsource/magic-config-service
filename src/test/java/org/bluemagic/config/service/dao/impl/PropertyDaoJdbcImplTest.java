package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
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
		
		String propertyKey = propertiesDao.getPropertyValue("test","testUser");
		
		// MAKE SURE THE PROPERTY DOESN'T EXIST
		Assert.assertNull(propertyKey);
	}
	
	@Test
	public void testGetPropertyValue() {
		
		propertiesDao.insertProperty("1", "test1", "testUser");
		propertiesDao.insertProperty("2", "test2", "testUser");
		propertiesDao.insertProperty("3", "test3", "testUser");
		
		String property = propertiesDao.getPropertyValue("3", "testUser2");
		
		Assert.assertEquals("test3", property);
	}
}
