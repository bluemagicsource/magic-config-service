package org.bluemagic.config.service.dao;

import org.springframework.beans.factory.InitializingBean;

public class InstallationCheck implements InitializingBean {
	
	private DatabaseDao databaseDao;
	
	private boolean force = false;

	@Override
	public void afterPropertiesSet() throws Exception {
		databaseDao.installDb(isForce());
		
	}

	public void setDatabaseDao(DatabaseDao databaseDao) {
		this.databaseDao = databaseDao;
	}

	public DatabaseDao getDatabaseDao() {
		return databaseDao;
	}

	public void setForce(boolean force) {
		this.force = force;
	}

	public boolean isForce() {
		return force;
	}
}
