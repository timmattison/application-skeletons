package com.timmattison.skeletons;

import org.apache.commons.io.IOUtils;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by timmattison on 5/27/14.
 */
public abstract class StaticInternalResource extends ServerResource {
    @Get
    public Representation represent() throws IOException {
        // Return the string representation of this internal resource
        return new StringRepresentation(getInternalResource(getResourceName()));
    }

    private String getInternalResource(String name) throws IOException {
        // Get the resource URL from inside the JAR
        URL url = this.getClass().getResource("/" + name);

        // Open an input stream from the URL
        InputStream inputStream = url.openStream();

        // Return the entire input stream to the caller
        return IOUtils.toString(inputStream);
    }

    /**
     * The name of the resource that is being served from inside the JAR
     * @return
     */
    protected abstract String getResourceName();
}
