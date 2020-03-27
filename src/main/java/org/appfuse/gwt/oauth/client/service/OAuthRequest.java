package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.user.client.Window;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;

public class OAuthRequest extends DefaultRequest {
    private OAuthProvider provider;

    public OAuthRequest(OAuthProvider provider) {
        this.provider = provider;
    }

    public void getToken(RequestCallback cb) {
        String url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), "", provider.getRequestTokenURL());
        url = url.replace(provider.getProxiedURL(), provider.getProxiedURLPrefix());
        send(cb, url);
    }

    public void authorizeToken(RequestCallback cb) {
        String url = provider.getAuthorizeTokenURL().replace("$1", getAuthToken());

        // get rid of oauth_token if it's already been set previously
        String currentLocation = getCurrentLocation();
        if (currentLocation.contains("?oauth_token")) {
            currentLocation = currentLocation.substring(0, currentLocation.indexOf("?oauth_token"));
        }

        url = url.replace("$2", currentLocation);

        url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), "", url);
        url = url.replace(provider.getProxiedURL(), provider.getProxiedURLPrefix());
        send(cb, url);
    }

    public void getAccessToken(RequestCallback cb) {
        String url = provider.getAccessTokenURL().replace("$1", getAuthToken());
        if (url.contains("$2")) {
            url = url.replace("$2", getAuthVerifier());
        }
        url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), getAuthTokenSecret(), url);
        url = url.replace(provider.getProxiedURL(), provider.getProxiedURLPrefix());
        send(cb, url);
    }
}
