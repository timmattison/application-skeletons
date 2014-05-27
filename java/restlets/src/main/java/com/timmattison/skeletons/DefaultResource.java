package com.timmattison.skeletons;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created by timmattison on 5/27/14.
 */
public class DefaultResource extends ServerResource {
    /**
     * The raw string that will contain the static content
     */
    private String content;

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

    private void generateContent() {
        // Create a string builder to efficiently build our content
        StringBuilder stringBuilder = new StringBuilder();

        // TODO - Generate content here for the root path
        stringBuilder.append("Generate content here for the root path");

        // Get the content as a string
        content = stringBuilder.toString();

        // Turn the content string data into a string representation with content-type text/html
        representation = new StringRepresentation(content, MediaType.TEXT_HTML);
    }
}
