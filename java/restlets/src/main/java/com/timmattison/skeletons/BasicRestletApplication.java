package com.timmattison.skeletons;

import com.timmattison.skeletons.resources.HelloWorldSimpleResource;
import com.timmattison.skeletons.resources.StaticJQueryResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * Created by timmattison on 5/27/14.
 */
public class BasicRestletApplication extends Application implements RestletApplication {
    private final Logger logger;
    private final String inboundRoot;
    private final RunningMonitor runningMonitor;

    @Inject
    public BasicRestletApplication(Logger logger, RunningMonitor runningMonitor, @Named(Constants.inboundRootName) String inboundRoot) {
        this.logger = logger;
        this.runningMonitor = runningMonitor;
        this.inboundRoot = inboundRoot;
    }

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call
        Router router = new Router(getContext());
        //router.setDefaultMatchingMode(Router.MODE_BEST_MATCH);

        // NOTE: Map your dynamic paths before you map the static directory

        // Serve jQuery from a local copy
        router.attach(StaticJQueryResource.resourceName, StaticJQueryResource.class);

        // A "Hello, World" path
        router.attach("/HelloWorld", HelloWorldSimpleResource.class);

        // Map the static directory
        mapStaticDirectory(router);

        // Other options that might be useful:

        //   Use exact matching to support serving full directories:
        //     https://stackoverflow.com/questions/150522/restlet-serving-up-static-content
        //     staticDirectory.setMatchingMode(Template.MODE_EQUALS);

        return router;
    }

    private void mapStaticDirectory(Router router) {
        // Serve the static directory
        Directory directory;

        try {
            directory = new Directory(getContext(), inboundRoot);
        } catch (IllegalArgumentException e) {
            // A common mistake, help the user out
            logger.info("Did you prefix the inbound root directory name with 'file:///'?");
            throw (e);
        }

        // Allow directory listing
        directory.setListingAllowed(true);

        // Attach this directory to the root
        router.attach("/", directory);
    }

    @Override
    public Application getApplication() {
        return this;
    }
}
