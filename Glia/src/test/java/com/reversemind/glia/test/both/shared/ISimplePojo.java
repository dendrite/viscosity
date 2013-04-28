package com.reversemind.glia.test.both.shared;

import java.io.Serializable;
import java.util.List;

/**
 * Date: 4/24/13
 * Time: 5:39 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public interface ISimplePojo extends Serializable {
    public List<PAddressNode> searchAddress(String query);
}
