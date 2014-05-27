package com.timmattison.skeletons;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;

/**
 * Created by timmattison on 5/27/14.
 */
public class EmbeddedJettyTestModule extends AbstractModule {
    private final EventBus eventBus = new EventBus("Debug EventBus");

    @Override
    protected void configure() {
        // Create a singleton event bus
        bind(EventBus.class).toInstance(eventBus);

        // Install all of the submodules
        install(new EmbeddedJettySharedModule(eventBus));
    }
}
