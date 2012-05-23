package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.UserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoJdbcImplTest {

	private UserDao userDao;
	
	@Before
	public void setUp() {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.test.h2.xml");
		
		// GET USER DAO FROM CONTEXT
		userDao = ctx.getBean(UserDaoJdbcImpl.class);
	}
	
	@Test
	public void testUserDoesntExist() {
		
		int userId = userDao.getUserId("test");
		
		// MAKE SURE THE USER DOESN'T EXIST
		Assert.assertTrue(userId == -1);
	}
	
	@Test
	public void testInsertUser() {
		
		boolean inserted = userDao.insertUser("test");
		
		Assert.assertTrue(inserted);
		
		// Try to pull the user out
		int userId = userDao.getUserId("test");
		
		Assert.assertTrue(userId != -1);
		Assert.assertTrue(userId == 1);
	}
	
	@Test
	public void testGetUserById() {
		
		userDao.insertUser("test1");
		userDao.insertUser("test2");
		userDao.insertUser("test3");
		
		String user = userDao.getUserById(3);
		
		Assert.assertEquals("test3", user);
	}
}
