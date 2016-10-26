package cn.canlnac.onlinecourse.data.entity.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.data.entity.LoginEntity;

/**
 * 将JSON字符串转成Object
 */
public class LoginEntityJsonMapper {
    private final Gson gson;

    @Inject
    public LoginEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * 转成LoginEntity
     * @param loginJsonResponse     登录返回数据，JSON字符串
     * @return                      登录实体类
     * @throws JsonSyntaxException  语法错误
     */
    public LoginEntity transformLoginEntity(String loginJsonResponse) throws JsonSyntaxException {
        try {
            Type loginEntityType = new TypeToken<LoginEntity>() {}.getType();
            LoginEntity loginEntity = this.gson.fromJson(loginJsonResponse, loginEntityType);

            return loginEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}
