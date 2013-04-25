package com.reversemind.glia.server;

import com.reversemind.glia.GliaPayload;

import java.io.Serializable;
import java.util.Map;

/**
 * Date: 4/24/13
 * Time: 2:39 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public interface IGliaPayloadProcessor extends Serializable {

    public Map<Class,Class> getPojoMap();
    public void setPojoMap(Map<Class, Class> map);
    public void setEjbMap(Map<Class, String> map);
    public void registerPOJO(Class interfaceClass, Class pojoClass);

    /**
     * Should be thread safe
     * @param gliaPayloadObject
     * @return
     */
    public GliaPayload process(Object gliaPayloadObject);
}
