package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.bluemagic.config.service.dao.TagDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertiesTagMappingDaoJdbcImplTest {

	private PropertiesDao propertiesDao;
	private TagDao tagDao;
	private PropertiesTagMappingDao propertiesTagMappingDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// Get Property DAO from context
		propertiesDao = ctx.getBean(PropertiesDaoJdbcImpl.class);
		
		// Get Tag DAO from context
		tagDao = ctx.getBean(TagDaoJdbcImpl.class);
		
		// Get PropertyTagMapping DAO from context
		propertiesTagMappingDao = ctx.getBean(PropertiesTagMappingDaoJdbcImpl.class);
	}
	
	
	@Test
	public void testInsertPropertiesTagMapping() {
		int propertyId = propertiesDao.insertProperty("propertyKey","propertyValue", "creationUser");
		int tagId = tagDao.insertTag("tagKey","tagValue", "public");
		
		boolean inserted = propertiesTagMappingDao.insertPropertyToTagMapping(propertyId, tagId);
		
		Assert.assertTrue(inserted);
	}
	
}
