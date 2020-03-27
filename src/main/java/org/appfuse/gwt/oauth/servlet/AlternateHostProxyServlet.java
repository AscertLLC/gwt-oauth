package org.appfuse.gwt.oauth.servlet;

import javax.servlet.ServletConfig;

/**
 * Provides reverse proxy functionality in GWT hosted mode to avoid
 * cross-domain issues while accessing services on another domain.
 */
public class AlternateHostProxyServlet extends ProxyServlet {
	private static final long serialVersionUID = -1;

	@Override
    public void init(ServletConfig servletConfig) {

        setProxyHost(servletConfig.getInitParameter("proxyHost"));

        String secure = servletConfig.getInitParameter("secure");
        if (secure != null) {
            setSecure(Boolean.valueOf(secure));
        }

        setFollowRedirects(false);
        setRemovePrefix(true);
        setProxyPort(80);
    }
}