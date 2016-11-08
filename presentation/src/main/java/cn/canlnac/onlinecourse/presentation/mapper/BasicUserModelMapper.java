package cn.canlnac.onlinecourse.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import cn.canlnac.onlinecourse.domain.BasicUser;
import cn.canlnac.onlinecourse.presentation.internal.di.PerActivity;
import cn.canlnac.onlinecourse.presentation.model.BasicUserModel;

/**
 * BasicUserModel 转成 BasicUserModel.
 */
@PerActivity
public class BasicUserModelMapper {
    @Inject
    public BasicUserModelMapper() {}

    public BasicUserModel transform(BasicUser basicUser) {
        if (null == basicUser) {
            throw new IllegalArgumentException("Cannot transform a null value to a BasicUserModel");
        }

        BasicUserModel basicUserModel = new BasicUserModel(basicUser.getId());

        basicUserModel.setName(basicUser.getName());
        basicUserModel.setIconUrl(basicUser.getIconUrl());

        return basicUserModel;
    }

    public Collection<BasicUserModel> transform(Collection<BasicUser> basicUsers) {
        Collection<BasicUserModel> basicUserModels;

        if (null != basicUsers && !basicUsers.isEmpty()) {
            basicUserModels = new ArrayList<>();

            for (BasicUser basicUser : basicUsers) {
                basicUserModels.add(transform(basicUser));
            }
        } else {
            basicUserModels = Collections.emptyList();
        }

        return basicUserModels;
    }
}
