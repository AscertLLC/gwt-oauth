package org.appfuse.gwt.oauth.client;

import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class OAuthPage extends DialogBox {
    public static String STANDARD_ERROR = "Error message is below. Please click OK and refresh your browser to try again.";
    FlowPanel container;
    
    public OAuthPage() {
        super(false, true);
        setText("OAuth with GWT");
        container = new FlowPanel();
        container.setStyleName("oauth_example");

        if (hasAuthenticated()) {
            container.add(new ApiForm());
        } else {
            container.add(new OAuthLoginForm());
        }

        add(container);
        center();
        show();
    }

    public static boolean hasAuthenticated() {
        return (Cookies.getCookie("oauth_token") != null);
    }

    public static boolean isOAuthComplete() {
        return (Cookies.getCookie("oauth_completed") != null);
    }

    public static String[] createOAuthCookies(String data) {
        String oauth_token = data.substring(data.indexOf("oauth_token=") + 12);
        oauth_token = oauth_token.substring(0, oauth_token.indexOf("&"));

        String oauth_token_secret = data.substring(data.indexOf("oauth_token_secret=") + 19);
	    if (oauth_token_secret.indexOf("&") > -1) {
			oauth_token_secret = oauth_token_secret.substring(0, oauth_token_secret.indexOf("&"));
	    }

        Cookies.setCookie("oauth_token", URL.decode(oauth_token));
        Cookies.setCookie("oauth_token_secret", URL.decode(oauth_token_secret));
        return new String[]{oauth_token, oauth_token_secret};
    }
}

