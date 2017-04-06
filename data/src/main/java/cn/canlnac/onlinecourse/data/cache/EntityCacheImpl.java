package cn.canlnac.onlinecourse.data.cache;

import android.content.Context;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.cache.serializer.JsonSerializer;
import cn.canlnac.onlinecourse.data.exception.NotFoundException;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import rx.Observable;

/**
 * 实体类缓存实现.
 */
@Singleton
public class EntityCacheImpl<E> implements EntityCache<E> {
    private static final String SETTINGS_FILE_NAME = "cn.canlnac.onlinecourse.settings";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final String DEFAULT_FILE_NAME;
    private final Context context;
    private final File cacheDir;
    private final JsonSerializer<E> serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * 构造函数 {@link EntityCacheImpl}.
     *
     * @param context           android上下文
     * @param cacheSerializer   缓存序列化.
     * @param fileManager       文件管理.
     */
    @Inject
    public EntityCacheImpl(
            Context context,
            JsonSerializer<E> cacheSerializer,
            String cacheFileName,
            FileManager fileManager,
            ThreadExecutor executor
    ) {
        if (context == null || cacheSerializer == null || cacheFileName == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }

        this.DEFAULT_FILE_NAME = cacheFileName + "_";

        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = cacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    /**
     * 获取一个实体类
     * @param id    实体类ID
     * @return
     */
    @Override
    public Observable<E> get(final int id) {
        return Observable.create(subscriber -> {
            File file = EntityCacheImpl.this.buildFile(id);
            String fileContent = EntityCacheImpl.this.fileManager.readFileContent(file);
            E e = EntityCacheImpl.this.serializer.deserialize(fileContent);

            if (e != null) {
                subscriber.onNext(e);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new NotFoundException());
            }
        });
    }

    /**
     * 添加一个实体类的缓存
     * @param id    实体类对象ID
     * @param e     实体类.
     */
    @Override
    public void put(int id, E e) {
        if (e != null) {
            File file = this.buildFile(id);
            if (!isCached(id)) {
                String jsonString = this.serializer.serialize(e);
                this.executeAsynchronously(new CacheWriter(this.fileManager, file,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    /**
     * 是否缓存
     * @return
     */
    @Override
    public boolean isCached(int id) {
        File file = this.buildFile(id);
        return this.fileManager.exists(file);
    }

    /**
     * 创建文件
     *
     * @param id 实体类对象ID
     * @return
     */
    private File buildFile(int id) {
        StringBuilder builder = new StringBuilder();
        builder.append(this.cacheDir.getPath());
        builder.append(File.separator);
        builder.append(DEFAULT_FILE_NAME);
        builder.append(id);

        return new File(builder.toString());
    }

    /**
     * 数据是否过期了
     * @return
     */
    @Override
    public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override
    public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * 设置最近一次缓存更新的时间.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * 在另外一个线程中执行一个任务.
     *
     * @param runnable 执行任务
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * 获取最后一次缓存更新的时间
     * @return
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /** 写缓存的Runnable类，线程中执行 */
    private static class CacheWriter implements Runnable {
        private final FileManager fileManager;
        private final File fileToWrite;
        private final String fileContent;

        CacheWriter(FileManager fileManager, File fileToWrite, String fileContent) {
            this.fileManager = fileManager;
            this.fileToWrite = fileToWrite;
            this.fileContent = fileContent;
        }

        @Override public void run() {
            this.fileManager.writeToFile(fileToWrite, fileContent);
        }
    }

    /** 清空缓存，Runnable任务，线程中运行 */
    private static class CacheEvictor implements Runnable {
        private final FileManager fileManager;
        private final File cacheDir;

        CacheEvictor(FileManager fileManager, File cacheDir) {
            this.fileManager = fileManager;
            this.cacheDir = cacheDir;
        }

        @Override public void run() {
            this.fileManager.clearDirectory(this.cacheDir);
        }
    }
}
