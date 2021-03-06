package org.appfuse.gwt.oauth.client.util;

import com.google.gwt.http.client.URL;

/*
 * Copyright 2006 Robert Hanson <iamroberthanson AT gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class WindowUtils {

	public static Location getLocation() {
		Location result = new Location();
		result.setHash(getHash());
		result.setHost(getHost());
		result.setHostName(getHostName());
		result.setHref(getHref());
		result.setPath(getPath());
		result.setPort(getPort());
		result.setProtocol(getProtocol());
		result.setQueryString(getQueryString());
		return result;
	}

	private static native String getQueryString()
	/*-{
		return $wnd.location.search;
    }-*/;

	private static native String getProtocol()
	/*-{
		return $wnd.location.protocol;
	}-*/;

	private static native String getPort()
	/*-{
	    return $wnd.location.port;
    }-*/;

    private static native String getPath()
    /*-{
        return $wnd.location.pathname;
    }-*/;

    private static native String getHref()
    /*-{
        return $wnd.location.href;
    }-*/;

    private static native String getHostName()
    /*-{
        return $wnd.location.hostname;
    }-*/;

    private static native String getHost()
    /*-{
        return $wnd.location.host;
    }-*/;

    private static native String getHash()
    /*-{
        return $wnd.location.hash;
    }-*/;

    private static native void _changeLocation(String url)
    /*-{
        $wnd.location.href = url;
    }-*/;

    public static void changeLocation(String url) {
        if (url != null && ! url.contains("://")) {
            // convert to absolute URL
            String protocol = getProtocol();
            // not sure why protocol is returned as 'http:'
            protocol = protocol.replaceFirst("[^a-z]*$", "://");
            url = protocol + getHost() + url;
        }
        _changeLocation(url);
    }

    /**
     * get the first value for a paraName from historyToken
     * @param historyToken  the query string or history token
     * @param paramName
     * @return the value of paramName or empty string if the paramName doesn't exist
     */
    public static String getParamValue(String historyToken, String paramName) {
        String paramValue = "";
        String withoutQM = historyToken.substring(historyToken.indexOf('?') + 1, historyToken.length());
        String[] pairs = withoutQM.split("&");
        for (String pair : pairs) {
            String[] keyThenVal = pair.split("=");
            if (paramName.equals(keyThenVal[0])) {
                paramValue = URL.decode(keyThenVal[1]);
                break;
            }
        }

        return paramValue;
    }
}
