package com.timmattison.skeletons.resources;

import org.restlet.data.MediaType;
import org.restlet.representation.StringRepresentation;

import javax.inject.Inject;

/**
 * Created by timmattison on 5/28/14.
 */
public class StopSimpleResource extends SimpleResource {
    @Inject
    public StopSimpleResource
    @Override
    protected void generateContent() {
        // Create a string builder to efficiently build our content
        StringBuilder stringBuilder = new StringBuilder();

        // Generate the "Hello World!" message
        stringBuilder.append("Hello World!  First executed at ");
        stringBuilder.append(System.currentTimeMillis());
        stringBuilder.append(".");

        // Get the content as a string
        content = stringBuilder.toString();

        // Turn the content string data into a string representation with content-type text/html
        representation = new StringRepresentation(content, MediaType.TEXT_HTML);
    }
}
