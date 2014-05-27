package com.timmattison.skeletons.servlets.eventbus.events.implementations;

import com.google.inject.assistedinject.Assisted;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEvent;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timmattison on 6/10/14.
 */
public class BasicExternalProcessEvent implements ExternalProcessEvent {
    private final String name;
    private final int exitStatus;
    private final List<String> output;
    private final List<String> errors;

    @Inject
    public BasicExternalProcessEvent(@Assisted("name") String name, @Assisted("exitStatus") int exitStatus, @Assisted("output") List<String> output, @Assisted("errors") List<String> errors) {
        this.name = name;
        this.exitStatus = exitStatus;
        this.output = output;
        this.errors = errors;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getExitStatus() {
        return exitStatus;
    }

    @Override
    public List<String> getOutput() {
        return output;
    }

    @Override
    public List<String> getErrors() {
        return errors;
    }
}
