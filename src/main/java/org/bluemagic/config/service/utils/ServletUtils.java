package org.bluemagic.config.service.utils;

import javax.servlet.http.HttpServletRequest;

public final class ServletUtils {

	// Private Constructor so it cannot be instantiated directly.
	private ServletUtils() {}
	
	public static String getProperty(HttpServletRequest request, String context) {
		String contextPath = request.getContextPath();
		int length = (contextPath + context).length();
		return (request.getRequestURI().substring(length));
	}
}
