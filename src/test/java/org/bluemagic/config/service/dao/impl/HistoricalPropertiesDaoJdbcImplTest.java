package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.lang.String;
import java.sql.Timestamp; 
import java.util.Date; 

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
		
//		historicalpropertiesDao.insertHistoricalProperty(1, "1", "test1", new Timestamp(new Date().getTime()), "testUser1", 2500, new Timestamp(new Date().getTime()), "testUser2", new Timestamp(new Date().getTime()), "testUser2");
		//historicalpropertiesDao.insertHistoricalProperty(20, "2", "test2", "testUser2", 2012-06-8, 15, "2012-06-08 14:59:30", "testUser1", "2012-06-08 15:59:30", "testUser1");
		//historicalpropertiesDao.insertHistoricalProperty(36, "3", "test3", "testUser3", 2012-06-8, 1500, "2012-06-08 17:59:30", "testUser3", "2012-06-08 19:59:30", "testUser3");
		
		String property = historicalpropertiesDao.getHistoricalPropertyValue("1", "testUser1");

		Assert.assertEquals("test1", property);
	}
	
}
