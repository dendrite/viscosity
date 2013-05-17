package com.reversemind.glia.integration.ejb.client;

import com.reversemind.glia.client.GliaClient;
import com.reversemind.glia.client.IGliaClient;

import javax.ejb.Local;
import java.io.Serializable;

/**
 * Date: 5/17/13
 * Time: 8:35 AM
 *
 * @author konilovsky
 * @since 1.0
 */
@Local
public interface IGliaClientProxy extends Serializable {
    public IGliaClient getClient();
    public Object getProxy(Class interfaceClass) throws Exception;
}
