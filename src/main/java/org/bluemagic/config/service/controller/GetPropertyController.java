package org.bluemagic.config.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bluemagic.config.service.PropertyService;
import org.bluemagic.config.service.utils.ServletUtils;
import org.bluemagic.config.service.utils.TagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value="/property")
@Controller
public class GetPropertyController {

	private static final Log LOG = LogFactory.getLog(GetPropertyController.class);
	
	@Autowired
	private PropertyService propertyService;
	
	/**
	 * The Default method for resolving properties.  This method will return back
	 * the value of the property as a String.
	 */
	@RequestMapping(value="/**", method=RequestMethod.GET)
	public @ResponseBody String getProperty(HttpServletRequest request) {
		// Get the base property from the URI.
		String baseProperty = ServletUtils.getProperty(request, "/property/");
		
		// Get the tags from the query parameter.
		String unorderedTags = request.getQueryString();
		
		String orderedTags = null;
		// Order the tags if there are any.
		if (unorderedTags != null) {
			orderedTags = TagUtils.reorder(unorderedTags);
		}
		
		// Start building the full Property URI by adding on the tags.
		StringBuilder property = new StringBuilder(baseProperty);
		
		// Append the tags if there are any.
		if (orderedTags != null) {
			property.append("?");
			property.append(orderedTags);
		}
		
		// Retrieve the value for this Property.
		String rval = propertyService.getProperty(property.toString());
		
		if (rval == null) {
			throw new RuntimeException("Property " + property.toString() + " not found!");
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info("Resolved property: " + property.toString() + " ----> " + rval);
		}
		
		return rval;
	}

	public PropertyService getPropertyService() {
		return propertyService;
	}

	public void setPropertyService(PropertyService propertyService) {
		this.propertyService = propertyService;
	}
}
