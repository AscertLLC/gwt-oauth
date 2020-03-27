package org.appfuse.gwt.oauth.client;

import org.appfuse.gwt.oauth.client.util.BaseModel;
import org.appfuse.gwt.oauth.client.util.JSOModel;


public class Status extends BaseModel {
    
    public Status(JSOModel data) {
        super(data);
    }

    public String getText() {
        return get("text");
    }
}
