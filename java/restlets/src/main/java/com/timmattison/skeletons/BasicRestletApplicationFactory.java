package com.timmattison.skeletons;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Logger;

/**
 * Created by timmattison on 5/27/14.
 */
public class BasicRestletApplicationFactory implements RestletApplicationFactory {
    private final Logger logger;
    private final String inboundRoot;
    private RestletApplication restletApplication;

    @Inject
    public BasicRestletApplicationFactory(Logger logger, @Named(Constants.inboundRootName) String inboundRoot) {
        this.logger = logger;
        this.inboundRoot = inboundRoot;
    }

    public RestletApplication create() {
        // Did we already create a RESTlet application?
        if(restletApplication == null) {
            // No, create one
            restletApplication = new RestletApplication(logger, inboundRoot);
        }

        // Return the singleton instance of the RESTlet application
        return restletApplication;
    }
}
