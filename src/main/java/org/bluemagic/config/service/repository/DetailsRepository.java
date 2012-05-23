package org.bluemagic.config.service.repository;

import java.net.URI;

import org.bluemagic.config.api.Repository;
import org.bluemagic.config.api.service.CompletePropertyDetails;
import org.bluemagic.config.api.service.PropertyDetails;

public interface DetailsRepository extends Repository {

	public PropertyDetails getPropertyDetails(URI key);
	
	public CompletePropertyDetails getCompletePropertyDetails(URI key);
}
