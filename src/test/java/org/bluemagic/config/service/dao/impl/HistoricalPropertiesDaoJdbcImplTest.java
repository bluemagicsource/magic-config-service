package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HistoricalPropertiesDaoJdbcImplTest {

	private HistoricalPropertiesDao historicalpropertiesDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET PROPERTY DAO FROM CONTEXT
		historicalpropertiesDao = ctx.getBean(HistoricalPropertiesDaoJdbcImpl.class);
	}
	
	@Test
	public void testPropertyDoesntExist() {
		
		String propertyKey = historicalpropertiesDao.getHistoricalPropertyValue("test","testUser");
		
		// MAKE SURE THE PROPERTY DOESN'T EXIST
		Assert.assertNull(propertyKey);
	}
	
	@Test
	public void testGetPropertyValue() {
		
		historicalpropertiesDao.insertHistoricalProperty("1", "test1", "testUser");
		historicalpropertiesDao.insertHistoricalProperty("2", "test2", "testUser");
		historicalpropertiesDao.insertHistoricalProperty("3", "test3", "testUser");
		
		String property = historicalpropertiesDao.getHistoricalPropertyValue("3", "testUser2");
		
		Assert.assertEquals("test3", property);
	}
}
