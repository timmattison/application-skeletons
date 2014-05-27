package com.timmattison.skeletons.servlets.eventbus.events.factories;

import com.google.inject.assistedinject.Assisted;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEvent;

import java.util.List;

/**
 * Created by timmattison on 6/10/14.
 */
public interface ExternalProcessEventFactory {
    public ExternalProcessEvent create(@Assisted("name") String name, @Assisted("exitStatus") int exitStatus, @Assisted("output") List<String> output, @Assisted("errors") List<String> errors);
}
