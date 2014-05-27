package com.timmattison.skeletons;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Created by timmattison on 5/27/14.
 */
public class EmbeddedJettyServletConfig {
    public class GuiceServletConfig extends GuiceServletContextListener {
        @Override
        protected Injector getInjector() {
            /*
            return Guice.createInjector(new AbstractModule(), new ServletModule() {
                @Override
                protected void configureServlets() {
                    // TODO - BIND SERVICE IMPL HERE
                    bind(gwtServiceImpl.class).asEagerSingleton();
                    serve("/gwt/gwtService").with(gwtServiceImpl.class);
                }
            });
            */

            return null;
        }
    }

}
