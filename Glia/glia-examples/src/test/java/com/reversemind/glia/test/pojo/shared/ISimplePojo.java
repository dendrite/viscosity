package com.reversemind.glia.test.pojo.shared;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
public interface ISimplePojo extends Serializable {
    public List<PAddressNode> searchAddress(String query);

    public String createException(String query) throws SimpleException;
}
