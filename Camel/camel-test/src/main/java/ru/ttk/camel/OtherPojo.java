package ru.ttk.camel;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class OtherPojo {

    private String name;
    private Type type;

    public OtherPojo() {

    }

    public OtherPojo(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OtherPojo{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

    public enum Type {
        VALUE1(1),
        VALUE2(2);

        private int code;

        private static Map<Integer, Type> namesMap = new HashMap<Integer, Type>(2);

        static {
            namesMap.put(1, VALUE1);
            namesMap.put(2, VALUE2);
        }

        private Type(int code) {
            this.code = code;
        }

        @JsonCreator
        public static Type forCode(Integer value) {
            return namesMap.get(value);
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return "Type{" +
                    "code=" + code +
                    '}';
        }
    }
}
