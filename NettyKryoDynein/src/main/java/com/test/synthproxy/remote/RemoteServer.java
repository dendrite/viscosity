package com.test.synthproxy.remote;

import com.test.synthproxy.local.PCity;
import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.shared.KryoPayload;
import com.test.synthproxy.shared.KryoSerializer;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RemoteServer {

    private Map<Class,Class> mapRegisteredInterfaces = new HashMap<Class, Class>();

    {
        mapRegisteredInterfaces.put(IPCity.class, CityServer.class);
    }


    public byte[] conversation(byte[] bytesIn){
        KryoPayload kryoPayload = new KryoSerializer().deserialize(bytesIn);

        if(kryoPayload != null){
            Class interfaceClass = kryoPayload.getInterfaceClass();

            Class toProcess = mapRegisteredInterfaces.get(interfaceClass);
            Method[] methods = toProcess.getMethods();
            Method selectedMethod = null;
            for(Method method: methods){
                System.out.println(method.getName() + "=" + method.getParameterTypes());
                if(method.getName().equals(kryoPayload.getMethodName())){
                    selectedMethod = method;
                    break;
                }
            }

            if(selectedMethod != null){
                try {
                    Object result = selectedMethod.invoke(new CityServer(), kryoPayload.getArgs());
                    kryoPayload.setResult(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        byte[] byteOut = new KryoSerializer().serialize(kryoPayload);
        return byteOut;
    }

}
