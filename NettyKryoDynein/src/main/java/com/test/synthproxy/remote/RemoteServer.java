package com.test.synthproxy.remote;

import com.test.synthproxy.shared.IPCity;
import com.test.synthproxy.shared.tools.KryoPayload;
import com.test.synthproxy.shared.tools.KryoSerializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class RemoteServer {

    private Map<Class,Class> mapPOJORegisteredInterfaces = new HashMap<Class, Class>();
    private Map<Class,String> mapEJBRegisteredInterfaces = new HashMap<Class, String>();

    {
        mapPOJORegisteredInterfaces.put(IPCity.class, CityServer.class);
        mapEJBRegisteredInterfaces.put(IPCity.class, "Local LOOK up - so need correct initial context lookup");
    }


    private Class findPOJOClass(Class interfaceClass){
        Class toProcess = mapPOJORegisteredInterfaces.get(interfaceClass);
        return toProcess;
    }

    public void registerPOJO(Class interfaceClass, Class pojoClass){
        mapPOJORegisteredInterfaces.put(interfaceClass,pojoClass);
    }

    public byte[] conversation(byte[] bytesIn){
        KryoPayload kryoPayload = new KryoSerializer().deserialize(bytesIn);

        if(kryoPayload != null){
            Class interfaceClass = kryoPayload.getInterfaceClass();



            Class toProcess = this.findPOJOClass(interfaceClass);

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
                    Object result = selectedMethod.invoke(toProcess.newInstance(), kryoPayload.getArgs());
                    kryoPayload.setResult(result);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InvocationTargetException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InstantiationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }

        byte[] byteOut = new KryoSerializer().serialize(kryoPayload);
        return byteOut;
    }

}
