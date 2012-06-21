package org.bluemagic.config.service.dao.impl.helper;

import java.net.URI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import org.bluemagic.config.api.property.LocatedProperty;
import org.bluemagic.config.api.tag.Tag;
import org.bluemagic.config.service.ServiceTag;
import org.bluemagic.config.service.dao.impl.PropertiesDaoJdbcImpl;
import org.springframework.jdbc.core.RowMapper;

public class CompletePropertyDtoRowMapper implements RowMapper<CompletePropertyDto> {

	@Override
	public CompletePropertyDto mapRow(ResultSet rs, int row) throws SQLException {
		
		try {
			
			CompletePropertyDto completePropertyDto = new CompletePropertyDto();
			completePropertyDto.setId(rs.getInt("ID"));
			
			// Set the Property
			String key = rs.getString("KEY");
			String value = rs.getString("VALUE");
			
			int query = key.indexOf("?");
			String baseKey;
			if (query > 0) {
				baseKey = key.substring(0, query);
			}
			else {
				baseKey = key;
			}
			
			URI original = new URI(baseKey);
			URI located = new URI(key);
			
			LocatedProperty property = new LocatedProperty(original, located, value, PropertiesDaoJdbcImpl.class);
			completePropertyDto.setProperty(property);
			
			// Set the service tags
			Collection<Tag> attributes = new ArrayList<Tag>();
			
			String creationUser = rs.getString("CREATION_USER");
			if (creationUser != null) {
				
				ServiceTag creationUserTag = new ServiceTag("creationUser", creationUser);
				attributes.add(creationUserTag);
			}
			
			Timestamp creationDatetime = rs.getTimestamp("CREATION_DATETIME");
			if (creationDatetime != null) {
				
				ServiceTag creationDatetimeTag = new ServiceTag("creationDateTime", creationDatetime.toString());
				attributes.add(creationDatetimeTag);
			}
			
			int odometer = rs.getInt("ODOMETER");
			ServiceTag odometerTag = new ServiceTag("odometer", Integer.toString(odometer));
			attributes.add(odometerTag);
			
			String lastAccessedUser = rs.getString("LAST_ACCESSED_USER");
			if (lastAccessedUser != null) {
				
				ServiceTag lastAccessedUserTag = new ServiceTag("lastAccessedUser", lastAccessedUser);
				attributes.add(lastAccessedUserTag);
			}
			
			Timestamp lastAccessedDatetime = rs.getTimestamp("LAST_ACCESSED_DATETIME");
			if (lastAccessedDatetime != null) {
				
				ServiceTag lastAccessedDatetimeTag = new ServiceTag("lastAccessedDatetime", lastAccessedDatetime.toString());
				attributes.add(lastAccessedDatetimeTag);
			}
			
			String lastModifiedUser = rs.getString("LAST_MODIFIED_USER");
			if (lastModifiedUser != null) {
				
				ServiceTag lastModifiedUserTag = new ServiceTag("lastModifiedUser", lastModifiedUser);
				attributes.add(lastModifiedUserTag);
			}
			
			Timestamp lastModifiedDatetime = rs.getTimestamp("LAST_MODIFIED_DATETIME");
			if (lastModifiedDatetime != null) {
				
				ServiceTag lastModifiedDatetimeTag = new ServiceTag("lastModifiedDatetime", lastModifiedDatetime.toString());
				attributes.add(lastModifiedDatetimeTag);
			}
			
			
			completePropertyDto.setAttributes(attributes);
			
			return completePropertyDto;
		} catch (Throwable t) {
			
			throw new RuntimeException(t.getMessage(), t);
		}
	}

}
