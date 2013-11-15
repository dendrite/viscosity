package com.reversemind.glia.test.pojo.shared;

import java.io.Serializable;

/**
 * Date: 4/24/13
 * Time: 5:40 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class PAddressNode implements Serializable {

    private String uuid;
    private String screenName;

    public PAddressNode(String uuid, String screenName) {
        this.uuid = uuid;
        this.screenName = screenName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        return "PAddressNode{" +
                "uuid='" + uuid + '\'' +
                ", screenName='" + screenName + '\'' +
                '}';
    }
}
