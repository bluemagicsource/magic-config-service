package org.bluemagic.config.service.dao.impl.helper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.service.dao.impl.PropertiesDaoJdbcImpl;
import org.springframework.jdbc.core.RowMapper;

public class PropertyDtoRowMapper implements RowMapper<PropertyDto> {

	@Override
	public PropertyDto mapRow(ResultSet rs, int row) throws SQLException {
		
		try {
			
			PropertyDto serviceProperty = new PropertyDto();
			serviceProperty.setId(rs.getInt("ID"));
			
			String key = rs.getString("KEY");
			String value = rs.getString("VALUE");
			
			int query = key.indexOf("?");
			String baseKey = key.substring(0, query);
			
			URI original = new URI(baseKey);
			URI located = new URI(key);
			
			LocatedProperty property = new LocatedProperty(original, located, value, PropertiesDaoJdbcImpl.class);
			serviceProperty.setProperty(property);
			
			return serviceProperty; 
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
	}

}
