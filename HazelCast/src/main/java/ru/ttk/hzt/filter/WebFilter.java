package ru.ttk.hzt.filter;

import com.google.common.io.Closeables;
import com.netflix.curator.framework.CuratorFramework;
import com.netflix.curator.framework.CuratorFrameworkFactory;
import com.netflix.curator.retry.ExponentialBackoffRetry;
import com.netflix.curator.test.Timing;

import javax.servlet.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.IOException;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 */
public class WebFilter implements Filter {

    private CuratorFramework curatorClientInstance;

    private static final ConcurrentMap<String, String> mapOriginalSessions = new ConcurrentHashMap<String, String>(1000);
    private static final ConcurrentMap<String, ZookeeperHttpSession> mapSessions = new ConcurrentHashMap<String, ZookeeperHttpSession>(1000);

    protected ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        this.servletContext = filterConfig.getServletContext();



        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3); // new RetryOneTime(1)

        int connectionTimeoutMs = new Timing().connection();
        int sessionTimeoutMs = new Timing().session();

        String host = "localhost";
        int port = 2181;
        String connectionString = host + ":" + port;

        Timing timing = new Timing();
        System.out.println("timing.session() - " + timing.session());
        System.out.println("timing.connection() - " + timing.connection());

        curatorClientInstance = CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                .build();

        curatorClientInstance.start();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void destroy() {
        if (this.curatorClientInstance != null) {
            Closeables.closeQuietly(this.curatorClientInstance);
        }
    }










    private class ZookeeperHttpSession implements HttpSession {

        volatile boolean valid = true;
        final String id;
        final HttpSession originalSession;
        final WebFilter webFilter;

        private ZookeeperHttpSession(String id, HttpSession originalSession, WebFilter webFilter) {
            this.id = id;
            this.originalSession = originalSession;
            this.webFilter = webFilter;
        }

        @Override
        public long getCreationTime() {
            return this.originalSession.getCreationTime();
        }

        @Override
        public String getId() {
            return this.id;
        }

        @Override
        public long getLastAccessedTime() {
            return this.originalSession.getLastAccessedTime();
        }

        @Override
        public ServletContext getServletContext() {
            return servletContext;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {
            this.originalSession.setMaxInactiveInterval(interval);
        }

        @Override
        public int getMaxInactiveInterval() {
            return this.originalSession.getMaxInactiveInterval();
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getAttribute(String name) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Object getValue(String name) {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String[] getValueNames() {
            return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setAttribute(String name, Object value) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void putValue(String name, Object value) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void removeAttribute(String name) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void removeValue(String name) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void invalidate() {
            this.originalSession.invalidate();
        }

        @Override
        public boolean isNew() {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
