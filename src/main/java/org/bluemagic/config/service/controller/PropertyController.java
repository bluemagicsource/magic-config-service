package org.bluemagic.config.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.PropertyService;
import org.bluemagic.config.service.utils.ServletUtils;
import org.bluemagic.config.service.utils.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="/property")
@Controller
public class PropertyController {

    private static final Log LOG = LogFactory.getLog(PropertyController.class);
	
	@Autowired
	private PropertyService propertyService;
	
	/**
	 * The Default method for resolving properties.  This method will return back
	 * the value of the property as a String.
	 */
	@RequestMapping(value="/**", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> getProperty(HttpServletRequest request) {
		String property = getPropertyFromRequest(request);
		
		// Retrieve the value for this Property.
		String rval = propertyService.getProperty(property);
		
		if (LOG.isInfoEnabled()) {
			LOG.info("[GET] Resolved property: " + property + " ----> " + rval);
		}

		if (rval == null) {
			throw new RuntimeException("Property " + property + " not found!");
		}
		
		return new ResponseEntity<String>(rval, HttpStatus.OK);
	}

	/**
	 * Helper method that extracts the full property name from the HTTP Request
	 * object.
	 * 
	 * @param request
	 *                 The Request to parse.
	 * @return
	 *                 A string that represents the property along with its
	 *                 tags ordered and appended on.
	 */
	private String getPropertyFromRequest(HttpServletRequest request) {
		// Get the base property from the URI.
		String baseProperty = ServletUtils.getProperty(request, "/property/");
		
		// Get that tags if there are any.
		String orderedTags = getTagParameters(request);
		
		// Start building the full Property URI by adding on the tags.
		StringBuilder property = new StringBuilder(baseProperty);
		
		// Append the tags if there are any.
		if (orderedTags != null) {
			property.append("?");
			property.append(orderedTags);
		}
		return property.toString();
	}

	/**
	 * Retrieves the tag parameters from the Request object.
	 * 
	 * @param request
	 *                 The Request object to parse.
	 * @return
	 *                 The tags as an ordered string.
	 */
	private String getTagParameters(HttpServletRequest request) {
		// Get the tags from the query parameter.
		String unorderedTags = request.getQueryString();
		
		String orderedTags = null;
		// Order the tags if there are any.
		if (unorderedTags != null) {
			orderedTags = TagUtils.reorder(unorderedTags);
		}
		return orderedTags;
	}
	
	/**
	 * This method handles all requests to create a new property on the server.  The Request
	 * entity should be in a JSON format.
	 */
	@RequestMapping(value="/**", method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> createProperty(@RequestBody String json) {
		if (LOG.isInfoEnabled()) {
			LOG.info("[POST] Creating property: " + json);
		}
		
		// see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
		// 200 (OK) If resource cannot be identified by a URI, but has content that describes the result.
		// 201 (CREATED) If a resource has been created, and can be identified by a URI.  The response
		//               should also contain an entity which describes the status of the request and
		//               refers to the new resource, and a Location header.
		// 204 (NO CONTENT) If resource cannot be identified by a URI, and no content in the response.
		return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * This method handles all requests to remove a property from the server.
	 */
	@RequestMapping(value="/**", method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<String> deleteProperty(HttpServletRequest request) {
		String property = getPropertyFromRequest(request);
		
		if (LOG.isInfoEnabled()) {
			LOG.info("[DELETE] Deleted property: " + property.toString());
		}
		
		// see http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html
		// 200 (OK) If the response includes an entity describing the status.
		// 202 (ACCEPTED) If the action has not yet been enacted.
		// 204 (NO CONTENT) If the action has been enacted but the response does not include an entity.
		return new ResponseEntity<String>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * This method handles all requests to update a property with new values.
	 */
	@RequestMapping(value="/**", method=RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> updateProperty(@RequestBody String json) {
		if (LOG.isInfoEnabled()) {
			LOG.info("[PUT] Updating property: " + json);
		}
		
		return new ResponseEntity<String>(json, HttpStatus.OK);
	}

	public PropertyService getPropertyService() {
		return propertyService;
	}

	public void setPropertyService(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
}
