package com.timmattison.skeletons;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import javax.inject.Inject;

/**
 * Created by timmattison on 5/27/14.
 */
public abstract class InternalEventBusModule extends AbstractModule {
    protected final EventBus eventBus;

    @Inject
    public InternalEventBusModule(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    // From: http://spin.atomicobject.com/2012/01/13/the-guava-eventbus-on-guice/
    protected void bindSubscribersToEventBus(final EventBus eventBus) {
        bindListener(Matchers.any(), new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register(new InjectionListener<I>() {
                    public void afterInjection(I i) {
                        eventBus.register(i);
                    }
                });
            }
        });
    }
}
