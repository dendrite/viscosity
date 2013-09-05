package com.test.test;

import ejb.client.ClientSimple;
import ejb.server.GliaSimpleServer;
import ejb.server.service.SimpleService;
import ejb.shared.ISimpleService;
import ejb.zookeeper.RunZookeeper;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 *
 */
@RunWith(Arquillian.class)
public class ClientServerEJBTest {

    @Inject
    ISimpleService simpleService;

    @Inject
    ClientSimple clientSimple;

    @Deployment
    @TargetsContainer("jbossas-managed")
    public static Archive<?> createDeployment() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        // Не стучимся на remote репы
        resolver.goOffline();

        WebArchive archive = ShrinkWrap.create(WebArchive.class, "ServerTest.war")
                // tip:
                // .artifact("GROUPID:ARTIFACTID:TYPE:VERSION")
                .addAsLibraries(resolver
                        .artifact("org.springframework:spring-core:3.0.7.RELEASE")
                        .artifact("org.springframework:spring-context:3.0.7.RELEASE")

                        .artifact("postgresql:postgresql:9.1-901.jdbc4")

                        .artifact("org.apache.commons:commons-lang3:3.1")

                        .artifact("log4j:log4j:1.2.16")

                        .artifact("com.reversemind:glia-core:1.7.5-SNAPSHOT")

                        .artifact("net.sf.dozer:dozer:5.4.0")
                        .artifact("com.google.code.gson:gson:2.2.4")
                        .artifact("com.google.guava:guava:14.0.1")

                        .resolveAsFiles())

                .addPackages(true, ISimpleService.class.getPackage())
                .addPackages(true, SimpleService.class.getPackage())
                .addPackages(true, GliaSimpleServer.class.getPackage())
                .addPackages(true, RunZookeeper.class.getPackage())
                .addPackages(true, ClientSimple.class.getPackage())

                .addAsResource("META-INF/glia-interface-map.xml", "META-INF/glia-interface-map.xml")
                .addAsResource("META-INF/glia-server-context.xml", "META-INF/glia-server-context.xml")
                .addAsResource("META-INF/glia-server.properties", "META-INF/glia-server.properties")

                .addAsResource("META-INF/glia-client-context.xml", "META-INF/glia-client-context.xml")
                .addAsResource("META-INF/glia-client.properties", "META-INF/glia-client.properties")

                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        System.out.println("archive:" + archive.toString(true));
        return archive;
    }

    @Test
    public void testSimple() throws InterruptedException {
        System.out.println("############");
        System.out.println("info:" + simpleService.functionNumberOne("1","2"));
    }

    @Test
    public void testClient() throws Exception {
        ISimpleService proxyService = clientSimple.getProxy(ISimpleService.class);
        System.out.println("proxyService: " + proxyService.functionNumberOne("1","2"));
    }

}
