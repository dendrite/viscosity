package ru.ttk.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.ejb.EjbComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;
import java.util.Properties;

/**
 * A Camel Application
 */
public class CamelRemoteEJB {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {

        // create CamelContext
        CamelContext camelContext = createCamelContext();

        System.out.println(camelContext);

        // add our route to the CamelContext
        camelContext.addRoutes(new RouteBuilder() {
            public void configure() {

                    from("stream:in?promptMessage=Press ENTER to start")
                        .bean(new SuperTime(),"time")
                        .to("stream:out")
                        .loop(100)
                            //.transform(body().append("B"))
//                            .bean(new SuperTime(),"time")
//                            .to("stream:out")

                                .to("ejb:building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB?method=getNewValue(" + System.currentTimeMillis() + ")")
                                //.to("stream:out")

//                            .bean(new SuperTime(),"time")
//                            .to("stream:out")
                        .end()
                        .bean(new SuperTime(), "time")
                        .to("stream:out")
                        .end();


                    //.to("string-template:SimpleText");



                    //.to("mock:result");
                    //.to("ejb:ISimpleRemoteEJB?method=getNewOld")

//                from("stream:in?promptMessage=Enter something:")
//                        .to("stream:out");


//                from("direct:start")
//                    //.to("ejb:ISimpleRemoteEJB?method=getNewValue(sdfdsf)")
//                    //.to("ejb:ISimpleRemoteEJB?method=getNewOld")
//                    //;
//                    .to("stream:out");
//                    //.to("mock:result");
            }
        });

//        from("direct:start")
//                // invoke the greeter EJB using the local interface and invoke the hello method
//                .to("ejb:GreaterImplLocal?method=hello")
//                .to("mock:result");

        // start the route and let it do its work
        camelContext.start();


//        do{
//
//        }while(!camelContext.isSuspended());

        Thread.sleep(20000);


        //camelContext.isSuspended()
        // stop the CamelContext
        camelContext.stop();

//        System.out.println("NEW START!!!!");
//        camelContext.start();
//        Thread.sleep(20000);
//        camelContext.stop();


    }

    public static CamelContext createCamelContext() throws Exception {
        CamelContext answer = new DefaultCamelContext();

        // enlist EJB component using the JndiContext
        EjbComponent ejb = answer.getComponent("ejb", EjbComponent.class);
        ejb.setContext(createEjbContext());

        return answer;
    }

    private static Context createEjbContext() throws NamingException {

        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        jndiProperties.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(InitialContext.PROVIDER_URL, "remote://localhost:4447");

        jndiProperties.put("remote.connections", "default");
//        jndiProperties.put("remote.connections.default.host", "localhost");
//        jndiProperties.put("remote.connections.default.port", "4447");
        jndiProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");

        return new InitialContext(jndiProperties);


//        // here we need to define our context factory to use OpenEJB for our testing
//        Properties properties = new Properties();
//        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");
//
//        return new InitialContext(properties);
    }

}

