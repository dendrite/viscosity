package com.reversemind.glia.simple;

import com.reversemind.glia.integration.ejb.client.ClientEJBDiscovery;

import javax.ejb.Singleton;
import java.io.Serializable;

/**
 *
 */
@Singleton
public class GliaClient extends ClientEJBDiscovery implements Serializable {

}
