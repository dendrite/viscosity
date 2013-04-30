package com.reversemind.glia.other.servicediscovery.serializer;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.UUID;

/**
 * Date: 4/30/13
 * Time: 8:47 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public final class ServerMetadata implements Serializable {

    @JsonProperty("name")
    private final String name;

    @JsonProperty("instance")
    private final String instance;

    @JsonProperty("host")
    private final String host;

    @JsonProperty("port")
    private final int port;

    @JsonCreator
    public ServerMetadata(
            @JsonProperty("name") String name,
            @JsonProperty("instance") String instance,
            @JsonProperty("host") String host,
            @JsonProperty("port") int port) {
        this.name = name;
        this.instance = instance;
        this.host = host;
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public String getInstance() {
        return instance;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerMetadata)) return false;

        ServerMetadata that = (ServerMetadata) o;

        if (port != that.port) return false;
        if (!host.equals(that.host)) return false;
        if (!instance.equals(that.instance)) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + instance.hashCode();
        result = 31 * result + host.hashCode();
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "ServerMetadata{" +
                "name='" + name + '\'' +
                ", instance='" + instance + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
