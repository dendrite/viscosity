package ru.ttk.hypergate.session.filter;

import com.esotericsoftware.kryo.Kryo;
import com.netflix.curator.framework.CuratorFramework;
import org.apache.commons.lang3.SerializationUtils;
import ru.ttk.hypergate.serializer.KryoDeserializer;
import ru.ttk.hypergate.serializer.KryoSerializer;
import ru.ttk.hypergate.serializer.KryoSettings;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 *
 */
public class ZooKeeperHashMap extends HashMap<String, Object> implements Serializable, IZooKeeperMap {

//    private static final String PARENT_PATH = "/zookeeper.map";
    private static final String PATH_SEPARATOR = "/";

    private CuratorFramework curatorFramework;
    private String parentMapPath;

    private final Kryo kryo = new KryoSettings().getKryo();
    private KryoSerializer kryoSerializer;
    private KryoDeserializer kryoDeserializer;


    public ZooKeeperHashMap(int initialCapacity, float loadFactor, CuratorFramework curatorFramework, String parentMapPath) {
        super(initialCapacity, loadFactor);
        this.curatorFramework = curatorFramework;
        this.parentMapPath = parentMapPath;
        this.kryoSerializer = new KryoSerializer(this.kryo);
        this.kryoDeserializer = new KryoDeserializer(this.kryo);
    }

    public ZooKeeperHashMap(int initialCapacity, CuratorFramework curatorFramework, String parentMapPath) {
        super(initialCapacity);
        this.curatorFramework = curatorFramework;
        this.parentMapPath = parentMapPath;
        this.kryoSerializer = new KryoSerializer(this.kryo);
        this.kryoDeserializer = new KryoDeserializer(this.kryo);
    }

    public ZooKeeperHashMap(CuratorFramework curatorFramework, String parentMapPath) {
        this.curatorFramework = curatorFramework;
        this.parentMapPath = parentMapPath;
        this.kryoSerializer = new KryoSerializer(this.kryo);
        this.kryoDeserializer = new KryoDeserializer(this.kryo);
    }

    public ZooKeeperHashMap(Map<? extends String, ? extends Object> m, CuratorFramework curatorFramework, String parentMapPath) {
        super(m);
        this.curatorFramework = curatorFramework;
        this.parentMapPath = parentMapPath;
        this.kryoSerializer = new KryoSerializer(this.kryo);
        this.kryoDeserializer = new KryoDeserializer(this.kryo);
    }

    private String path(String parentPath, String key) {
        return parentPath + PATH_SEPARATOR + key;
    }

    private String path(String key) {
        return parentMapPath + PATH_SEPARATOR + key;
    }
    private String path() {
        return parentMapPath;
    }

    private void deleteFromZookeeper(String path) throws Exception {
        curatorFramework.delete().forPath(path);
    }

    private void save(java.lang.String parentPath, String name, Object object) throws Exception {

        //byte[] data = SerializationUtils.serialize((Serializable) object);
        byte[] data = this.kryoSerializer.serialize(object);

        if(data != null)
        System.out.println("DATA SIZE for name:" + name + " " + data.length);

        if (curatorFramework.checkExists().forPath(parentPath) == null) {
            curatorFramework.create().forPath(parentPath);
        }

        if (curatorFramework.checkExists().forPath(this.path(parentPath, name)) == null) {
            curatorFramework.create().forPath(this.path(parentPath, name), data);
        } else {
            curatorFramework.setData().forPath(this.path(parentPath, name), data);
        }

    }

    private java.lang.Object read(String parentPath, String name) throws Exception {
        if (curatorFramework.checkExists().forPath(this.path(parentPath, name)) == null) {
            throw new IOException("Path:" + this.path(parentPath, name) + " is not exist");
        }
        byte[] data = curatorFramework.getData().forPath(this.path(parentPath, name));
        // return SerializationUtils.deserialize(data);
        return this.kryoDeserializer.deserialize(data);
    }

    private Object read(String name) throws Exception {
        if (curatorFramework.checkExists().forPath(this.path(name)) == null) {
            throw new IOException("Path:" + this.path(name) + " is not exist");
        }
        byte[] data = curatorFramework.getData().forPath(this.path(name));
        //return SerializationUtils.deserialize(data);
        return this.kryoDeserializer.deserialize(data);
    }

    private boolean isPathExist(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path) != null;
    }

    public Object put(String key, Object value) {
        Object returnObject = super.put(key, value);

        try {
            this.save(parentMapPath, key, value);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not save value");
        }

        return returnObject;
    }

    public Object get(String key) {
        Object returnObject = super.get(key);

        // if not in local cache
        if (returnObject == null) {
            try {
                returnObject = this.read(parentMapPath, key);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("COULD NOT FIND OBJECT FOR PATH:" + this.path(key));
            }
        }

        return returnObject;
    }

    @Override
    public Object get(Object key) {
        Object returnObject = super.get(key);

        // if not in local cache
        if (returnObject == null) {
            try {
                returnObject = this.read(parentMapPath, key.toString());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("COULD NOT FIND OBJECT FOR PATH:" + this.path(key.toString()));
            }
        }

        return returnObject;
    }

    public boolean containsKey(String key) {
        boolean result = super.containsKey(key);

        if (result == false) {
            try {
                result = this.isPathExist(this.path(key));
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("COULD NOT CHECK IS PATH EXIST path:" + this.path(key));
            }
        }
        return result;
    }

    @Override
    public void delete(String key) {
        Object returnObject = super.get(key);
        if(returnObject != null){
            super.remove(key);
        }

        try {
            System.out.println("\n\n\n\n\n  GOING TO DELETE  a key: "+ key +"\n\n\n\n");
            if(this.isPathExist(this.path(key))){
                this.deleteFromZookeeper(this.path(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("");
        }
    }

    private Map<String, Object> childrenWhereKeyLike(String keyLike) throws Exception {
        List<String> _list = curatorFramework.getChildren().forPath(this.path());
        if (_list != null && _list.size() > 0) {

            Map<String, Object> map = new HashMap<String, Object>();

            for(String key: _list){
                if(key.startsWith(keyLike)){
                    Object object = this.read(key);
                    map.put(key,object);
                }
            }

            return map;
        }
        return new HashMap<String, Object>();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet(String keyLikeString) {
        try {
            Map<String, Object> map = this.childrenWhereKeyLike(keyLikeString);
            if (map != null) {
                return map.entrySet();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("COULD NOT FIND ANY ENTRY SET for key starts with:" + keyLikeString);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        return map.entrySet();
    }

}
