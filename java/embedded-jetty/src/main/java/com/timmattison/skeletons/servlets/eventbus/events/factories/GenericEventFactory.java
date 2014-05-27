package com.timmattison.skeletons.servlets.eventbus.events.factories;

import com.google.inject.assistedinject.Assisted;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.GenericEvent;

/**
 * Created by timmattison on 6/10/14.
 */
public interface GenericEventFactory {
    public GenericEvent create(@Assisted("data") Object data);
}
