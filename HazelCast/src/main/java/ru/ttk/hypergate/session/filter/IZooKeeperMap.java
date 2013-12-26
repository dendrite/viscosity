package ru.ttk.hypergate.session.filter;

import java.util.Map;
import java.util.Set;

/**
 *
 */
public interface IZooKeeperMap extends Map<String, Object>{

    public void delete(String key);
    public Set<Entry<String, Object>> entrySet(String keyLikeString);

}
