package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.HasComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerLoginComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.LoginComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LoginModule;
import cn.canlnac.onlinecourse.presentation.model.LoginModel;
import cn.canlnac.onlinecourse.presentation.presenter.LoginPresenter;

/**
 * 登录.
 */

public class LoginActivity extends BaseActivity implements HasComponent<LoginComponent> {
    @BindView(R.id.login_close_button)
    Button closeButton;
    @BindView(R.id.login_username)
    EditText username;
    @BindView(R.id.login_password)
    EditText password;
    @BindView(R.id.login_done)
    Button login;
    @BindView(R.id.login_forget)
    TextView forget;

    private static boolean canSubmit = false;

    @Inject
    LoginPresenter loginPresenter;

    private LoginComponent loginComponent;

    @Override
    public LoginComponent getComponent() {
        return loginComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ButterKnife.bind(this);
    }

    /**
     * 关闭页面
     * @param v 视图
     */
    @OnClick(R.id.login_close_button)
    public void onCloseActivity(View v){
        LoginActivity.this.finish();
    }

    /**
     * 编辑框文字变化监听事件
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged({R.id.login_username,R.id.login_password})
    public void onTextChange(CharSequence s, int start, int before, int count) {
        if (username.getText().length() > 0 && password.getText().length() > 0) {
            login.setBackgroundColor(Color.parseColor("#2196F3"));
            canSubmit = true;
        } else {
            login.setBackgroundColor(Color.parseColor("#AFAFAF"));
            canSubmit = false;
        }
    }

    @OnClick(R.id.login_forget)
    public void onClickForget(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        LoginActivity.this.startActivity(intent);
    }

    @OnClick(R.id.login_done)
    public void onClickDone(View v) {
        if (canSubmit) {
            this.loginComponent = DaggerLoginComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .loginModule(new LoginModule(username.getText().toString(), password.getText().toString()))
                    .build();
            this.getComponent(LoginComponent.class).inject(this);

            this.loginPresenter.setView(this);
            this.loginPresenter.initialize();
        }
    }

    public void loginBack(LoginModel loginModel) {
        Intent intent = new Intent();
        intent.putExtra("loginModel", loginModel);
        setResult(200, intent);
        finish();
    }
}
