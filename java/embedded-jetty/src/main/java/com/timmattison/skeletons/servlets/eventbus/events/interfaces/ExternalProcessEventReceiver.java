package com.timmattison.skeletons.servlets.eventbus.events.interfaces;

/**
 * Created by timmattison on 6/10/14.
 */
public interface ExternalProcessEventReceiver {
    public void onExternalProcessEvent(ExternalProcessEvent externalProcessEvent);
}
