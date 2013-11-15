package com.reversemind.glia.test.ejb;

import com.reversemind.glia.simple.Greeter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

/**
 */
@RunWith(Arquillian.class)
public class GreeterTest {

    @Inject
    Greeter greeter;

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
                .addClasses(Greeter.class, Greeter.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        // LOG.debug(jar.toString(true));
        return jar;
    }

    @Test
    public void should_create_greeting() {
        Assert.assertEquals("Hello, Earthling!", greeter.createGreeting("Earthling"));
        greeter.greet(System.out, "Earthling");
    }
}
