package com.simple.test;

import javax.ejb.Local;
import java.io.Serializable;

/**
 *
 *
 */
@Local
public interface ISimpleEJB extends Serializable {
    public String getResult(String value);
}
