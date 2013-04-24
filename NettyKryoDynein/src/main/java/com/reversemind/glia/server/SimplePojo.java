package com.reversemind.glia.server;

import com.reversemind.glia.shared.ISimplePojo;
import com.reversemind.glia.shared.PAddressNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 4/24/13
 * Time: 5:42 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class SimplePojo implements ISimplePojo, Serializable {

    @Override
    public List<PAddressNode> searchAddress(String query) {
        List<PAddressNode> list = new ArrayList<PAddressNode>();
        for(int i=0;i<10;i++){
            list.add(new PAddressNode("" + i, " city - query - " + i));
        }
        return list;
    }
}
