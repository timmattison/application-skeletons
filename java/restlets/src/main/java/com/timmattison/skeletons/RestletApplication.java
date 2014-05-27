package com.timmattison.skeletons;

import com.google.inject.assistedinject.Assisted;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;
import org.restlet.routing.Template;
import org.restlet.routing.TemplateRoute;

import javax.inject.Inject;
import java.util.logging.Logger;

/**
 * Created by timmattison on 5/27/14.
 */
public class RestletApplication extends Application {
    private final Logger logger;
    private final String inboundRoot;

    @Inject
    public RestletApplication(Logger logger, @Assisted(Constants.inboundRootName) String inboundRoot) {
        this.logger = logger;
        this.inboundRoot = inboundRoot;
    }

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public Restlet createInboundRoot() {
        // Create a router Restlet that routes each call
        Router router = new Router(getContext());

        // A simple default path that doesn't do much
        router.attachDefault(DefaultResource.class);

        // Serve jQuery from a local copy
        router.attach(StaticJQueryResource.resourceName, StaticJQueryResource.class);

        // Serve the static directory
        Directory directory;

        try {
            directory = new Directory(getContext(), inboundRoot);
        } catch (IllegalArgumentException e) {
            // A common mistake, help the user out
            logger.info("Did you prefix the inbound root directory name with 'file:///'?");
            throw(e);
        }

        TemplateRoute staticDirectory = router.attach(directory);

        // Use exact matching to support serving full directories:
        //   https://stackoverflow.com/questions/150522/restlet-serving-up-static-content
        staticDirectory.setMatchingMode(Template.MODE_EQUALS);

        return router;
    }
}
