package org.bluemagic.config.service.dao.impl;

import org.bluemagic.config.service.dao.PropertiesDao;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class PropertiesDaoJdbcImpl extends JdbcDaoSupport implements PropertiesDao {

	@Override
	public String getPropertyValue(String propertyKey){

		return "";
	}
}
