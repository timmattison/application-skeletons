package com.timmattison.skeletons;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.restlet.Component;
import org.restlet.data.Protocol;

/**
 * Created by timmattison on 5/27/14.
 */
public class RestletSkeleton {
    public static void main(String[] args) throws Exception {
        if ((args == null) || (args.length == 0) || (args[0] == null)) {
            System.out.println("You must specify the location of the inbound root");
            return;
        }

        String inboundRoot = args[0];

        // Create the injector
        Injector injector = Guice.createInjector(new RestletModule(inboundRoot));

        // Create a factory to instantiate the RESTlet application
        RestletApplicationFactory restletApplicationFactory = injector.getInstance(RestletApplicationFactory.class);

        try {
            // Create a new Component.
            Component component = new Component();

            // Add a new HTTP server listening on the specified port
            component.getServers().add(Protocol.HTTP, 8000);

            // Attach the sample application.
            component.getDefaultHost().attach(restletApplicationFactory.create());

            // Start the component.
            component.start();
        } catch (Exception e) {
            // Something is wrong.
            e.printStackTrace();
        }

        Thread.sleep(100000);
    }
}
