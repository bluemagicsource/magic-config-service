package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.HistoricalPropertiesDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HistoricalPropertiesDaoJdbcImplTest {

	private HistoricalPropertiesDao HistoricalPropertiesDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET HISTORICAL PROPERTY DAO FROM CONTEXT
		HistoricalPropertiesDao = ctx.getBean(HistoricalPropertiesDaoJdbcImpl.class);
	}
	
	@Test
	public void testPropertyDoesntExist() {
		
		String historicalpropertyKey = HistoricalPropertiesDao.gethistoricalpropertyvalue("test");
		
		// MAKE SURE THE USER DOESN'T EXIST
		Assert.assertNull(historicalpropertyKey);
	}
	
	
	
	@Test
	public void testgethistoricalpropertyvalue() {
		
		HistoricalPropertiesDao.inserthistoricalproperty("1", "test1");
		HistoricalPropertiesDao.inserthistoricalproperty("2", "test2");
		HistoricalPropertiesDao.inserthistoricalproperty("3", "test3");
		
		String historicalproperty = HistoricalPropertiesDao.gethistoricalpropertyvalue("3");
		
		Assert.assertEquals("test3", historicalproperty);
	}
}
