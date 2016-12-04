package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 课程简介.
 */

public class CourseIntroFragment extends Fragment {
    @BindView(R.id.course_intro_content) TextView content;
    @BindView(R.id.course_intro_header) ImageView header;
    @BindView(R.id.course_intro_gender) ImageView gender;
    @BindView(R.id.course_intro_username) TextView username;
    @BindView(R.id.course_intro_phone) TextView phone;
    @BindView(R.id.course_intro_email) TextView email;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_intro, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        content.setText("\u3000\u3000Java基础班是专门针对零基础学员，学习Java语言基础，" +
                "走进Java编程世界，掌握面向对象思想和编程方法，打好Java编程基础。" +
                "整个课程学时为25天，在牢固的掌握了Java基础后，才能够进一步的学习JavaEE相关内容，" +
                "进入企业级JavaEE开发的艺术世界。\n" +
                "Java技术交流群：xxxxxxxxxxxxx");

        header.setImageResource(R.drawable.header);
        gender.setImageResource(R.drawable.male);
        username.setText("弗拉德丽嘉 - 计算机系");
        phone.setText("手机: 18819437953");
        email.setText("邮箱: ceciljxsu@gmail.com");

        return view;
    }
}
