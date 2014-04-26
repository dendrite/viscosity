package ru.ttk.camel;

import org.junit.Ignore;
import org.junit.Test;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 *
 */
public class JettyServerTest {

    private Server server;

    @Ignore
    @Test
    public void testRunServer() throws Exception {

        final int portNumber = 18081;

        server = new Server(portNumber);
        server.setStopAtShutdown(true);

        WebAppContext webAppContext = new WebAppContext();
        webAppContext.setContextPath("/rest");
        webAppContext.setResourceBase("src/main/webapp");
        webAppContext.setClassLoader(getClass().getClassLoader());
        server.addHandler(webAppContext);


        server.start();

        Thread.sleep(10 * 60 * 1000);

        server.stop();
    }

}
