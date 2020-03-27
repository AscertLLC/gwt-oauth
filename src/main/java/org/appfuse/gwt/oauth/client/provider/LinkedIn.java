package org.appfuse.gwt.oauth.client.provider;

import org.appfuse.gwt.oauth.client.util.WindowUtils;

/**
 * Information for talking to Twitter from demo.raibledesigns.com.
 */
public class LinkedIn implements OAuthProvider {
    private String consumerKey = "Lb0NRUhAVq5iJFVrjqWR93iI7DgGcor5rmxLhfKsVdVA51jHQJyxGxI1lby6oTvB";
    private String consumerSecret = "bNfsg66sag5QUC8bGpfZP321YSjs30VB926t1jeroTn-9VZt6fd5KnLgoZG3twS4";
    private String requestTokenURL = "https://api.linkedin.com/uas/oauth/requestToken";
    private String authorizeTokenURL = "https://api.linkedin.com/uas/oauth/authorize?oauth_token=$1";
    private String accessTokenURL = "https://api.linkedin.com/uas/oauth/accessToken?oauth_token=$1&oauth_verifier=$2";

    private String proxiedURLPrefix = "/linkedin/";
    private String proxiedURL = "https://api.linkedin.com/";

    public LinkedIn() {
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