package com.reversemind.glia.other.servicediscovery.serializer;

import com.netflix.curator.x.discovery.JsonServiceInstance;
import com.netflix.curator.x.discovery.details.InstanceSerializer;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 */
public class InstanceSerializerFactory {

    private final ObjectReader objectReader;
    private final ObjectWriter objectWriter;

    public InstanceSerializerFactory(ObjectReader objectReader, ObjectWriter objectWriter) {
        this.objectReader = objectReader;
        this.objectWriter = objectWriter;
    }

    public <T> InstanceSerializer<T> getInstanceSerializer(TypeReference<JsonServiceInstance<T>> typeReference) {
        return new JacksonInstanceSerializer<T>(objectReader, objectWriter, typeReference);
    }

}