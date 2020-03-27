package org.appfuse.gwt.oauth.client;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.appfuse.gwt.oauth.client.provider.Google;
import org.appfuse.gwt.oauth.client.provider.LinkedIn;
import org.appfuse.gwt.oauth.client.provider.OAuthProvider;
import org.appfuse.gwt.oauth.client.provider.Twitter;
import org.appfuse.gwt.oauth.client.service.*;
import org.appfuse.gwt.oauth.client.util.JSOModel;
import org.appfuse.gwt.oauth.client.util.WindowUtils;

import java.util.ArrayList;
import java.util.List;

public class ApiForm extends Composite {
    FlowPanel form;
    TextArea payload;

    protected ApiForm() {
        form = new FlowPanel();
        initWidget(form);

        String providerCookie = Cookies.getCookie("provider");
        if (providerCookie == null) {
            providerCookie = Google.class.getName(); // default
        }

        final OAuthProvider provider = (providerCookie.equals(Google.class.getName()) ? new Google() :
                providerCookie.equals(Twitter.class.getName()) ? new Twitter() : new LinkedIn());

        // Don't ask user to get access token, just do it.
        if (OAuthPage.isOAuthComplete()) {
            String serviceName;
            if (provider instanceof Google) {
                serviceName = "contacts";
            } else if (provider instanceof Twitter) {
                serviceName = "timeline";
            } else {
                serviceName = "profile";
            }
            form.add(new Label("Fetching " + serviceName + "..."));
        } else {
            form.add(new Label("Getting access token..."));
        }

        if (OAuthPage.hasAuthenticated()) {
            Button startOver = new Button("Start Over");
            startOver.addStyleName("start_over");
            startOver.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent clickEvent) {
                    for (String cookie : Cookies.getCookieNames()) {
                        Cookies.removeCookie(cookie);
                    }
                    WindowUtils.changeLocation(WindowUtils.getLocation().getPath());
                }
            });
            form.add(startOver);
        }

        if (provider instanceof LinkedIn && Cookies.getCookie("oauth_verifier") == null) {
            String data = DefaultRequest.getCurrentLocation();
            if (data.indexOf("oauth_verifier=") > -1) {
                String oauth_verifier = data.substring(data.indexOf("oauth_verifier=") + 15);
                if (oauth_verifier.indexOf("&") > -1) {
                    oauth_verifier = oauth_verifier.substring(0, oauth_verifier.indexOf("&"));
                }
                Cookies.setCookie("oauth_verifier", URL.decode(oauth_verifier));
            }
        }

        payload = new TextArea();
        payload.setStyleName("payload");
        form.add(payload);

        if (!OAuthPage.isOAuthComplete()) {
            final OAuthRequest oauthRequest = new OAuthRequest(provider);

            oauthRequest.getAccessToken(new RequestCallback() {

                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == 200) {
                        form.add(new Label("Got access token, calling API..."));
                        OAuthPage.createOAuthCookies(response.getText());
                        Cookies.setCookie("oauth_completed", "true");
                        if (provider instanceof Google) {
                            getContacts(provider);
                        } else if (provider instanceof Twitter) {
                            getTimeline(provider);
                        } else {
                            getProfile(provider);
                        }
                    } else {
                        onError(request, new RequestException(response.getText()));
                    }
                }

                public void onError(Request request, Throwable throwable) {
                    Window.alert("Uh oh, get access token failed. " + OAuthPage.STANDARD_ERROR + "\n\n" + throwable.getMessage());
                }
            });
        } else if (provider instanceof Twitter) {
            getTimeline(provider);
        } else {
            getProfile(provider);
        }
    }

    private void getContacts(OAuthProvider provider) {
        ContactsRequest request = new ContactsRequest(provider);
        request.getContacts(new ApiCallback());
    }

    private void getTimeline(OAuthProvider provider) {
        TimelineRequest request = new TimelineRequest(provider);
        request.getTimeline(new TwitterApiCallback());
    }

    private void getProfile(OAuthProvider provider) {
        ProfileRequest request = new ProfileRequest(provider);
        request.getProfile(new ApiCallback());
    }

    private class ApiCallback implements RequestCallback {
        public void onResponseReceived(Request request, Response response) {
            if (response.getStatusCode() == 200) {
                payload.setText(response.getText());
                Label success = new Label("API call successful!");
                success.setStyleName("success");
                form.add(success);
            } else {
                onError(request, new RequestException(response.getText()));
            }
        }

        public void onError(Request request, Throwable throwable) {
            Window.alert("Calling API failed. " + OAuthPage.STANDARD_ERROR + "\n\n" + throwable.getMessage());
        }
    }

    private class TwitterApiCallback implements RequestCallback {
        public void onResponseReceived(Request request, Response response) {
            if (response.getStatusCode() == 200) {
                JsArray<JSOModel> data = JSOModel.arrayFromJson(response.getText());
                List<Status> statuses = new ArrayList<Status>();
                for (int i = 0; i < data.length(); i++) {
                    Status s = new Status(data.get(i));
                    statuses.add(s);
                }

                for (Status status : statuses) {
                    payload.setValue(payload.getValue() + status.getText() + "\n\n");
                }

                Label success = new Label("API call successful!");
                success.setStyleName("success");
                form.add(success);
            } else {
                onError(request, new RequestException(response.getText()));
            }
        }

        public void onError(Request request, Throwable throwable) {
            Window.alert("Calling API failed. " + OAuthPage.STANDARD_ERROR + "\n\n" + throwable.getMessage());
        }
    }
}
