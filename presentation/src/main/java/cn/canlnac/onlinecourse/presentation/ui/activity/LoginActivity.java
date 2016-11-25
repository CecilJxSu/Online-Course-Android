package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 登录.
 */

public class LoginActivity extends Activity {
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
        } else {
            login.setBackgroundColor(Color.parseColor("#AFAFAF"));
        }
    }

    @OnClick(R.id.login_forget)
    public void onClickForget(View v) {
        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        LoginActivity.this.startActivity(intent);
    }
}
