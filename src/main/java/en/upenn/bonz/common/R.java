package en.upenn.bonz.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * unifying the data format so that front end can easily use it and display data
 * @param <T>
 */
public class R<T> implements Serializable {

    private Integer code; // 1: success, 0: error
    private String msg; // error msg
    private T data; // encapsulate data that front end needed
    private Map map = new HashMap(); // dynamic data

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.code = 1;
        r.data = object;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.code = 0;
        r.msg = msg;
        return r;
    }

    public R<T> add(String key, String value) {
        this.map.put(key, value);
        return this;
    }
}
