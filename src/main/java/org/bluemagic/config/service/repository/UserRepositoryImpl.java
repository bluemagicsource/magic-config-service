package org.bluemagic.config.service.repository;

import org.bluemagic.config.api.UserRepository;
import java.net.URI;

/**
 * Generic implementation to support user repositories
 **/
public class UserRepositoryImpl implements UserRepository {

    /**
     * @param  uri - Requested uri
     * @return boolean - true when the incoming uri is supported
     **/
	public boolean supports(URI uri) {
        return true;
    }
	
    /**
     * The put method inserts a new data record or updates
     * an existing data record's value
     *
     * @param  key - Unique uri (note that our URIs should already
     *         be normalized prior to reaching this interface.
     * @param  value -
     * @param  userIdentifier - Unique identifier of the user requesting
     *         the specified operation.
     * @return return ?
     **/
	public Object put(URI key, 
                      Object value, 
                      String userIdentifier) {
        return null;
    }
	
    /**
     * @param  key - Unique uri
     * @param  userIdentifier - Unique identifier of the user requesting
     *         the specified operation.
     * @return Object The current value corresponding to the key or MissingProperty
     *         if the value does not exist in the repository
     **/
	public Object get(URI key, 
                      String userIdentifier) {

	/* 
	 * IF key is valid
	 * 	objValue = SELECT key
	 *	update property last accessed by userIdentifier
	 *	update odometer
	 * ELSE
	 *	return MissingProperty
	 *
	 *
	 * IF objValue = null
	 *	return null
	 * ELSE
	 *	return objValue
	 **/

        return null;
    }

    /**
     * Removes the item from the repository.
     *
     * @param  key - Unique uri
     * @param  userIdentifier - Unique identifier of the user requesting
     *         the specified operation.
     * @return Object value removed
     **/
	public Object remove(URI key, 
                         String userIdentifier) {
        return null;
    }
	
    /**
     * Clears the entire repository.
     * @param  userIdentifier - Unique identifier of the user requesting
     *         the specified operation.
     **/
	public void clear(String userIdentifier) {
    }
	
    /**
     * Returns the total number of properties in the repository
     **/
	public int size() {
        return 0;
    }
}
