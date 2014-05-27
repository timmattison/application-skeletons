package com.timmattison.skeletons;

/**
 * Created by timmattison on 5/27/14.
 */
public class StaticJQueryResource extends StaticInternalResource {
    public static final String resourceName = "jquery-2.1.1.min.js";

    @Override
    protected String getResourceName() {
        return resourceName;
    }
}
