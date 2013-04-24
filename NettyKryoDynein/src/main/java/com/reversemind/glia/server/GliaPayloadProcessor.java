package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.GliaPayloadStatus;
import com.test.synthproxy.shared.tools.KryoPayload;
import com.test.synthproxy.shared.tools.KryoSerializer;
import sun.util.LocaleServiceProviderPool;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Date: 4/24/13
 * Time: 2:54 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaPayloadProcessor implements IGliaPayloadProcessor, Serializable {

    private static final Logger LOG = Logger.getLogger(GliaPayloadProcessor.class.getName());

    private static Map<Class,Class> mapPOJORegisteredInterfaces = new HashMap<Class, Class>();

    @Override
    public void registerPOJO(Class interfaceClass, Class pojoClass) {
        synchronized (mapPOJORegisteredInterfaces){
            mapPOJORegisteredInterfaces.put(interfaceClass,pojoClass);
        }
    }

    @Override
    public GliaPayload process(Object gliaPayloadObject) {

        if (gliaPayloadObject == null) {
            LOG.info("ERROR: " + GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
            return this.generateErrorPayload(GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
        }

        if (!(gliaPayloadObject instanceof GliaPayload)) {
            LOG.info("ERROR: " + GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
            return this.generateErrorPayload(GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
        }

        GliaPayload gliaPayload = ((GliaPayload) gliaPayloadObject);


        System.out.println("Get from client:" + gliaPayload);

        Class interfaceClass = gliaPayload.getInterfaceClass();
        Class pojoClass = this.findPOJOClass(interfaceClass);

        Method selectedMethod = null;

        Method[] pojoClassMethods = pojoClass.getMethods();
        for (Method method : pojoClassMethods) {
            if (method.getName().equals(gliaPayload.getMethodName())) {
                selectedMethod = method;
                LOG.info("Find method in pojo:" + selectedMethod);
                break;
            }
        }

        if (selectedMethod == null) {
            LOG.info("ERROR :" + GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
            return this.generateErrorPayload(GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
        }

        try {

            Object result = selectedMethod.invoke(pojoClass.newInstance(), gliaPayload.getArguments());
            gliaPayload.setResultResponse(result);
            gliaPayload.setStatus(GliaPayloadStatus.OK);
            System.out.println("\n\n find result: " + result + " \n\n");
            return gliaPayload;

            // TODO make correct Exception processing
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return this.generateErrorPayload(GliaPayloadStatus.ERROR_UNKNOWN);
    }

    private Class findPOJOClass(Class interfaceClass){
        Class pojoInterfaceClass = this.mapPOJORegisteredInterfaces.get(interfaceClass);
        return pojoInterfaceClass;
    }

    private GliaPayload generateErrorPayload(GliaPayloadStatus status){

        GliaPayload gliaPayload = new GliaPayload();
        gliaPayload.setServerTimestamp(System.currentTimeMillis());

        gliaPayload.setArguments(null);
        gliaPayload.setMethodName(null);
        gliaPayload.setInterfaceClass(null);

        gliaPayload.setResultResponse(null);
        gliaPayload.setStatus(status);

        return gliaPayload;
    }
}
