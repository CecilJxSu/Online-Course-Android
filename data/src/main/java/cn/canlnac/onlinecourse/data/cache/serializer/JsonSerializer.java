package cn.canlnac.onlinecourse.data.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 实体类序列化
 */
@Singleton
public class JsonSerializer<E> {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {
    }

    /**
     * 序列化成json
     *
     * @param e {@link E} 序列化实体类.
     */
    public String serialize(E e) {
        return gson.toJson(e, getClass().getGenericSuperclass());
    }

    /**
     * 序列化成实体对象
     *
     * @param jsonString json字符串.
     * @return {@link E}  实体类
     */
    public E deserialize(String jsonString) {
        return gson.fromJson(jsonString, getClass().getGenericSuperclass());
    }
}
