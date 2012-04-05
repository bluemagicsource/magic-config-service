package org.bluemagic.config.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

public class ServletUtilsTest {

	@Test
	public void testParseUri() {
		HttpServletRequest request = new RequestHolder("http://localhost:8080", "/property/", "test/prop").getRequest();
		
		Assert.assertEquals("http://localhost:8080/property/test/prop", request.getRequestURI());
		Assert.assertEquals("test/prop", ServletUtils.getProperty(request, "/property/"));
	}
	
	private class RequestHolder {
		
		private HttpServletRequest request;
		
		public RequestHolder(final String contextPath, final String context, final String property) {
			this.request = new HttpServletRequest() {
				
				public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
					// TODO Auto-generated method stub
					
				}
				
				public void setAttribute(String arg0, Object arg1) {
					// TODO Auto-generated method stub
					
				}
				
				public void removeAttribute(String arg0) {
					// TODO Auto-generated method stub
					
				}
				
				public boolean isSecure() {
					// TODO Auto-generated method stub
					return false;
				}
				
				public int getServerPort() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public String getServerName() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getScheme() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public RequestDispatcher getRequestDispatcher(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public int getRemotePort() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public String getRemoteHost() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getRemoteAddr() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getRealPath(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public BufferedReader getReader() throws IOException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getProtocol() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String[] getParameterValues(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Enumeration getParameterNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Map getParameterMap() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getParameter(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Enumeration getLocales() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Locale getLocale() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public int getLocalPort() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public String getLocalName() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getLocalAddr() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public ServletInputStream getInputStream() throws IOException {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getContentType() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public int getContentLength() {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public String getCharacterEncoding() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Enumeration getAttributeNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Object getAttribute(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public boolean isUserInRole(String arg0) {
					// TODO Auto-generated method stub
					return false;
				}
				
				public boolean isRequestedSessionIdValid() {
					// TODO Auto-generated method stub
					return false;
				}
				
				public boolean isRequestedSessionIdFromUrl() {
					// TODO Auto-generated method stub
					return false;
				}
				
				public boolean isRequestedSessionIdFromURL() {
					// TODO Auto-generated method stub
					return false;
				}
				
				public boolean isRequestedSessionIdFromCookie() {
					// TODO Auto-generated method stub
					return false;
				}
				
				public Principal getUserPrincipal() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public HttpSession getSession(boolean arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public HttpSession getSession() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getServletPath() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getRequestedSessionId() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public StringBuffer getRequestURL() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getRequestURI() {
					return contextPath + context + property;
				}
				
				public String getRemoteUser() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getQueryString() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getPathTranslated() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getPathInfo() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getMethod() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public int getIntHeader(String arg0) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public Enumeration getHeaders(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public Enumeration getHeaderNames() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getHeader(String arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
				public long getDateHeader(String arg0) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				public Cookie[] getCookies() {
					// TODO Auto-generated method stub
					return null;
				}
				
				public String getContextPath() {
					return contextPath;
				}
				
				public String getAuthType() {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}

		public HttpServletRequest getRequest() {
			return request;
		}
	}
}
