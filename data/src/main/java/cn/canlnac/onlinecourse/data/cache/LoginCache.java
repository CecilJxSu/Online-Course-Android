package cn.canlnac.onlinecourse.data.cache;

import rx.Observable;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;

/**
 * 登录数据缓存接口
 */

public interface LoginCache {
    /**
     * 获取一个 {@link rx.Observable} 对象，该对象会发送 {@link LoginEntity}.
     * @return
     */
    Observable<LoginEntity> get(String username, String password);

    /**
     * 添加元素到缓存.
     *
     * @param loginEntity 登录实体类.
     */
    void put(LoginEntity loginEntity);

    /**
     * 检查是否已经缓存了.
     *
     * @return true代表缓存，false未缓存.
     */
    boolean isCached();

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
