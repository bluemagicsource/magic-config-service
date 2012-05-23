package org.bluemagic.config.service.dao;

public interface UserDao {

	public int getUserId(String user);
	
	public boolean insertUser(String user);
	
	public String getUserById(int id);
}