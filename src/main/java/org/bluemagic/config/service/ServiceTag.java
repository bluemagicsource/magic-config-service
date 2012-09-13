package org.bluemagic.config.service;

import org.bluemagic.config.api.tag.TripleTag;

/**
 * Used by the UI to display additional information.
 */
public class ServiceTag extends TripleTag {

	private static String serviceNamespace = "service";
	
	public ServiceTag(String predicate, String value) {
		super(serviceNamespace, predicate, value);
	}

	public String getServiceNamespace() {
		return serviceNamespace;
	}

	public void setServiceNamespace(String serviceNamespace) {
		ServiceTag.serviceNamespace = serviceNamespace;
	}
}
