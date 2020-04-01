/**
 * Copyright (C) 2009-2012 the original author or authors.
 * See the notice.md file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.appfuse.gwt.oauth.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Timer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.appfuse.gwt.oauth.client.service.DefaultRequest;

/**
 * This test pack is somewhat complicated due to the JUnit rigs apparently not loading our JS lib files - I guess understandable in a way
 * becasue they are not in the junit.html, and there doesn't seem to be a standard way to customize that. So instead we load them by hand
 * which, being an asynchronous process, means we need some ugly timed delays in here.
 * 
 * @author Rob Walker
 */
public class OAuthSignatureGwtTest extends GWTTestCase {

    @Override
    public String getModuleName() {
        return "org.appfuse.gwt.oauth.OAuthGwtTest";
    }

    
    public void gwtSetUp () {
        System.out.println("startiing up tests ....");
        loadShaJS();
    }
        
    public void loadShaJS() {
        ScriptInjector.fromUrl("js/sha1.js").setWindow(ScriptInjector.TOP_WINDOW).setCallback(
            new Callback<Void, Exception>() {
                public void onFailure(Exception reason) {
                    System.out.println("loadShaJS - Script load failed.");
                    reason.printStackTrace();
                    junit.framework.Assert.fail(reason.getMessage());
                }
                public void onSuccess(Void result) {
                    System.out.println("loadShaJS - loaded.");
                    loadOAuthJS();
                }
            }
        ).inject();        
    }
    
    public void loadOAuthJS() {
        ScriptInjector.fromUrl("js/oauth.js").setWindow(ScriptInjector.TOP_WINDOW).setCallback(
            new Callback<Void, Exception>() {
                public void onFailure(Exception reason) {
                    System.out.println("loadOAuthJS - Script load failed.");
                    reason.printStackTrace();
                    junit.framework.Assert.fail(reason.getMessage());
                }
                public void onSuccess(Void result) {
                    System.out.println("loadOAuthJS - loaded.");
                }
            }
        ).inject();        
    }
        
    
    public void testOAuthSignatureCalculation() {
        // We have to run the test in async mode to allow time for the JS scripts to load
        Timer timer = new Timer() {
            public void run() {
                // Desk calculated values - derived using this very useful checker:
                //      http://lti.tools/oauth/        
                String oauthSig = DefaultRequest.calcSignature("ck-1234",                               // Consumer Key
                                                               "cks-5678",                              // Consumer Key Secret
                                                               "oat-888",                               // OAuth token
                                                               "ts-9999",                               // OAuth token Secret
                                                               "http://localhost:8084/restapi/envs",    // URL
                                                               "1585239133",                            // test timestamp
                                                               "SsWTfF");                               // test nonce
                assertEquals("JnG2Wu8K2/BssCimEufbM/EfDIs=", oauthSig);
                finishTest();        
            }
        };

        // Set a delay period longer than this is expected to take.
        delayTestFinish(2000);
        // Schedule the test.
        timer.schedule(250);        
    }

    
    public void testOAuthSignRequest() {
        // We have to run the test in async mode to allow time for the JS scripts to load
        Timer timer = new Timer() {
            public void run() {
                String oauthSig = DefaultRequest.signRequest("ck-4321",                               // Consumer Key
                                                             "cks-8765",                              // Consumer Key Secret
                                                             "ts-888",                               // OAuth token Secret
                                                             "http://localhost:8084/restapi/envs?oauth_token=oat-9999",    // URL
                                                             "1585239133",                            // test timestamp
                                                             "SsWTfF");                               // test nonce
                assertEquals("http://localhost:8084/restapi/envs?oauth_token=oat-9999&oauth_signature_method=HMAC-SHA1&oauth_consumer_key=ck-4321&oauth_version=1.0&format=json&oauth_timestamp=1585239133&oauth_nonce=SsWTfF&oauth_signature=qlPsKL7HRoYpKVPLqhMz4c78VG8%3D", 
                             oauthSig);
                finishTest();        
            }
        };

        // Set a delay period longer than this is expected to take.
        delayTestFinish(2000);
        // Schedule the test.
        timer.schedule(250);        
    }
    
    
    public void testOAuthSHA1() {
        // We have to run the test in async mode to allow time for the JS scripts to load
        Timer timer = new Timer() {
          public void run() {
            assertEquals("E7D537E128158790157EA057BB883E0292A84930".toLowerCase(), DefaultRequest.calcSHA1("1234abcd"));
            finishTest();        
          }
        };

        
        // Set a delay period longer than this is expected to take.
        delayTestFinish(2000);
        // Schedule the test.
        timer.schedule(250);        
    }
    
}