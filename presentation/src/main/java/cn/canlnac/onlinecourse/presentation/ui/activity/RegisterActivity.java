package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 注册页面.
 */

public class RegisterActivity extends Activity {
    @BindView(R.id.register_close_button)
    Button closeButton;
    @BindView(R.id.register_username)
    EditText username;
    @BindView(R.id.register_email)
    EditText email;
    @BindView(R.id.register_password)
    EditText password;
    @BindView(R.id.register_done)
    Button done;
    @BindView(R.id.register_is_visible)
    ImageView isVisible;

    private final HideReturnsTransformationMethod visible = HideReturnsTransformationMethod.getInstance();
    private final PasswordTransformationMethod invisible = PasswordTransformationMethod.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ButterKnife.bind(this);

        //设置密码不可见
        password.setTransformationMethod(invisible);
    }

    /**
     * 关闭页面
     * @param v 视图
     */
    @OnClick(R.id.register_close_button)
    public void onCloseActivity(View v){
        RegisterActivity.this.finish();
    }

    /**
     * 密码是否可见
     * @param v 视图
     */
    @OnClick(R.id.register_is_visible)
    public void onClickIsVisible(View v){
        if (password.getTransformationMethod().equals(invisible)){
            password.setTransformationMethod(visible);
            password.setSelection(password.getText().length());
            isVisible.setImageResource(R.drawable.visible);
        } else {
            password.setTransformationMethod(invisible);
            password.setSelection(password.getText().length());
            isVisible.setImageResource(R.drawable.invisible);
        }
    }

    /**
     * 编辑框文字变化监听事件
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged({R.id.register_username,R.id.register_email,R.id.register_password})
    public void onTextChange(CharSequence s, int start, int before, int count) {
        if (username.getText().length() > 0 && password.getText().length() > 0 && email.getText().length() >0) {
            done.setBackgroundColor(Color.parseColor("#2196F3"));
        } else {
            done.setBackgroundColor(Color.parseColor("#AFAFAF"));
        }
    }
}
