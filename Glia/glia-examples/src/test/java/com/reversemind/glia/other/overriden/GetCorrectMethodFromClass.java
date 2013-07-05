package com.reversemind.glia.other.overriden;

import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class GetCorrectMethodFromClass {

    private static Map<String,Class> typeMap = new HashMap<String,Class>();
    {
        typeMap.put(int.class.getCanonicalName(),       Integer.class );
        typeMap.put(long.class.getCanonicalName(),      Long.class );
        typeMap.put(double.class.getCanonicalName(),    Double.class );
        typeMap.put(float.class.getCanonicalName(),     Float.class );
        typeMap.put(boolean.class.getCanonicalName(),   Boolean.class );
        typeMap.put(char.class.getCanonicalName(),      Character.class );
        typeMap.put(byte.class.getCanonicalName(),      Byte.class );
        typeMap.put(short.class.getCanonicalName(),     Short.class );
    }

    @Test
    public void testGetCorrectMethod(){

        SimpleOverridenMethods simple = new SimpleOverridenMethods();

        String methodName = "getSimple";
        Object[] arguments = new Object[2];
        arguments[0] = 1;
        arguments[1] = "1";



        Method foundedMethod = null;

        Method[] methods = simple.getClass().getMethods();

        String compareTypeName = "";

        for(Method method: methods){

            if(method.getName().equals(methodName)){
                System.out.println(method.getName() + " args:" + method.getParameterTypes().length);

                if(arguments.length == method.getParameterTypes().length){

                    if(arguments.length == 0){
                        foundedMethod = method;
                        break;
                    }

                    if(method.getParameterTypes().length > 0){
                        int count = arguments.length;
                        Class[] cl = method.getParameterTypes();
                        for(int i=0; i<arguments.length; i++){

                            compareTypeName = cl[i].getCanonicalName();
                            if(typeMap.containsKey(cl[i].getCanonicalName())){
                                compareTypeName = typeMap.get(cl[i].getCanonicalName()).getCanonicalName();
                            }
                            if(compareTypeName.equals(arguments[i].getClass().getCanonicalName())){
                                count--;
                            }
                        }
                        if(count==0){
                            foundedMethod = method;
                        }
                    }
                }
            }

        }

        if(foundedMethod != null) System.out.println("Founded method:" + foundedMethod + " arguments:" + foundedMethod.getParameterTypes());
    }
}
