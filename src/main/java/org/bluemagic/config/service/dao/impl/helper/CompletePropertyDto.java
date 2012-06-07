package org.bluemagic.config.service.dao.impl.helper;

import java.util.Collection;

import org.bluemagic.config.api.tag.Tag;

public class CompletePropertyDto extends PropertyDto {

	private Collection<Tag> attributes;

	public Collection<Tag> getAttributes() {
		return attributes;
	}

	public void setAttributes(Collection<Tag> attributes) {
		this.attributes = attributes;
	}
}
