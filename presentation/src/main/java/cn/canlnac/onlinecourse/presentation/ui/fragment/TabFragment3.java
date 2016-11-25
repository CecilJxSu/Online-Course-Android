package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

/**
 * 个人中心.
 */

public class TabFragment3 extends Fragment {
    @BindView(R.id.tab3_register)
    TextView register;
    @BindView(R.id.tab3_login)
    TextView login;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        //绑定视图
        ButterKnife.bind(this, view);

        return view;
    }

    //注册按钮点击事件
    @OnClick(R.id.tab3_register)
    public void onClickRegister(View v) {
        Intent intent = new Intent(TabFragment3.this.getActivity(), RegisterActivity.class);
        TabFragment3.this.getActivity().startActivity(intent);
    }

    //登录按钮点击事件
    @OnClick(R.id.tab3_login)
    public void onClickLogin(View v) {
        Intent intent= new Intent(TabFragment3.this.getActivity(), LoginActivity.class);
        TabFragment3.this.getActivity().startActivity(intent);
    }
}
