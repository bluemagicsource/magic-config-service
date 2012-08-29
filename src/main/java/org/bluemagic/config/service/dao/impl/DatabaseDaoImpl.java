package org.bluemagic.config.service.dao.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.dao.DatabaseDao;
import org.bluemagic.config.service.repository.DatabaseRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

public class DatabaseDaoImpl implements DatabaseDao {
	
	private static final Log LOG = LogFactory.getLog(PropertiesDaoJdbcImpl.class);
	
	private DatabaseRepository dataSource;
	

	@Override
	public void installDb(boolean force) {
	   /*ResourceDatabasePopulator rdp = new ResourceDatabasePopulator();
	   rdp.addScript(new ClassPathResource("/script-file.sql"));
	   rdp.populate(dataSource.getConnection());*/

	}

}
