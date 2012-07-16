package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.lang.String; 

public class HistoricalPropertiesDaoJdbcImplTest {

	private PropertiesDao propertiesDao;
	private HistoricalPropertiesDao historicalpropertiesDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET PROPERTY DAO FROM CONTEXT
		propertiesDao = ctx.getBean(PropertiesDaoJdbcImpl.class);
		historicalpropertiesDao = ctx.getBean(HistoricalPropertiesDaoJdbcImpl.class);
	}
	
	@Test
	public void testPropertyDoesntExist() {
		
		String propertyKey = historicalpropertiesDao.getHistoricalPropertyValue("test","testUser");
		
		// MAKE SURE THE PROPERTY DOESN'T EXIST
		Assert.assertNull(propertyKey);
	}
	
	@Test
	public void testGetHistoricalPropertyValue() {

		propertiesDao.insertProperty("1", "test1", "testUser");
		historicalpropertiesDao.insertHistoricalProperty("1");
		
		String property = historicalpropertiesDao.getHistoricalPropertyValue("1", "testUser1");

		Assert.assertEquals("test1", property);
	}
	
}
