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
		HistoricalPropertiesDao = ctx.getBean(HistoricalPropertiesDaoJdbcImpl.class);
	}
	
	@Test
	public void testPropertyDoesntExist() {
		
		String propertyKey = HistoricalPropertiesDao.getPropertyValue("test");
		
		// MAKE SURE THE USER DOESN'T EXIST
		Assert.assertNull(propertyKey);
	}
	
	
	
	@Test
	public void testGetHistoricalPropertyValue() {
		
		HistoricalPropertiesDao.insertProperty("1", "test1");
		HistoricalPropertiesDao.insertProperty("2", "test2");
		HistoricalPropertiesDao.insertProperty("3", "test3");
		
		String property = HistoricalPropertiesDao.getPropertyValue("3");
		
		Assert.assertEquals("test3", property);
	}
}
