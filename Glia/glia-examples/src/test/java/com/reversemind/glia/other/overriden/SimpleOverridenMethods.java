package com.reversemind.glia.other.overriden;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class SimpleOverridenMethods {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleOverridenMethods.class);

    public String getSimple(String string) {
        LOG.debug("Argument #1:" + string);
        return "argument #1:" + string;
    }

    public String getSimple(String string, String otherString) {
        LOG.debug("Argument #1:" + string);
        LOG.debug("Argument #2:" + otherString);
        return "argument #1:" + string + " argument #2:" + otherString;
    }

    public String getSimple(int string, String otherString) {
        LOG.debug("Argument #1:" + string);
        LOG.debug("Argument #2:" + otherString);
        return "argument #1:" + string + " argument #2:" + otherString;
    }

}
