package com.timmattison.skeletons.servlets;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.timmattison.skeletons.EmbeddedJetty;
import com.timmattison.skeletons.EmbeddedJettyProductionModule;
import com.timmattison.skeletons.EmbeddedJettyTestModule;
import com.timmattison.skeletons.servlets.eventbus.servlets.implementations.BasicExternalProcessEventReceiverServlet;
import com.timmattison.skeletons.servlets.eventbus.servlets.implementations.BasicExternalProcessEventSenderServlet;
import com.timmattison.skeletons.servlets.eventbus.servlets.implementations.BasicGenericEventReceiverServlet;
import com.timmattison.skeletons.servlets.eventbus.servlets.implementations.BasicGenericEventSenderServlet;
import com.timmattison.skeletons.servlets.standalone.StandaloneServlet;

/**
 * Created by timmattison on 4/4/14.
 */
public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        AbstractModule module;

        if (EmbeddedJetty.debug) {
            module = new EmbeddedJettyTestModule();
        } else {
            module = new EmbeddedJettyProductionModule();
        }

        return Guice.createInjector(module, new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/standalone").with(StandaloneServlet.class);
                serve("/basicSender").with(BasicGenericEventSenderServlet.class);
                serve("/basicReceiver").with(BasicGenericEventReceiverServlet.class);
                serve("/externalProcessSender").with(BasicExternalProcessEventSenderServlet.class);
                serve("/externalProcessReceiver").with(BasicExternalProcessEventReceiverServlet.class);
            }
        });
    }
}
