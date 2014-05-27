package com.timmattison.skeletons.servlets.eventbus.events.interfaces;

/**
 * Created by timmattison on 6/10/14.
 */
public interface GenericEventReceiver {
    public void onEvent(GenericEvent genericEvent);
}
