package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.UserDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class UserDaoJdbcImpl extends JdbcDaoSupport implements UserDao {

	private static final String SELECT_ID_FOR_USER = "SELECT ID FROM USERS WHERE USERNAME=?";
	
	private static final String INSERT_USER = "INSERT INTO USERS (USERNAME,CREATION_DATETIME) VALUES (?, sysdate)";
	
	private static final String SELECT_USERNAME_BY_ID = "SELECT USERNAME FROM USERS WHERE ID=?";
	
	@Override
	public int getUserId(String user) {
		
		try {
			
			// Try to select the user from the table.  If the user is not found, the result will be null.
			return getJdbcTemplate().queryForObject(SELECT_ID_FOR_USER, new Object[] { user }, Integer.class);
		} catch (EmptyResultDataAccessException erdae) {
			
			// Means the user did not exist.
			return -1;
		}
	}

	@Override
	public boolean insertUser(String user) {
		
		int rowsUpdated = getJdbcTemplate().update(INSERT_USER, user);
		
		if (rowsUpdated == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getUserById(int id) {
		
		try {
			
			return getJdbcTemplate().queryForObject(SELECT_USERNAME_BY_ID, new Object[] { id }, String.class);
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

}
