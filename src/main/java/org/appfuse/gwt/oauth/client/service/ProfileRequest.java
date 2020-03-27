package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;
import org.appfuse.gwt.oauth.client.util.WindowUtils;

public class ProfileRequest extends DefaultRequest {
    private static final String LINKEDIN_PROFILE_URL = "http://api.linkedin.com/v1/people/~:(id,first-name,last-name,picture-url,headline,summary,positions,educations)?oauth_token=$1";
    private OAuthProvider provider;

    public ProfileRequest(OAuthProvider provider) {
        this.provider = provider;
    }

    public void getProfile(RequestCallback cb) {
        String url = LINKEDIN_PROFILE_URL.replace("$1", getAuthToken());
        url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), getAuthTokenSecret(), url);

        String proxiedURLPrefix = "/linkedin-api/";
        // allow for deploying at /gwt-oauth context
        if (WindowUtils.getLocation().getPath().contains("gwt-oauth")) {
            proxiedURLPrefix = "/gwt-oauth" + proxiedURLPrefix;
        }

        url = url.replace("http://api.linkedin.com/", proxiedURLPrefix);

        send(cb, url);
    }
}