package com.timmattison.skeletons.servlets.eventbus.servlets.implementations;

import com.google.common.eventbus.Subscribe;
import com.google.gson.Gson;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEvent;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEventReceiver;

import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by timmattison on 6/10/14.
 */
@Singleton
public class BasicExternalProcessEventReceiverServlet extends HttpServlet implements ExternalProcessEventReceiver {
    private Map<String, Long> counters = new HashMap<String, Long>();

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            Gson gson = new Gson();

            String countersJson = gson.toJson(counters);
            httpServletResponse.getOutputStream().write(countersJson.getBytes());
        } finally {
            // Close the output stream no matter what
            httpServletResponse.getOutputStream().close();
        }
    }

    @Subscribe
    @Override
    public void onExternalProcessEvent(ExternalProcessEvent externalProcessEvent) {
        if (externalProcessEvent == null) {
            return;
        }

        Gson gson = new Gson();
        String json = gson.toJson(externalProcessEvent);

        Long count = counters.get(json);

        if (count == null) {
            count = 0L;
        }

        count++;

        counters.put(json, count);
    }
}
