package cn.canlnac.onlinecourse.data.entity.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import cn.canlnac.onlinecourse.data.entity.PositionEntity;
import cn.canlnac.onlinecourse.domain.Position;

/**
 * 消息发生的位置实体类转换.
 */
@Singleton
public class PositionEntityDataMapper {

    @Inject
    public PositionEntityDataMapper() {}

    /**
     * 转换
     * @param positionEntity 消息发生的位置实体类
     * @return
     */
    public Position transform(PositionEntity positionEntity) {
        Position position = null;
        if (positionEntity != null) {
            position = new Position();
            position.setId(positionEntity.getId());
            position.setName(positionEntity.getName());
        }
        return position;
    }
}
