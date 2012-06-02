package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesTagMappingDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PropertiesTagMappingDaoJdbcImplTest {

	private PropertiesTagMappingDao propertiestagmappingDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET USER DAO FROM CONTEXT
		userDao = ctx.getBean(PropertiesTagMappingDaoJdbcImpl.class);
	}
	
	@Test
	public void testTagDoesntExist() {
		
		String value = tagDao.getTagById(1);
		int id = tagDao.getTagId("empty","tag");
		
		// make sure tag and id do not exist
		Assert.assertNull(value);
		Assert.assertTrue(id == -1);
	}
	

	
