package com.reversemind.glia.servicediscovery.serializer;

import com.netflix.curator.x.discovery.JsonServiceInstance;
import com.netflix.curator.x.discovery.ServiceInstance;
import com.netflix.curator.x.discovery.details.InstanceSerializer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.type.TypeReference;

import java.io.ByteArrayOutputStream;

/**
 *
 */
public final class JacksonInstanceSerializer<T> implements InstanceSerializer<T> {

    private static Logger LOG = Logger.getLogger(JacksonInstanceSerializer.class);

    private final TypeReference<JsonServiceInstance<T>> typeReference;
    private final ObjectWriter objectWriter;
    private final ObjectReader objectReader;

    public JacksonInstanceSerializer(ObjectReader objectReader, ObjectWriter objectWriter, TypeReference<JsonServiceInstance<T>> typeReference) {
        this.objectReader = objectReader;
        this.objectWriter = objectWriter;
        this.typeReference = typeReference;
    }

    public ServiceInstance<T> deserialize(byte[] bytes) throws Exception {
        return objectReader.withType(typeReference).readValue(bytes);
    }

    public byte[] serialize(ServiceInstance<T> serviceInstance) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        objectWriter.writeValue(out, serviceInstance);
        return out.toByteArray();
    }
}
