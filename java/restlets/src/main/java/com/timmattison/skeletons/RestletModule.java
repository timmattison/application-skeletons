package com.timmattison.skeletons;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Created by timmattison on 5/27/14.
 */
public class RestletModule extends AbstractModule {
    private final String inboundRoot;

    public RestletModule(String inboundRoot) {
        this.inboundRoot = inboundRoot;
    }

    @Override
    protected void configure() {
        // Use the RESTlet system
        bind(RestletApplicationFactory.class).to(BasicRestletApplicationFactory.class);

        // Use the specified inbound root
        bind(String.class).annotatedWith(Names.named(Constants.inboundRootName)).toInstance(inboundRoot);
    }
}
