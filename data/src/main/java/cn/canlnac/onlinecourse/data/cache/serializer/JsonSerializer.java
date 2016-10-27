/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.canlnac.onlinecourse.data.cache.serializer;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;

/**
 * 登录实体类序列化
 */
@Singleton
public class JsonSerializer {

    private final Gson gson = new Gson();

    @Inject
    public JsonSerializer() {
    }

    /**
     * 序列化成json
     *
     * @param loginEntity {@link LoginEntity} 序列化实体类.
     */
    public String serialize(LoginEntity loginEntity) {
        String jsonString = gson.toJson(loginEntity, LoginEntity.class);
        return jsonString;
    }

    /**
     * 序列化成实体对象
     *
     * @param jsonString json字符串.
     * @return {@link LoginEntity}  实体类
     */
    public LoginEntity deserialize(String jsonString) {
        LoginEntity loginEntity = gson.fromJson(jsonString, LoginEntity.class);
        return loginEntity;
    }
}
