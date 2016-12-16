package cn.canlnac.onlinecourse.presentation.mapper;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.Position;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.PositionModel;

@PerActivity
public class PositionModelDataMapper {
    @Inject
    public PositionModelDataMapper() {}

    public PositionModel transform(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        PositionModel positionModel = new PositionModel();
        positionModel.setId(position.getId());
        positionModel.setName(position.getName());

        return positionModel;
    }
}
