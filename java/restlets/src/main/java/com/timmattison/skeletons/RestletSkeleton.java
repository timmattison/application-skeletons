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

        try {
            // Create a new Component
            Component component = new Component();

            // Add a new HTTP server listening on the specified port
            component.getServers().add(Protocol.HTTP, 8000);

            // Required to get the inbound root working
            component.getClients().add(Protocol.FILE);

            // Attach the application
            RestletApplication restletApplication = injector.getInstance(RestletApplication.class);
            component.getDefaultHost().attach(restletApplication.getApplication());

            // Start the component
            component.start();
        } catch (Exception e) {
            // Something is wrong
            e.printStackTrace();
        }

        RunningMonitor runningMonitor = injector.getInstance(RunningMonitor.class);

        // Wait until someone signals we are stopping
        runningMonitor.waitUntilStopping();
    }
}
