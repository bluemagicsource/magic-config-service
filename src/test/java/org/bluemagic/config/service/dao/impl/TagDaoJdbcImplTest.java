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
		
		// GET USER DAO FROM CONTEXT
		tagDao = ctx.getBean(TagDaoJdbcImpl.class);
	}
	
	@Test
	public void testTagDoesntExist() {
		
		String value = tagDao.getTagById(1);
		
		// MAKE SURE THE TAG DOESN'T EXIST
		Assert.assertNull(value);
	}
	
	@Test
	public void testInsertTag() {
		
		boolean inserted = tagDao.insertTag("system","test");
		
		Assert.assertTrue(inserted);
		
		// Try to pull the tag out
		String value = tagDao.getTagById(1);
		
		Assert.assertNotNull(value);
		Assert.assertEquals("test", value);
	}
	
	@Test
	public void testUpdateTagById() {
		
		boolean inserted = tagDao.insertTag("system","test");
		boolean updated = tagDao.updateTagById(1,"system","production");
		
		Assert.assertTrue(updated);
		
		// Try to pull the tag out
		String value = tagDao.getTagById(1);
		
		Assert.assertNotNull(value);
		Assert.assertEquals("production", value);
	}
	
	@Test
	public void testDeleteTagById() {
		
		boolean inserted = tagDao.insertTag("system","test");
		boolean deleted = tagDao.deleteTagById(1);
		
		Assert.assertTrue(deleted);
		
		// Try to pull the tag out, should return null
		String value = tagDao.getTagById(1);
		
		Assert.assertNull(value);
	}
	
	
}
