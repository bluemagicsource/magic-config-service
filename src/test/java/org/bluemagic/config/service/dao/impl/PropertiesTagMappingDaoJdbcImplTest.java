/*package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertiesTagMappingDaoJdbcImplTest {

	private PropertiesTagMapping propertiestagmappingDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET TAG DAO FROM CONTEXT
		tagDao = ctx.getBean(PropertiesTagMappingDaoJdbcImpl.class);
	}
	
	
	@Test
	public void testGetPropertyValue() {
		
		propertiestagmappingDao.insertProperty("1", "test1");
		propertiestagmappingDao.insertProperty("2", "test2");
		propertiestagmappingDao.insertProperty("3", "test3");
		
		String property = propertiestagmappingDao.getPropertyValue("3");
		
		Assert.assertEquals("test3", property);
	}
}
	
	@Test
	public void testInsertTag() {
		
		boolean inserted = propertiestagmappingDao.insertTag("system","test");
		
		Assert.assertTrue(inserted);
		
		// Try to pull the tag out by id
		String value = propertiestagmappingDao.getTagById(1);
		
		// Try to pull the tag id out by key and value
		int id = propertiestagmappingDao.getTagId("system", "test");
		
		Assert.assertNotNull(value);
		Assert.assertTrue(id != -1);
		Assert.assertEquals("test", value);
		Assert.assertTrue(id == 1);
	}
	
	@Test
	public void testDeleteTagById() {
		
		boolean inserted = propertiestagmappingDao.insertTag("system","test");
		boolean deleted = propertiestagmappingDao.deleteTagById(1);
		
		Assert.assertTrue(deleted);
		
		// Try to pull the tag out, should return null
		String value = propertiestagmappingDao.getTagById(1);
		
		Assert.assertNull(value);
	}
	
	
}
*/
