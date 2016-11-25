package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 忘记密码.
 */

public class ForgetActivity extends Activity {
    @BindView(R.id.forget_close_button)
    Button closeButton;
    @BindView(R.id.forget_email)
    EditText email;
    @BindView(R.id.forget_verify)
    EditText verify;
    @BindView(R.id.forget_verify_send)
    TextView send;
    @BindView(R.id.forget_next)
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget);

        ButterKnife.bind(this);
    }

    /**
     * 关闭页面
     * @param v 视图
     */
    @OnClick(R.id.forget_close_button)
    public void onCloseActivity(View v){
        finish();
    }

    /**
     * 编辑框文字变化监听事件
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged({R.id.forget_email,R.id.forget_verify})
    public void onTextChange(CharSequence s, int start, int before, int count) {
        if (email.getText().length() > 0 && verify.getText().length() > 0) {
            next.setBackgroundColor(Color.parseColor("#2196F3"));
        } else {
            next.setBackgroundColor(Color.parseColor("#AFAFAF"));
        }
    }

    /**
     * 下一步按钮点击事件
     * @param v
     */
    @OnClick(R.id.forget_next)
    public void onClickNext(View v) {
        if (email.getText().length() > 0 && verify.getText().length() > 0) {
            Toast.makeText(getApplicationContext(), "下一步", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送验证码点击事件
     * @param v
     */
    @OnClick(R.id.forget_verify_send)
    public void onClickSend(View v) {
        Toast.makeText(getApplicationContext(), "发送", Toast.LENGTH_SHORT).show();
    }
}
