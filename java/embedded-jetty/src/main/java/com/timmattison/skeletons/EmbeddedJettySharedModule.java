package com.timmattison.skeletons;

import com.google.common.eventbus.EventBus;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.timmattison.skeletons.servlets.eventbus.events.factories.ExternalProcessEventFactory;
import com.timmattison.skeletons.servlets.eventbus.events.factories.GenericEventFactory;
import com.timmattison.skeletons.servlets.eventbus.events.implementations.BasicExternalProcessEvent;
import com.timmattison.skeletons.servlets.eventbus.events.implementations.BasicGenericEvent;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEvent;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.GenericEvent;

import javax.inject.Inject;
import java.util.logging.*;

/**
 * Created by timmattison on 5/27/14.
 */
public class EmbeddedJettySharedModule extends InternalEventBusModule {
    @Inject
    public EmbeddedJettySharedModule(EventBus eventBus) {
        super(eventBus);
    }

    @Override
    protected void configure() {
        configureLogger();

        // Bind all subscribers to the event bus automatically
        bindSubscribersToEventBus(eventBus);

        // Install the event factories
        install(new FactoryModuleBuilder().implement(GenericEvent.class, BasicGenericEvent.class).build(GenericEventFactory.class));
        install(new FactoryModuleBuilder().implement(ExternalProcessEvent.class, BasicExternalProcessEvent.class).build(ExternalProcessEventFactory.class));
    }

    public static void configureLogger() {
        // Get the logger instance
        LogManager logManager = LogManager.getLogManager();
        Logger rootLogger = logManager.getLogger("");
        rootLogger.setLevel(Level.ALL);

        // Loop and see if a console handler is already installed
        boolean consoleHandlerInstalled = false;

        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                consoleHandlerInstalled = true;
                break;
            }
        }

        // Is a console handler already installed?
        if (consoleHandlerInstalled) {
            // Yes, skip it
            return;
        }

        // No console handler installed, install one
        rootLogger.addHandler(new ConsoleHandler());
    }
}
