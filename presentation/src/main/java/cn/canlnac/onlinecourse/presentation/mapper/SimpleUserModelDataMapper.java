package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.SimpleUser;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.SimpleUserModel;

@PerActivity
public class SimpleUserModelDataMapper {
    @Inject
    public SimpleUserModelDataMapper() {}

    public SimpleUserModel transform(SimpleUser simpleUser) {
        if (simpleUser == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        SimpleUserModel simpleUserModel = new SimpleUserModel();
        simpleUserModel.setId(simpleUser.getId());
        simpleUserModel.setIconUrl(simpleUser.getIconUrl());
        simpleUserModel.setName(simpleUser.getName());

        return simpleUserModel;
    }

    public List<SimpleUserModel> transform(List<SimpleUser> simpleUserList) {
        List<SimpleUserModel> simpleUserModelList = new ArrayList<>(simpleUserList.size());
        SimpleUserModel simpleUserModel;
        for (SimpleUser simpleUser : simpleUserList) {
            simpleUserModel = transform(simpleUser);
            if (simpleUser != null) {
                simpleUserModelList.add(simpleUserModel);
            }
        }

        return simpleUserModelList;
    }
}
