package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Cookies;

public class DefaultRequest {
    protected void send(RequestCallback cb, String URL) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL);
        builder.setTimeoutMillis(10000);
        builder.setCallback(cb);
        
        Request req = null;
        try {
            req = builder.send();
        } catch (RequestException e) {
            cb.onError(req, e);
        }
    }

    public static String getAuthToken() {
        String authToken = Cookies.getCookie("oauth_token");
        assert authToken != null;
        return authToken;
    }

	public static String getAuthVerifier() {
        String authVerifier = Cookies.getCookie("oauth_verifier");
        assert authVerifier != null;
        return authVerifier;
    }

    public static String getAuthTokenSecret() {
        String tokenSecret = Cookies.getCookie("oauth_token_secret");
        assert tokenSecret != null;
        return tokenSecret;
    }

    public native static String signRequest(String key, String secret, String tokenSecret, String url) /*-{
        return $wnd.makeSignedRequest(key, secret, tokenSecret, url);
    }-*/;


    public native static String getCurrentLocation() /*-{
        return $doc.location.toString();
    }-*/;
}

