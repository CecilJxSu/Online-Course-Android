package cn.canlnac.onlinecourse.data.cache;

import rx.Observable;

/**
 * 实体类数据缓存接口
 */

public interface EntityCache<E> {
    /**
     * 获取一个 {@link rx.Observable} 对象，该对象会发送 {@link E}.
     * @return
     */
    Observable<E> get(int id);

    /**
     * 添加元素到缓存.
     *
     * @param id    实体类对象ID
     * @param e     实体类.
     */
    void put(int id, E e);

    /**
     * 检查是否已经缓存了.
     *
     * @param id    实体类对象ID
     * @return      true代表缓存，false未缓存.
     */
    boolean isCached(int id);

    /**
     * 检查是否已经过期了.
     *
     * @return true代表过期，false未缓存.
     */
    boolean isExpired();

    /**
     * 清空所有缓存.
     */
    void evictAll();
}
