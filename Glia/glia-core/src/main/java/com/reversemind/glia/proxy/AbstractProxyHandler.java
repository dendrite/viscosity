package com.reversemind.glia.proxy;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.client.IGliaClient;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
public abstract class AbstractProxyHandler implements InvocationHandler {

    private GliaPayload makePayload(){
        return new GliaPayload();
    }

    public abstract IGliaClient getGliaClient() throws Exception;
    public abstract Class getInterfaceClass();
    public abstract void returnClient() throws Exception;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(this.getGliaClient() == null){
            System.out.println(" ^^^^^ gliaClient is NULL !!!"  + Thread.currentThread().getName());
            this.returnClient();
            throw new RuntimeException("Client is null");
        }

        synchronized (this.getGliaClient()){
            System.out.println("\n\n\n" + "!!!!!!!!!!!!!!!!\n" + "Invoke REMOTE METHOD\n\n\n");

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
            gliaPayload.setInterfaceClass(this.getInterfaceClass());

            System.out.println(" =GLIA= CREATED ON CLIENT a PAYLOAD:" + gliaPayload);
            System.out.println(" =GLIA= gliaClient:" + this.getGliaClient());
            if(this.getGliaClient() != null){
                System.out.println(" =GLIA= is running gliaClient:" + this.getGliaClient().isRunning());
            }
            assert this.getGliaClient() != null;

            // TODO need to refactor this catcher
            try{
                this.getGliaClient().send(gliaPayload);
            }catch(IOException ex){
                System.out.println(" =GLIA= gliaClient.send(gliaPayload);" + ex.getMessage());
                ex.printStackTrace();

                System.out.println("=GLIA= gliaClient going to restart a client and send again data");
                this.getGliaClient().restart();

                try{
                    this.getGliaClient().send(gliaPayload);
                }catch (IOException ex2){
                    System.out.println("After second send - exception");
                    ex2.printStackTrace();
                    this.returnClient();
                    throw new ProxySendException("=GLIA= Could not to send data into server - let's reconnect" );
                }
            }

//        System.out.println("==1");
            //Thread.sleep(100);
//        System.out.println("==2");
            long bT = System.currentTimeMillis();
            GliaPayload fromServer = this.getGliaClient().getGliaPayload();
            this.returnClient();
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


        } //synchronized

        return null;
    }

}
