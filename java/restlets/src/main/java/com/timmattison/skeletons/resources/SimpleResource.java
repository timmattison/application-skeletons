package com.timmattison.skeletons.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created by timmattison on 5/27/14.
 */
public abstract class SimpleResource extends ServerResource {
    /**
     * The raw string that will contain the static content
     */
    protected String content;

    /**
     * The representation of the content suitable for sending to a client
     */
    Representation representation;

    @Get
    public Representation represent() {
        // Did we generate the content yet?
        if (content == null) {
            // No, generate it now
            generateContent();
        }

        // Return the representation of the content
        return representation;
    }

    protected abstract void generateContent();
}
