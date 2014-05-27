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
        // Use the specified inbound root
        bind(String.class).annotatedWith(Names.named(Constants.inboundRootName)).toInstance(inboundRoot);

        // Use the RESTlet system (must be a singleton since it binds to a port)
        bind(RestletApplication.class).to(BasicRestletApplication.class).asEagerSingleton();

        // Use the basic running monitor (must be a singleton since it implements shared state)
        bind(RunningMonitor.class).to(BasicRunningMonitor.class).asEagerSingleton();
    }
}
