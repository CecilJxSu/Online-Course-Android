package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.HasComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.CourseIntroComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerCourseIntroComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CourseIntroModule;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.model.ProfileModel;
import cn.canlnac.onlinecourse.presentation.presenter.CourseIntroPresenter;

/**
 * 课程简介.
 */

public class CourseIntroFragment extends BaseFragment implements HasComponent<CourseIntroComponent> {
    @BindView(R.id.course_intro_content) TextView content;
    @BindView(R.id.course_intro_header) SimpleDraweeView header;
    @BindView(R.id.course_intro_gender) ImageView gender;
    @BindView(R.id.course_intro_username) TextView username;
    @BindView(R.id.course_intro_phone) TextView phone;
    @BindView(R.id.course_intro_email) TextView email;

    private CourseIntroComponent courseIntroComponent;

    @Inject
    CourseIntroPresenter courseIntroPresenter;

    @Override
    public CourseIntroComponent getComponent() {
        return courseIntroComponent;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //获取布局
        View view = inflater.inflate(R.layout.course_intro, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        return view;
    }

    /**
     * 显示课程介绍信息信息
     * @param courseModel
     */
    public void showCourseInfo(CourseModel courseModel) {
        if (null != courseModel.getIntroduction()) {
            content.setText("\u3000\u3000" + courseModel.getIntroduction().replace("\n","\n\u3000\u3000"));
        }

        if (null != courseModel.getAuthor() || courseModel.getAuthor().getId() > 0) {
            this.courseIntroComponent = DaggerCourseIntroComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .courseIntroModule(new CourseIntroModule(courseModel.getAuthor().getId()))
                    .build();
            this.getComponent(CourseIntroComponent.class).inject(this);
            this.courseIntroPresenter.setView(this);
            this.courseIntroPresenter.initializeProfile();
        }
    }

    /**
     * 设置作者信息
     * @param profileModel
     */
    public void showAuthor(ProfileModel profileModel) {
        Uri iconUrl = Uri.parse(profileModel.getIconUrl());
        if (iconUrl != null) {
            header.setImageURI(iconUrl);
        }

        if (null != profileModel.getGender() && profileModel.getGender().equals("female")) {
            gender.setImageResource(R.drawable.female);
        } else {
            gender.setImageResource(R.drawable.male);
        }

        username.setText(profileModel.getRealname() + " - " + profileModel.getDepartment());
        phone.setText("手机: " + profileModel.getPhone());
        email.setText("邮箱: " + profileModel.getEmail());
    }
}
