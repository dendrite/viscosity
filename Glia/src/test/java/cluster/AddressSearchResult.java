package cluster;

import java.io.Serializable;

/**
 * Date: 4/22/13
 * Time: 7:28 PM
 *
 * DTO for PrimeFaces autocomplete
 *
 * @author
 * @since 1.0
 */
public class AddressSearchResult implements Serializable {

    private String uuid;
    private String address;

    public AddressSearchResult(String uuid, String address) {
        this.uuid = uuid;
        this.address = address;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "AddressSearchResult{" +
                "uuid='" + uuid + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
