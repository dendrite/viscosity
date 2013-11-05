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
    public abstract void returnClient(IGliaClient gliaClient) throws Exception;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        IGliaClient localGliaClient = null;
        synchronized (this.getGliaClient()){
            localGliaClient = this.getGliaClient();
        }

        if(localGliaClient == null){
            System.out.println(" ^^^^^ gliaClient is NULL !!!"  + Thread.currentThread().getName());
            this.returnClient();
            throw new RuntimeException("Client is null");
        }

        synchronized (localGliaClient){
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
            System.out.println(" =GLIA= gliaClient:" + localGliaClient);
            if(localGliaClient != null){
                System.out.println(" =GLIA= is running gliaClient:" + this.getGliaClient().isRunning());
            }
//            assert this.getGliaClient() != null;

            // TODO need to refactor this catcher
            try{
                localGliaClient.send(gliaPayload);
            }catch(IOException ex){
                System.out.println(" =GLIA= gliaClient.send(gliaPayload);" + ex.getMessage());
                ex.printStackTrace();

                System.out.println("=GLIA= gliaClient going to restart a client and send again data");
                localGliaClient.restart();

                try{
                    localGliaClient.send(gliaPayload);
                }catch (IOException ex2){
                    System.out.println("After second send - exception");
                    ex2.printStackTrace();
                    this.returnClient(localGliaClient);
                    throw new ProxySendException("=GLIA= Could not to send data into server - let's reconnect" );
                }
            }

            long bT = System.currentTimeMillis();
            GliaPayload fromServer = localGliaClient.getGliaPayload();
            this.returnClient(localGliaClient);
            if(fromServer.getThrowable() != null){
                // TODO What if impossible to load a specific Class
                if(fromServer.getThrowable().getCause() == null){
                    Constructor constructor =  fromServer.getThrowable().getClass().getConstructor(new Class[]{String.class});
                    String[] exceptionMessage = {fromServer.getThrowable().getMessage()};
                    throw (Throwable) constructor.newInstance(exceptionMessage);
                }else{
                    Constructor constructor = fromServer.getThrowable().getCause().getClass().getConstructor(new Class[]{String.class});
                    String[] exceptionMessage = {fromServer.getThrowable().getCause().getMessage()};
                    throw (Throwable) constructor.newInstance(exceptionMessage);
                }
            }

            System.out.println("==2.5 Get back from server for:" + (System.currentTimeMillis() - bT) + " ms");

            if(fromServer!=null && fromServer.getStatus() != null){
                return fromServer.getResultResponse();
            }

        } //synchronized

        this.returnClient(localGliaClient);

        return null;
    }

}
