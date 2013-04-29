package com.reversemind.glia.test.pojo.server;

import com.reversemind.glia.test.pojo.shared.ISimplePojo;
import com.reversemind.glia.test.pojo.shared.PAddressNode;

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

        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<PAddressNode> list = new ArrayList<PAddressNode>();
        for(int i=0;i<10;i++){
            list.add(new PAddressNode("" + i, " city - " + query + "_" + i));
        }
        return list;
    }
}
