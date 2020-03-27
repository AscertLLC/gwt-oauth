package org.appfuse.gwt.oauth.client.provider;

import org.appfuse.gwt.oauth.client.util.WindowUtils;

/**
 * Information for talking to Google from demo.raibledesigns.com.
 */
public class Google implements OAuthProvider {
    private String consumerKey = "demo.raibledesigns.com";
    private String consumerSecret = "RDRv27OIl+ubl0eHnvxpuml7";
    private String requestTokenURL = "https://www.google.com/accounts/OAuthGetRequestToken?scope=http%3A%2F%2Fwww.google.com%2Fm8/feeds%2F";
    private String authorizeTokenURL = "https://www.google.com/accounts/OAuthAuthorizeToken?oauth_token=$1&oauth_callback=$2";
    private String accessTokenURL = "https://www.google.com/accounts/OAuthGetAccessToken?oauth_token=$1";

    private String proxiedURLPrefix = "/google/";
    private String proxiedURL = "https://www.google.com/";

    public Google() {
        // allow for deploying at /gwt-oauth context
        if (WindowUtils.getLocation().getPath().contains("gwt-oauth")) {
            proxiedURLPrefix = "/gwt-oauth" + proxiedURLPrefix;
        }
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getRequestTokenURL() {
        return requestTokenURL;
    }

    public String getAuthorizeTokenURL() {
        return authorizeTokenURL;
    }

    public String getAccessTokenURL() {
        return accessTokenURL;
    }

    public String getProxiedURL() {
        return proxiedURL;
    }

    public String getProxiedURLPrefix() {
        return proxiedURLPrefix;
    }
}