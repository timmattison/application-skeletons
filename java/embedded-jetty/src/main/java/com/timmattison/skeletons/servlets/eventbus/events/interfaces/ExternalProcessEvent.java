package com.timmattison.skeletons.servlets.eventbus.events.interfaces;

import java.util.List;

/**
 * Created by timmattison on 6/10/14.
 */
public interface ExternalProcessEvent {
    public String getName();
    public int getExitStatus();
    public List<String> getOutput();
    public List<String> getErrors();
}
