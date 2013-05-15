package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;

import org.junit.Ignore;
import org.junit.Test;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Date: 5/15/13
 * Time: 12:40 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaServerFactoryTest  {

    /**
     * Need a payloadWorker
     */
    @Test
    public void testBuilderNoPayloadWorker() {
        IGliaServer gliaServer = null;
        try {
            gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE).build();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        assertNull(gliaServer);
    }

    /**
     * AutoSelectPort is true and port is 8000 - should be selected auto port number
     * <p></p>
     * 'Cause priority to .autoSelectPort(true)
     */
    @Test
    public void testAutoSelectPortNumber(){
        IGliaServer gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE)
                .autoSelectPort(true)
                .port(8000)
                .payloadWorker(new IGliaPayloadProcessor() {
                    @Override
                    public Map<Class, Class> getPojoMap() {
                        return null;
                    }

                    @Override
                    public void setPojoMap(Map<Class, Class> map) {
                    }

                    @Override
                    public void setEjbMap(Map<Class, String> map) {
                    }

                    @Override
                    public void registerPOJO(Class interfaceClass, Class pojoClass) {
                    }

                    @Override
                    public GliaPayload process(Object gliaPayloadObject) {
                        return null;
                    }
                }).build();

        assertNotNull(gliaServer);
        // 'cause .autoSelectPort(true)
        assertNotEquals(8000,gliaServer.getPort());


        if(gliaServer != null){
            gliaServer.shutdown();
        }
        assertNotNull(gliaServer);


        gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE)
                .autoSelectPort(true)
                .payloadWorker(new IGliaPayloadProcessor() {
                    @Override
                    public Map<Class, Class> getPojoMap() {
                        return null;
                    }

                    @Override
                    public void setPojoMap(Map<Class, Class> map) {
                    }

                    @Override
                    public void setEjbMap(Map<Class, String> map) {
                    }

                    @Override
                    public void registerPOJO(Class interfaceClass, Class pojoClass) {
                    }

                    @Override
                    public GliaPayload process(Object gliaPayloadObject) {
                        return null;
                    }
                }).build();

        assertNotNull(gliaServer);
        // TODO need correct logging
        System.out.println("port number:" + gliaServer.getPort());

        if(gliaServer != null){
            gliaServer.shutdown();
        }
        assertNotNull(gliaServer);
    }

    @Test
    public void testAssignPortNumber(){

        IGliaServer gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE)
                .payloadWorker(new IGliaPayloadProcessor() {
                    @Override
                    public Map<Class, Class> getPojoMap() {
                        return null;
                    }

                    @Override
                    public void setPojoMap(Map<Class, Class> map) {
                    }

                    @Override
                    public void setEjbMap(Map<Class, String> map) {
                    }

                    @Override
                    public void registerPOJO(Class interfaceClass, Class pojoClass) {
                    }

                    @Override
                    public GliaPayload process(Object gliaPayloadObject) {
                        return null;
                    }
                })
                .autoSelectPort(false)
                .port(8000)
                .build();

        // TODO correct logging
        System.out.println("port number:" + gliaServer.getPort());
        assertEquals(8000, gliaServer.getPort());

        if(gliaServer != null){
            gliaServer.shutdown();
        }
        assertNotNull(gliaServer);
    }

    @Test
    public void testSetNames(){

        final String NAME = "GLIA_SERVER_NAME";
        final String NAME_INSTANCE = "GLIA_SERVER_INSTANCE_NAME";

        IGliaServer gliaServer = GliaServerFactory.builder(GliaServerFactory.Builder.Type.SIMPLE)
                .payloadWorker(new IGliaPayloadProcessor() {
                    @Override
                    public Map<Class, Class> getPojoMap() {
                        return null;
                    }

                    @Override
                    public void setPojoMap(Map<Class, Class> map) {
                    }

                    @Override
                    public void setEjbMap(Map<Class, String> map) {
                    }

                    @Override
                    public void registerPOJO(Class interfaceClass, Class pojoClass) {
                    }

                    @Override
                    public GliaPayload process(Object gliaPayloadObject) {
                        return null;
                    }
                })
                .autoSelectPort(true)
                .name(NAME)
                .instanceName(NAME_INSTANCE)
                .build();

        assertEquals(NAME, gliaServer.getName());
        assertEquals(NAME_INSTANCE, gliaServer.getInstanceName());

        if(gliaServer != null){
            gliaServer.shutdown();
        }
        assertNotNull(gliaServer);
    }

    /**
     * // TODO need Netflix Curator - fake zookeeper instance
     *
     */
    @Ignore
    @Test
    public void testAutoDiscovery(){

        final String ZOOKEEPER_HOST = "localhost";
        final int ZOOKEEPER_PORT = 2181;

    }

}
