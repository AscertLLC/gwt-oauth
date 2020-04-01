package org.appfuse.gwt.oauth.client.service;

import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.user.client.Cookies;

public class DefaultRequest {
    protected void send(RequestCallback cb, String URL) {
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL);
        builder.setTimeoutMillis(10000);
        builder.setCallback(cb);
        
        Request req = null;
        try {
            req = builder.send();
        } catch (RequestException e) {
            cb.onError(req, e);
        }
    }

    public static String getAuthToken() {
        String authToken = Cookies.getCookie("oauth_token");
        assert authToken != null;
        return authToken;
    }

	public static String getAuthVerifier() {
        String authVerifier = Cookies.getCookie("oauth_verifier");
        assert authVerifier != null;
        return authVerifier;
    }

    public static String getAuthTokenSecret() {
        String tokenSecret = Cookies.getCookie("oauth_token_secret");
        assert tokenSecret != null;
        return tokenSecret;
    }

    public static String signRequest(String ck, String cks, String ts, String encodedurl) {
        return signRequest(ck, cks, ts, encodedurl, null, null);
        
    }
    /**
     * Make a signed OAuth request. Originally this was coded in HTML, but it's tidier to have the JS functions as part of overall code since
     * it removes the need for a user of this module to have custom JS in their main HTML page.
     * @param ck consumer key
     * @param cks consumer secret
     * @param ts oauth token secret
     * @param encodedurl - must have oauth encoded as a query param to work properly
     * @return request with OAuth params and encoded URL
     */
    public native static String signRequest(String ck, String cks, String ts, String encodedurl,
                                            String tst_tstamp, String tst_nonce) /*-{
        var accessor = {consumerSecret: cks, tokenSecret: ts};
            var message = {
                method: "GET",
                action: encodedurl,
                parameters: {
                    oauth_signature_method: 'HMAC-SHA1',
                    oauth_consumer_key:     ck,
                    oauth_version:          '1.0',
                    format:                 'json',						
                    oauth_timestamp:        tst_tstamp,												
                    oauth_nonce:            tst_nonce
                }
            };

            // For test and debug we allow a known timestamp and nonce to be passed
            if (tst_tstamp == null || tst_nonce == null) {
                $wnd.OAuth.setTimestampAndNonce(message);
                $wnd.OAuth.setParameter(message, "oauth_timestamp", $wnd.OAuth.timestamp());
            }
        
            $wnd.OAuth.SignatureMethod.sign(message, accessor);
            return $wnd.OAuth.addToURL(message.action, message.parameters);        
    }-*/;

    public static String calcSignature(String ck, String cks, String oat, String ts, String encodedurl) {
        return calcSignature(ck, cks, oat, ts, encodedurl, null, null);
    }
                                              

    public native static String calcSignature(String ck, String cks, String oat, String ts, String encodedurl,
                                              String tst_tstamp, String tst_nonce) /*-{
        
        var accessor = {consumerSecret: cks, tokenSecret: ts};
        var message = {
                method: "GET",
                action: encodedurl,
                parameters: {
                    oauth_signature_method: 'HMAC-SHA1',
                    oauth_consumer_key:     ck,
                    oauth_token:            oat,
                    oauth_version:          '1.0',						
                    oauth_timestamp:        tst_tstamp,												
                    oauth_nonce:            tst_nonce
                }
        };

        // For test and debug we allow a known timestamp and nonce to be passed
        if (tst_tstamp == null || tst_nonce == null) {
            $wnd.OAuth.setTimestampAndNonce(message);
            $wnd.OAuth.setParameter(message, "oauth_timestamp", $wnd.OAuth.timestamp());
        }

        $wnd.OAuth.SignatureMethod.sign(message, accessor);
        return $wnd.OAuth.getParameter(message.parameters, "oauth_signature");
    }-*/;
    
    // Mainly in here as a convenient test stub
    public native static String calcSHA1(String val)  /*-{
      return $wnd.hex_sha1(val);
    }-*/;
    
    public native static String getCurrentLocation() /*-{
        return $doc.location.toString();
    }-*/;
}

