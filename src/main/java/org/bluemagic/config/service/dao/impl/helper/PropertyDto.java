package org.bluemagic.config.service.dao.impl.helper;

import org.bluemagic.config.api.property.LocatedProperty;

public class PropertyDto {

	private int id;
	private LocatedProperty property;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public LocatedProperty getProperty() {
		return property;
	}
	
	public void setProperty(LocatedProperty property) {
		this.property = property;
	}
}
