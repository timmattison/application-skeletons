package com.timmattison.skeletons.servlets.eventbus.servlets.implementations;

import com.google.common.eventbus.EventBus;
import com.google.gson.Gson;
import com.timmattison.skeletons.servlets.eventbus.events.factories.ExternalProcessEventFactory;
import com.timmattison.skeletons.servlets.eventbus.events.interfaces.ExternalProcessEvent;
import com.timmattison.skeletons.servlets.eventbus.servlets.interfaces.GenericEventSenderServlet;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by timmattison on 6/10/14.
 */
@Singleton
public class BasicExternalProcessEventSenderServlet extends HttpServlet implements GenericEventSenderServlet {
    private final EventBus eventBus;
    private final ExternalProcessEventFactory externalProcessEventFactory;
    private static final String processToRun1 = "/usr/bin/env";
    private static final String processToRun2 = "ls";

    @Inject
    public BasicExternalProcessEventSenderServlet(EventBus eventBus, ExternalProcessEventFactory externalProcessEventFactory) {
        this.eventBus = eventBus;
        this.externalProcessEventFactory = externalProcessEventFactory;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            Runtime runtime = Runtime.getRuntime();
            String[] commands = { processToRun1, processToRun2 };

            Process proc = runtime.exec(commands);

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

            List<String> output = new ArrayList<String>();
            List<String> errors = new ArrayList<String>();

            String line;

            while ((line = stdInput.readLine()) != null) {
                output.add(line);
            }

            while ((line = stdError.readLine()) != null) {
                errors.add(line);
            }

            int exitVal = proc.waitFor();

            ExternalProcessEvent externalProcessEvent = externalProcessEventFactory.create(processToRun2, exitVal, output, errors);

            eventBus.post(externalProcessEvent);

            Gson gson = new Gson();
            String json = gson.toJson(externalProcessEvent);

            httpServletResponse.getOutputStream().write(json.getBytes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Close the output stream no matter what
            httpServletResponse.getOutputStream().close();
        }
    }
}
