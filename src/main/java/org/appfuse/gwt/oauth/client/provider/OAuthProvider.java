package org.appfuse.gwt.oauth.client.provider;

public interface OAuthProvider {
    String getConsumerKey();
    String getConsumerSecret();
    String getRequestTokenURL();
    String getAuthorizeTokenURL();
    String getAccessTokenURL();
    String getProxiedURL();
    String getProxiedURLPrefix();
}
