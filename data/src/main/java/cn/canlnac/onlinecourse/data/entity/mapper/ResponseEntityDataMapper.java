package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.BaseEntity;
import cn.canlnac.onlinecourse.data.entity.ResponseEntity;
import cn.canlnac.onlinecourse.domain.Response;

/**
 * ResponseEntity数据转换.
 */
@Singleton
public class ResponseEntityDataMapper<Body> {
    @Inject
    public ResponseEntityDataMapper() {}

    public Response transform(ResponseEntity<Body> responseEntity) {
        Response response = null;
        if (responseEntity != null) {
            response = new Response(((BaseEntity)responseEntity.getResponseBody()).getId());
            response.setStatus(responseEntity.getResponseStatus());
        }

        return response;
    }
}
