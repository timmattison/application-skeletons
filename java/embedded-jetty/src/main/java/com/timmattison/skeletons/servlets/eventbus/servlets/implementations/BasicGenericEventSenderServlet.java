package com.timmattison.skeletons.servlets.eventbus.servlets.implementations;

import com.google.common.eventbus.EventBus;
import com.timmattison.skeletons.servlets.eventbus.servlets.interfaces.GenericEventSenderServlet;
import com.timmattison.skeletons.servlets.eventbus.events.factories.GenericEventFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by timmattison on 6/10/14.
 */
@Singleton
public class BasicGenericEventSenderServlet extends HttpServlet implements GenericEventSenderServlet {
    private final EventBus eventBus;
    private final GenericEventFactory genericEventFactory;

    @Inject
    public BasicGenericEventSenderServlet(EventBus eventBus, GenericEventFactory genericEventFactory) {
        this.eventBus = eventBus;
        this.genericEventFactory = genericEventFactory;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            String input = httpServletRequest.getParameter("input");

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Generic event emitting servlet hit, message posted to bus");

            if (input != null) {
                stringBuilder.append(" [");
                stringBuilder.append(input);
                stringBuilder.append("]");
            }

            String message = stringBuilder.toString();

            eventBus.post(genericEventFactory.create(message));
            httpServletResponse.getOutputStream().write(message.getBytes());
        } finally {
            // Close the output stream no matter what
            httpServletResponse.getOutputStream().close();
        }
    }
}
