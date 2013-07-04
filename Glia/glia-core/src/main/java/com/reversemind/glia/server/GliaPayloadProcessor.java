package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;
import com.reversemind.glia.GliaPayloadBuilder;
import com.reversemind.glia.GliaPayloadStatus;
import org.apache.commons.lang3.StringUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
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
    private static Map<Class,String> mapEJBRegisteredInterfaces = new HashMap<Class, String>();

    private static Properties jndiEnvironment;
    private static Hashtable jndiPropertiesMap;
    private static Context jndiContext = null;

    @Override
    public Map<Class, Class> getPojoMap() {
        return mapPOJORegisteredInterfaces;
    }

    @Override
    public void setPojoMap(Map<Class, Class> map) {
        if (map != null && map.size() > 0) {
            Set<Class> set = map.keySet();
            for (Class interfaceClass : set) {
                synchronized (mapPOJORegisteredInterfaces) {
                    mapPOJORegisteredInterfaces.put(interfaceClass, map.get(interfaceClass));
                }
            }
        }
    }

    @Override
    public void setEjbMap(Map<Class, String> map) {
        if (map != null && map.size() > 0) {
            Set<Class> set = map.keySet();
            for (Class interfaceClass : set) {
                synchronized (mapEJBRegisteredInterfaces) {
                    mapEJBRegisteredInterfaces.put(interfaceClass, map.get(interfaceClass));
                }
            }
        }
    }

    @Override
    public void registerPOJO(Class interfaceClass, Class pojoClass) {
        synchronized (mapPOJORegisteredInterfaces){
            mapPOJORegisteredInterfaces.put(interfaceClass,pojoClass);
        }
    }

    private void initJndiContext(){
        jndiPropertiesMap = this.getJndiProperties(jndiEnvironment);
        try {
            jndiContext = new InitialContext(jndiPropertiesMap);
            // buildingDAO = InitialContext.doLookup("java:global/ttk-house/ttk-house-ejb-2.0-SNAPSHOT/BuildingDAO!ru.ttk.baloo.house.data.service.building.IBuildingDAO");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the JNDI environment to use for JNDI lookups.
     *
     * @param jndiEnvironment
     */
    public void setJndiEnvironment(Properties jndiEnvironment) {
        this.jndiEnvironment = jndiEnvironment;
    }

    /**
     * Return the JNDI environment to use for JNDI lookups.
     */
    public Properties getJndiEnvironment() {
        return this.jndiEnvironment;
    }

    @Override
    public GliaPayload process(Object gliaPayloadObject) {

        if (gliaPayloadObject == null) {
            LOG.info("ERROR: " + GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
            return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
        }

        if (!(gliaPayloadObject instanceof GliaPayload)) {
            LOG.info("ERROR: " + GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
            return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_CLIENT_PAYLOAD);
        }

        GliaPayload gliaPayload = ((GliaPayload) gliaPayloadObject);


        System.out.println("Get from client:" + gliaPayload);

        Class interfaceClass = gliaPayload.getInterfaceClass();
        Class pojoClass = this.findPOJOClass(interfaceClass);

        // POJO

        if(pojoClass != null){
            return this.invokeMethod(gliaPayload, pojoClass, gliaPayload.getMethodName(), gliaPayload.getArguments());
        }

        // EJB
        String jndiName = this.findEjbClass(interfaceClass);

        if(!StringUtils.isEmpty(jndiName)){
            try {
                // buildingDAO = InitialContext.doLookup("java:global/ttk-house/ttk-house-ejb-2.0-SNAPSHOT/BuildingDAO!ru.ttk.baloo.house.data.service.building.IBuildingDAO");
                Object remoteObject = InitialContext.doLookup(jndiName);
                //Object remoteObject = jndiContext.lookup(jndiName);

                return this.invokeEjbMethod(gliaPayload, remoteObject, gliaPayload.getMethodName(), gliaPayload.getArguments());
            } catch (NamingException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }

        return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_UNKNOWN);
    }

    private GliaPayload invokeMethod(GliaPayload gliaPayload, Class pojoOrEjbClass, String methodName, Object[] arguments){

        Method selectedMethod = this.findMethod(pojoOrEjbClass, methodName);

        if (selectedMethod == null) {
            LOG.info("ERROR :" + GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
            return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
        }

        try {

            Object result = selectedMethod.invoke(pojoOrEjbClass.newInstance(), arguments);
            gliaPayload.setResultResponse(result);
            gliaPayload.setStatus(GliaPayloadStatus.OK);
            gliaPayload.setServerTimestamp(System.currentTimeMillis());

            //System.out.println("\n\n find result: " + result + " \n\n");
            return gliaPayload;

            // TODO make correct Exception processing
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_UNKNOWN);
    }

    private GliaPayload invokeEjbMethod(GliaPayload gliaPayload, Object instance, String methodName, Object[] arguments){

        Method selectedMethod = this.findMethod(instance.getClass(), methodName);

        if (selectedMethod == null) {
            LOG.info("ERROR :" + GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
            return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_PAYLOAD_UNKNOWN_METHOD);
        }

        try {

            Object result = selectedMethod.invoke(instance, arguments);
            gliaPayload.setResultResponse(result);
            gliaPayload.setStatus(GliaPayloadStatus.OK);
            gliaPayload.setServerTimestamp(System.currentTimeMillis());

            //System.out.println("\n\n find result: " + result + " \n\n");
            return gliaPayload;

            // TODO make correct Exception processing
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return GliaPayloadBuilder.buildErrorPayload(GliaPayloadStatus.ERROR_UNKNOWN);
    }

    private Method findMethod(Class interfaceClass, String methodName){
        Method selectedMethod = null;

        Method[] pojoClassMethods = interfaceClass.getMethods();
        for (Method method : pojoClassMethods) {
            if (method.getName().equals(methodName)) {
                selectedMethod = method;
                LOG.info("Find method:" + selectedMethod);
                break;
            }
        }

        return selectedMethod;
    }

    private Hashtable getJndiProperties(Properties jndi){
        Set set = jndi.keySet();
        Hashtable localTable = new Hashtable();
        for(Object key: set){
            localTable.put(key,jndi.get(key));
        }
        return localTable;
    }

    private Class findPOJOClass(Class interfaceClass){
        Class pojoInterfaceClass = this.mapPOJORegisteredInterfaces.get(interfaceClass);
        return pojoInterfaceClass;
    }

    private String findEjbClass(Class interfaceClass){
        return this.mapEJBRegisteredInterfaces.get(interfaceClass);
    }


}
