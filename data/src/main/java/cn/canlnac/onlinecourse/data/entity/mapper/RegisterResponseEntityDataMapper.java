package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.RegisterEntity;
import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import cn.canlnac.onlinecourse.domain.Response;

/**
 * RegisterResponseEntity数据转换.
 */
@Singleton
public class RegisterResponseEntityDataMapper {
    @Inject
    public RegisterResponseEntityDataMapper() {}

    public Response transform(ResponseEntity<RegisterEntity> responseEntity) {
        Response response = null;
        if (responseEntity != null) {
            response = new Response();
            response.setBody(new RegisterEntityDataMapper().transform(responseEntity.getResponseBody()));
            response.setStatus(responseEntity.getResponseStatus());
            response.setJwt(responseEntity.getJwtHeader());
        }

        return response;
    }
}
