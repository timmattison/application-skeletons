package com.timmattison.skeletons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.net.URL;
import java.util.EnumSet;

/**
 * This is an embedded Jetty container that can serve up static assets and handle GWT-RPC requests
 */
public class EmbeddedJetty {
    private static final boolean debug = false;

    public static void main(String[] args) throws Throwable {
        Injector injector;

        if ((args == null) || (args.length == 0) || (args[0] == null)) {
            System.out.println("You must specify the location of the content");
            return;
        }

        String resourceBase = args[0];

        if (debug) {
            injector = Guice.createInjector(new EmbeddedJettyTestModule());
        } else {
            injector = Guice.createInjector(new EmbeddedJettyProductionModule());
        }

        String webXmlLocation = getWarUri(injector) + "/WEB-INF/web.xml";

        // Create an embedded Jetty server on port 8080
        Server server = new Server(8080);

        // Create a handler that works for our GWT code
        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setResourceBase(resourceBase);
        webAppContext.setDescriptor(webXmlLocation);
        webAppContext.setContextPath("/");
        webAppContext.setParentLoaderPriority(true);

        // Add Guice in via GuiceFilter
        webAppContext.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));

        // Add it to the server
        server.setHandler(webAppContext);

        // And start it up
        server.start();
        server.join();
    }

    private static String getWarUri(Injector injector) {
        Class clazz = injector.getClass();
        ClassLoader classLoader = clazz.getClassLoader();
        URL resource = classLoader.getResource("war");

        String resourceBase;

        // Did we find the resource?
        if (resource != null) {
            // Yes, we are inside of a JAR file
            String externalForm = resource.toExternalForm();
            resourceBase = externalForm;
        } else {
            // No, we are debugging
            resourceBase = "./war";
        }

        return resourceBase;
    }
}
