package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.TagDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TagDaoJdbcImplTest {

	private TagDao tagDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET TAG DAO FROM CONTEXT
		tagDao = ctx.getBean(TagDaoJdbcImpl.class);
	}
	
	@Test
	public void testTagDoesntExist() {
		
		String value = tagDao.getTagById(1);
		int id = tagDao.getTagId("empty","tag", "public");
		
		// make sure tag and id do not exist
		Assert.assertNull(value);
		Assert.assertTrue(id == -1);
	}
	
	@Test
	public void testInsertTag() {
		
	        boolean inserted = tagDao.insertTag("system","test", "public");
		
		Assert.assertTrue(inserted);
		
		// Try to pull the tag out by id
		String value = tagDao.getTagById(1);
		
		// Try to pull the tag id out by key and value
		int id = tagDao.getTagId("system", "test", "public");
		
		Assert.assertNotNull(value);
		Assert.assertTrue(id != -1);
		Assert.assertEquals("test", value);
		Assert.assertTrue(id == 1);
	}
	
	@Test
	public void testDeleteTagById() {
		
	        boolean inserted = tagDao.insertTag("system","test", "public");
		boolean deleted = tagDao.deleteTagById(1);
		
		Assert.assertTrue(deleted);
		
		// Try to pull the tag out, should return null
		String value = tagDao.getTagById(1);
		
		Assert.assertNull(value);
	}
	
	
}
