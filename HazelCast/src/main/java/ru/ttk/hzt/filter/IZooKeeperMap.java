package ru.ttk.hzt.filter;

import java.util.Map;

/**
 *
 */
public interface IZooKeeperMap<String, Object> extends Map<String, Object>{

    public void delete(String key);

}
