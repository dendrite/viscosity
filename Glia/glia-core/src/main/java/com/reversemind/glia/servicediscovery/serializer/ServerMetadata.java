package com.reversemind.glia.servicediscovery.serializer;

import com.reversemind.glia.server.Metrics;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 8:47 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public final class ServerMetadata implements Serializable {

    @JsonProperty("setName")
    private String name;

    @JsonProperty("instance")
    private String instance;

    @JsonProperty("host")
    private String host;

    @JsonProperty("port")
    private int port;

    @JsonProperty("metrics")
    private Metrics metrics;


    @JsonCreator
    public ServerMetadata(
            @JsonProperty("setName") String name,
            @JsonProperty("instance") String instance,
            @JsonProperty("host") String host,
            @JsonProperty("port") int port,
            @JsonProperty("metrics") Metrics metrics) {
        this.name = name;
        this.instance = instance;
        this.host = host;
        this.port = port;
        this.metrics = metrics;
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

    public Metrics getMetrics() {
        return metrics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerMetadata)) return false;

        ServerMetadata metadata = (ServerMetadata) o;

        if (port != metadata.port) return false;
        if (!host.equals(metadata.host)) return false;
        if (!instance.equals(metadata.instance)) return false;
        if (!name.equals(metadata.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + instance.hashCode();
        result = 31 * result + host.hashCode();
        result = 31 * result + port;
        result = 31 * result + (metrics != null ? metrics.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ServerMetadata{" +
                "setName='" + name + '\'' +
                ", instance='" + instance + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", metrics=" + metrics +
                '}';
    }
}
