package com.timmattison.skeletons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.DispatcherType;
import java.io.File;
import java.net.BindException;
import java.net.URL;
import java.util.EnumSet;
import java.util.logging.Logger;

/**
 * This is an embedded Jetty container that can serve up static assets and handle GWT-RPC requests
 */
public class EmbeddedJetty {
    public static final boolean debug = false;

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

        Logger logger = injector.getInstance(Logger.class);

        String webXmlLocation = getWarUri(injector) + "/WEB-INF/web.xml";

        File webXml = new File(webXmlLocation);

        if(!webXml.exists()) {
            logger.info("Couldn't find web.xml.  Did you specify the full path?  Is it in your content directory?");
            System.exit(0);
        }

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
        try {
            server.start();
            server.join();
        } catch (BindException e) {
            logger.info("Failed to start, another application is bound on this port");
            e.printStackTrace();
            System.exit(1);
        }
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
            resourceBase = "./web";
        }

        return resourceBase;
    }
}
