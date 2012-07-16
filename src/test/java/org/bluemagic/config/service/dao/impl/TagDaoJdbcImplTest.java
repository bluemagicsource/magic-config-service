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
		int id = tagDao.getTagId("empty", "tag", "public");

		// make sure tag and id do not exist
		Assert.assertNull(value);
		Assert.assertTrue(id == -1);
	}

	@Test
	public void testInsertTag() {

		int id = tagDao.insertTag("system", "test", "public");

		Assert.assertTrue(id != -1);

		// Try to pull the tag out by id
		String value = tagDao.getTagById(id);

		Assert.assertNotNull(value);
		Assert.assertEquals("test", value);
	}

	@Test
	public void testDeleteTagById() {

		int id = tagDao.insertTag("system", "test", "public");
		boolean deleted = tagDao.deleteTagById(id);

		Assert.assertTrue(deleted);

		// Try to pull the tag out, should return null
		String value = tagDao.getTagById(id);

		Assert.assertNull(value);
	}

}
