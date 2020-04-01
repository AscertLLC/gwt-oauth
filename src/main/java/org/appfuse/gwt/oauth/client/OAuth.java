package org.appfuse.gwt.oauth.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

import org.appfuse.gwt.oauth.client.service.DefaultRequest;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class OAuth implements EntryPoint {

    // Bit braindead this startup process
    public void onModuleLoad() {
        new OAuthPage();
    }
       
}
