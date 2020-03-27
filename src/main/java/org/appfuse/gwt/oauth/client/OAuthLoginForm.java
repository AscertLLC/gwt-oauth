package org.appfuse.gwt.oauth.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;
import org.appfuse.gwt.oauth.client.provider.Google;
import org.appfuse.gwt.oauth.client.provider.LinkedIn;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;
import org.appfuse.gwt.oauth.client.provider.Twitter;
import org.appfuse.gwt.oauth.client.service.DefaultRequest;
import org.appfuse.gwt.oauth.client.service.OAuthRequest;
import org.appfuse.gwt.oauth.client.util.WindowUtils;

public class OAuthLoginForm extends Composite {
    FlowPanel form;

    protected OAuthLoginForm() {
        form = new FlowPanel();
        initWidget(form);

        form.add(new HTML("<p>Select a Provider and then click on the button to see OAuth in action...</p>"));

        final ListBox providers = new ListBox();
        providers.addItem("Google");
        providers.addItem("LinkedIn");
        providers.addItem("Twitter");
        providers.setSelectedIndex(1);

        form.add(providers);

        Button getToken = new Button("Get OAuth Token");
        getToken.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent clickEvent) {
                getTokenAndAuthorize(providers.getSelectedIndex());
            }
        });

        form.add(getToken);

        form.add(new HTML("<p>For more information about this demo, see <a href=\"http://raibledesigns.com/rd/entry/implementing_oauth_with_gwt\">Implementing OAuth with GWT</a>."));

        //form.add(new HTML("<p class='note'>NOTE: You must use a Gmail account (not a hosted one) for this demo to work properly.</p>"));
    }

    private void getTokenAndAuthorize(int index) {
        OAuthProvider provider;
	    switch (index) {
		    case 0: provider = new Google(); break;
			case 1: provider = new LinkedIn(); break;
			default: provider = new Twitter(); break;
	    }

	    final OAuthProvider oauthProvider = provider;

        Cookies.setCookie("provider", oauthProvider.getClass().getName());
        final OAuthRequest oauthRequest = new OAuthRequest(provider);

        oauthRequest.getToken(new RequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                if (response.getStatusCode() == 200) {
                    String data = response.getText();

                    OAuthPage.createOAuthCookies(data);

                    oauthRequest.authorizeToken(new RequestCallback() {

                        public void onResponseReceived(Request request, Response response) {
                            String text = response.getText();
                            if (response.getStatusCode() == 200 && response.getText().startsWith("http")) {
                                WindowUtils.changeLocation(response.getText());
                            } else {
                                // look for meta-tag that refreshes and grab its URL
                                if (text.contains("<meta http-equiv=\"refresh\"")) {
                                    String tokenToStartWith = "url=&#39;";
                                    String tokenToEndWith = "&#39;\">";
                                    String url = text.substring(text.indexOf(tokenToStartWith) + tokenToStartWith.length());
                                    url = url.substring(0, url.indexOf(tokenToEndWith) + tokenToEndWith.length());
                                    WindowUtils.changeLocation(url);
                                } else {
                                    // Twitter and LinkedIn return a full HTML page, so redirect to the authorize URL manually
                                    if (oauthProvider instanceof Twitter || oauthProvider instanceof LinkedIn) {
                                        String url = oauthProvider.getAuthorizeTokenURL();
                                        url = url.replace("$1", OAuthRequest.getAuthToken());
                                        url = url.replace("$2", DefaultRequest.getCurrentLocation());
                                        WindowUtils.changeLocation(url);
                                    } else {
                                        onError(request, new RequestException(text));
                                    }
                                }
                            }
                        }

                        public void onError(Request request, Throwable caught) {
                            Window.alert("Calling authorize token failed. " + OAuthPage.STANDARD_ERROR + "\n\n" + caught.getMessage());
                        }
                    });
                } else {
                    onError(request, new RequestException(response.getText()));
                }
            }

            public void onError(Request request, Throwable caught) {
                Window.alert("Calling get token failed. " + OAuthPage.STANDARD_ERROR + "\n\n" + caught.getMessage());
            }
        });
    }
}