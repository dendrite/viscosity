package ru.ttk.pure_java;

import ru.ttk.test.interfaces.ISimpleRemoteEJB;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 *
 */
public class PureJBossClient {

    public static void main(String[] argv) throws NamingException {

        System.out.println("New system");

        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

        jndiProperties.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        jndiProperties.put(InitialContext.PROVIDER_URL, "remote://localhost:4447");

        jndiProperties.put("remote.connections", "default");
//        jndiProperties.put("remote.connections.default.host", "localhost");
//        jndiProperties.put("remote.connections.default.port", "4447");
        jndiProperties.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
        jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS", "false");


//        remote.connections=default
//        remote.connection.default.host=localhost
//        remote.connection.default.port = 4447
//        remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED=false
//        remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS=false

        long iBeg,iEnd;
        iBeg = System.currentTimeMillis();
        final Context context = new InitialContext(jndiProperties);
        System.out.println("Time #1:" + (System.currentTimeMillis()-iBeg) );


        // The JNDI lookup name for a stateless session bean has the syntax of:
        // ejb:<appName>/<moduleName>/<distinctName>/<beanName>!<viewClassName>
        //
        // <appName> The application name is the name of the EAR that the EJB is deployed in
        //           (without the .ear).  If the EJB JAR is not deployed in an EAR then this is
        //           blank.  The app name can also be specified in the EAR's application.xml
        //
        // <moduleName> By the default the module name is the name of the EJB JAR file (without the
        //              .jar suffix).  The module name might be overridden in the ejb-jar.xml
        //
        // <distinctName> : AS7 allows each deployment to have an (optional) distinct name.
        //                  This example does not use this so leave it blank.
        //
        // <beanName>     : The name of the session been to be invoked.
        //
        // <viewClassName>: The fully qualified classname of the remote interface.  Must include
        //                  the whole package name.

//        // let's do the lookup
//        return (RemoteCalculator) context.lookup(
//                "ejb:/jboss-as-ejb-remote-server-side/CalculatorBean!" + RemoteCalculator.class.getName()
//        );



//        java:global/building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB
//        java:app/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB
//        java:module/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB
//        java:jboss/exported/building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB
//        java:global/building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB
//        java:app/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB
//        java:module/SimpleRemoteEJB

        //String path = "ejb:/building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB";
        String path = "ejb:building/building-ejb-0.5-SNAPSHOT/SimpleRemoteEJB!ru.ttk.test.interfaces.ISimpleRemoteEJB";

        // Let's lookup the remote stateless calculator
        iBeg = System.currentTimeMillis();
        final ISimpleRemoteEJB simpleRemoteEJB = (ISimpleRemoteEJB)context.lookup(path);
        System.out.println("Time #2:" + (System.currentTimeMillis()-iBeg) );

        System.out.println("Obtained a remote ");

        // invoke on the remote calculator

        iBeg = System.currentTimeMillis();
        String simpleString = simpleRemoteEJB.getNewValue("NEW VALUE");
        System.out.println("Time #3:" + (System.currentTimeMillis()-iBeg) );


        iBeg = System.currentTimeMillis();
        for(int i=0; i<100; i++){
            simpleString = simpleRemoteEJB.getNewValue(""+System.currentTimeMillis());
            System.out.println(simpleString);
        }
        iEnd = System.currentTimeMillis();
        System.out.println("Remote returned  = " + simpleString + " time:" + (iEnd-iBeg) + "ms");

    }

}
