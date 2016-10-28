/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.cache;

import android.content.Context;
import cn.canlnac.onlinecourse.data.cache.serializer.JsonSerializer;
import cn.canlnac.onlinecourse.data.entity.LoginEntity;
import cn.canlnac.onlinecourse.data.exception.LoginNotFoundException;
import cn.canlnac.onlinecourse.domain.executor.ThreadExecutor;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;

/**
 * {@link LoginCache} 实现.
 */
@Singleton
public class LoginCacheImpl implements LoginCache {

    private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
    private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

    private static final String DEFAULT_FILE_NAME = "login_";
    private static final long EXPIRATION_TIME = 60 * 10 * 1000;

    private final Context context;
    private final File cacheDir;
    private final JsonSerializer serializer;
    private final FileManager fileManager;
    private final ThreadExecutor threadExecutor;

    /**
     * Constructor of the class {@link LoginCacheImpl}.
     *
     * @param context A
     * @param loginCacheSerializer {@link JsonSerializer} for object serialization.
     * @param fileManager {@link FileManager} for saving serialized objects to the file system.
     */
    @Inject
    public LoginCacheImpl(Context context, JsonSerializer loginCacheSerializer,
                         FileManager fileManager, ThreadExecutor executor) {
        if (context == null || loginCacheSerializer == null || fileManager == null || executor == null) {
            throw new IllegalArgumentException("Invalid null parameter");
        }
        this.context = context.getApplicationContext();
        this.cacheDir = this.context.getCacheDir();
        this.serializer = loginCacheSerializer;
        this.fileManager = fileManager;
        this.threadExecutor = executor;
    }

    @Override public Observable<LoginEntity> get(String username, String password) {
        return Observable.create(subscriber -> {
            File loginEntityFile = LoginCacheImpl.this.buildFile();
            String fileContent = LoginCacheImpl.this.fileManager.readFileContent(loginEntityFile);
            LoginEntity loginEntity = LoginCacheImpl.this.serializer.deserialize(fileContent);

            if (loginEntity != null) {
                subscriber.onNext(loginEntity);
                subscriber.onCompleted();
            } else {
                subscriber.onError(new LoginNotFoundException());
            }
        });
    }

    @Override public void put(LoginEntity loginEntity) {
        if (loginEntity != null) {
            File loginEntityFile = this.buildFile();
            if (!isCached()) {
                String jsonString = this.serializer.serialize(loginEntity);
                this.executeAsynchronously(new CacheWriter(this.fileManager, loginEntityFile,
                        jsonString));
                setLastCacheUpdateTimeMillis();
            }
        }
    }

    @Override
    public boolean isCached() {
        File loginEntitiyFile = this.buildFile();
        return this.fileManager.exists(loginEntitiyFile);
    }

    @Override public boolean isExpired() {
        long currentTime = System.currentTimeMillis();
        long lastUpdateTime = this.getLastCacheUpdateTimeMillis();

        boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);

        if (expired) {
            this.evictAll();
        }

        return expired;
    }

    @Override public void evictAll() {
        this.executeAsynchronously(new CacheEvictor(this.fileManager, this.cacheDir));
    }

    /**
     * Build a file, used to be inserted in the disk cache.
     *
     * @return A valid file.
     */
    private File buildFile() {
        StringBuilder fileNameBuilder = new StringBuilder();
        fileNameBuilder.append(this.cacheDir.getPath());
        fileNameBuilder.append(File.separator);
        fileNameBuilder.append(DEFAULT_FILE_NAME);

        return new File(fileNameBuilder.toString());
    }

    /**
     * Set in millis, the last time the cache was accessed.
     */
    private void setLastCacheUpdateTimeMillis() {
        long currentMillis = System.currentTimeMillis();
        this.fileManager.writeToPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE, currentMillis);
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private long getLastCacheUpdateTimeMillis() {
        return this.fileManager.getFromPreferences(this.context, SETTINGS_FILE_NAME,
                SETTINGS_KEY_LAST_CACHE_UPDATE);
    }

    /**
     * Executes a {@link Runnable} in another Thread.
     *
     * @param runnable {@link Runnable} to execute
     */
    private void executeAsynchronously(Runnable runnable) {
        this.threadExecutor.execute(runnable);
    }

    /**
     * {@link Runnable} class for writing to disk.
     */
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

    /**
     * {@link Runnable} class for evicting all the cached files
     */
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
