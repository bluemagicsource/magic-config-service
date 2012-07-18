package org.bluemagic.config.service;
import java.net.URI;

import org.bluemagic.config.service.repository.DatabaseRepository;

/**
 * The service is the interface that all implementations can use including
 * but not limited to the restful service, command line client, thick client....
 **/
public class PropertyServiceImpl {

    private DatabaseRepository databaseRepo;    

    /**
     * Creates a new property and value combination
     *
     * @param  uri Contains private and public visibility as well as the optional or required status.
     * @param  value new data to be persisted
     * @return boolean TRUE when the value is successfully persisted false failure to create
     **/
    public boolean create(URI uri, String value) {

	Object property = databaseRepo.put(uri,value);

	if(property != null){
	    return true;
	} else{
	    return false;
	}
    }

    /**
     * @param  uri Contains private and public visibility as well as the optional or required status.
     * @return value new data to be updated (assumed it already exists)
     **/
    public String read(URI uri) {

	Object value = databaseRepo.get(uri);

	return value.toString();
    }

    /**
     * @param  uri Contains private and public visibility as well as the optional or required status.
     * @param  value new data to be updated (assumed it already exists)
     * @return boolean TRUE when the value is successfully persisted false failure to update
     **/
    public boolean update(URI uri, String value) {

	Object updatedProperty = databaseRepo.put(uri,value);

	if(updatedProperty != null){
	    return true;
	} else{
	    return false;
	}
    }

    /**
     * @param  uri Contains private and public visibility as well as the optional or required status.
     * @return boolean TRUE when the value is successfully removed
     **/
    public boolean delete(URI uri) {

	Object deletedProperty = databaseRepo.remove(uri);

	if(deletedProperty != null){
	    return true;
	} else{
	    return false;
	}
    }
}
