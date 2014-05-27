package com.timmattison.skeletons.servlets.eventbus.events.implementations;

import com.google.inject.assistedinject.Assisted;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.GenericEvent;

import javax.inject.Inject;

/**
 * Created by timmattison on 6/10/14.
 */
public class BasicGenericEvent implements GenericEvent {
    private final Object data;

    @Inject
    public BasicGenericEvent(@Assisted("data") Object data) {
        this.data = data;
    }

    @Override
    public Object getData() {
        return data;
    }
}
