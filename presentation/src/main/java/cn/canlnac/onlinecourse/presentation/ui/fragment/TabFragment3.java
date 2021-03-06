package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetLoginDataComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetLoginDataModule;
import cn.canlnac.onlinecourse.presentation.model.LoginModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetLoginDataPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyChatActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyFavoriteActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.MyRepliesActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.ProfileActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.RegisterActivity;

/**
 * 个人中心.
 */

public class TabFragment3 extends BaseFragment {
    @BindView(R.id.tab3_register)
    TextView register;
    @BindView(R.id.tab3_login)
    TextView login;
    @BindView(R.id.header)
    SimpleDraweeView header;

    LoginModel loginModel;

    @Inject
    GetLoginDataPresenter getLoginDataPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        //绑定视图
        ButterKnife.bind(this, view);

        initialize();

        return view;
    }

    public void initialize(){
        DaggerGetLoginDataComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getLoginDataModule(new GetLoginDataModule())
                .build().inject(this);

        getLoginDataPresenter.setView(this);
        getLoginDataPresenter.initialize();
    }

    //注册按钮点击事件
    @OnClick(R.id.tab3_register)
    public void onClickRegister(View v) {
        Intent intent = new Intent(getContext(), RegisterActivity.class);
        startActivity(intent);
    }

    //登录按钮点击事件
    @OnClick(R.id.tab3_login)
    public void onClickLogin(View v) {
        Intent intent= new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 200);
    }

    @OnClick(R.id.user_profile)
    public void onClickProfile(View v) {
        if (loginModel == null) {
            showToastMessage("请先登陆");
            onClickLogin(null);
            return;
        }

        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra("userId", loginModel.getId());
        startActivityForResult(intent,ProfileActivity.UPDATE_AVATAR);
    }

    public void showHeader(LoginModel loginModel) {
        this.loginModel = loginModel;
        if (loginModel != null && loginModel.getIconUrl() != null) {
            header.setImageURI(loginModel.getIconUrl());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ProfileActivity.UPDATE_AVATAR) {
            if (data.getStringExtra("avatar") != null) {
                header.setImageURI(data.getStringExtra("avatar"));
            }
        }
    }

    @OnClick(R.id.my_chats)
    public void onClickMyChats(View v) {
        if (loginModel == null) {
            showToastMessage("请先登陆");
            onClickLogin(null);
            return;
        }

        Intent intent = new Intent(getActivity(), MyChatActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.my_favorites)
    public void onClickMyFavorites(View v) {
        if (loginModel == null) {
            showToastMessage("请先登陆");
            onClickLogin(null);
            return;
        }

        Intent intent = new Intent(getActivity(), MyFavoriteActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.my_replies)
    public void onClickMyReplies(View v) {
        if (loginModel == null) {
            showToastMessage("请先登陆");
            onClickLogin(null);
            return;
        }

        Intent intent = new Intent(getActivity(), MyRepliesActivity.class);
        startActivity(intent);
    }
}
