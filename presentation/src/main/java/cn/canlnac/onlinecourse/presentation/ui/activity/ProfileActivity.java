package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;

public class ProfileActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //绑定视图
        ButterKnife.bind(this);
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        finish();
    }
}
