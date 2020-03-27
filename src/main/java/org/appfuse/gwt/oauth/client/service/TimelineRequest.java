package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;

public class TimelineRequest extends DefaultRequest {
    private static final String TWITTER_TIMELINE_URL = "http://twitter.com/statuses/user_timeline.json?oauth_token=$1";
    private OAuthProvider provider;

    public TimelineRequest(OAuthProvider provider) {
        this.provider = provider;
    }

    public void getTimeline(RequestCallback cb) {
        String url = TWITTER_TIMELINE_URL.replace("$1", getAuthToken());
        url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), getAuthTokenSecret(), url);
        url = url.replace(provider.getProxiedURL(), provider.getProxiedURLPrefix());
        send(cb, url);
    }
}