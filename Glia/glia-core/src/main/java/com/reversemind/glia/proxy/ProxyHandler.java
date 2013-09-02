package com.reversemind.glia.proxy;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.GliaPayloadStatus;
import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.IGliaClient;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Date: 4/24/13
 * Time: 4:33 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class ProxyHandler implements InvocationHandler {

    private IGliaClient gliaClient;
    private Class interfaceClass;

    public ProxyHandler(IGliaClient gliaClient, Class interfaceClass){
        this.gliaClient = gliaClient;
        this.interfaceClass = interfaceClass;
    }

    private GliaPayload makePayload(){
        return new GliaPayload();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(this.gliaClient == null){
            System.out.println(" ^^^^^ gliaClient is NULL !!!");
            throw new RuntimeException("Client is null");
        }

        synchronized (this.gliaClient){
            System.out.println("\n\n\n" +
                    "!!!!!!!!!!!!!!!!\n" +
                    "Invoke REMOTE METHOD\n\n\n");

            System.out.println("Method:" + method.getName());
            if(args != null && args.length > 0){
                for(Object obj: args){
                    if(obj != null)
                    System.out.println("arguments: " + obj.getClass().getCanonicalName() + " value:" + obj);
                }
            }

            if(method.getName().equalsIgnoreCase("toString")){
                return "You are calling for toString method - it's not a good idea :)";
            }

            GliaPayload gliaPayload = this.makePayload();

            gliaPayload.setClientTimestamp(System.currentTimeMillis());
            gliaPayload.setMethodName(method.getName());
            gliaPayload.setArguments(args);
            gliaPayload.setInterfaceClass(this.interfaceClass);

            System.out.println(" =GLIA= CREATED ON CLIENT a PAYLOAD:" + gliaPayload);
            System.out.println(" =GLIA= gliaClient:" + gliaClient);
            if(gliaClient != null){
                System.out.println(" =GLIA= is running gliaClient:" + gliaClient.isRunning());
            }
            assert gliaClient != null;


            // TODO need to refactor this catcher
            try{
                gliaClient.send(gliaPayload);
            }catch(IOException ex){
                System.out.println(" =GLIA= gliaClient.send(gliaPayload);" + ex.getMessage());
                ex.printStackTrace();

                System.out.println("=GLIA= gliaClient going to restart a client and send again data");
                gliaClient.restart();

                try{
                    gliaClient.send(gliaPayload);
                }catch (IOException ex2){
                    System.out.println("After second send - exception");
                    ex2.printStackTrace();
                    throw new ProxySendException("=GLIA= Could not to send data into server - let's reconnect" );
                }
            }

//        System.out.println("==1");
            //Thread.sleep(100);
//        System.out.println("==2");
            long bT = System.currentTimeMillis();
            GliaPayload fromServer = gliaClient.getGliaPayload();

            if(fromServer.getThrowable() != null){

                // TODO What if impossible to load a specific Class

                Constructor constructor = fromServer.getThrowable().getCause().getClass().getConstructor(new Class[]{String.class});
                String[] exceptionMessage = {fromServer.getThrowable().getCause().getMessage()};
                throw (Throwable) constructor.newInstance(exceptionMessage);
            }

            System.out.println("==2.5 Get back from server for:" + (System.currentTimeMillis() - bT) + " ms");

//        System.out.println("==3");

//        System.out.println(fromServer);
//        System.out.println("==4");
            if(fromServer!=null && fromServer.getStatus() != null){
//            System.out.println("==5");
//            System.out.println("fromServer.getResultResponse()\n\n" + fromServer.getResultResponse());
//            System.out.println("==6");
                return fromServer.getResultResponse();
            }
//        System.out.println("==7");
        }


        return null;
    }
}
