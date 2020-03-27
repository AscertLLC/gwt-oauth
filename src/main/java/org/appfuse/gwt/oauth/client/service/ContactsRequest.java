package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;
import org.appfuse.gwt.oauth.client.util.WindowUtils;

public class ContactsRequest extends DefaultRequest {
    private static final String GOOGLE_CONTACTS_URL = "http://www.google.com/m8/feeds/contacts/default/thin?oauth_token=$1";
    private OAuthProvider provider;

    public ContactsRequest(OAuthProvider provider) {
        this.provider = provider;
    }

    public void getContacts(RequestCallback cb) {
        String url = GOOGLE_CONTACTS_URL.replace("$1", getAuthToken());
        url = signRequest(provider.getConsumerKey(), provider.getConsumerSecret(), getAuthTokenSecret(), url);

        String proxiedURLPrefix = "/contacts/";
        // allow for deploying at /gwt-oauth context
        if (WindowUtils.getLocation().getPath().contains("gwt-oauth")) {
            proxiedURLPrefix = "/gwt-oauth" + proxiedURLPrefix;
        }

        url = url.replace("http://www.google.com/", proxiedURLPrefix);

        send(cb, url);
    }
}