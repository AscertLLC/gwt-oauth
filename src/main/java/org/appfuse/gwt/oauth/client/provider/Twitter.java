package org.appfuse.gwt.oauth.client.provider;

import org.appfuse.gwt.oauth.client.util.WindowUtils;

/**
 * Information for talking to Twitter from demo.raibledesigns.com.
 */
public class Twitter implements OAuthProvider {
    private String consumerKey = "B4JEOnHccXp242Z5V9vw";
    private String consumerSecret = "kiecw38pNeRywv3uqWG4kSlw3IO6SsIu0R3SHqxq8";
    private String requestTokenURL = "http://twitter.com/oauth/request_token";
    private String authorizeTokenURL = "http://twitter.com/oauth/authorize?oauth_token=$1&oauth_callback=$2";
    private String accessTokenURL = "http://twitter.com/oauth/access_token?oauth_token=$1";

    private String proxiedURLPrefix = "/twitter/";
    private String proxiedURL = "http://twitter.com/";

    public Twitter() {
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
