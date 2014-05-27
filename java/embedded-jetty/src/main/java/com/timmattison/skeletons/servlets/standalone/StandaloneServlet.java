package com.timmattison.skeletons.servlets.standalone;

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
public class StandaloneServlet extends HttpServlet {
    private int counter;

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        try {
            String output = "Counter: " + counter;
            httpServletResponse.getOutputStream().write(output.getBytes());
            counter++;
        } finally {
            // Close the output stream no matter what
            httpServletResponse.getOutputStream().close();
        }
    }
}
