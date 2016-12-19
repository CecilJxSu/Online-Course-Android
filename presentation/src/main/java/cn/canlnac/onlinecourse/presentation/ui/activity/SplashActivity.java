package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 应用启动页.
 */

public class SplashActivity extends Activity {

    @BindView(R.id.background_start) ImageView start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.background);

        //绑定视图
        ButterKnife.bind(this);
    }

    @OnClick(R.id.background_start)
    public void onClickStart(View view) {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
