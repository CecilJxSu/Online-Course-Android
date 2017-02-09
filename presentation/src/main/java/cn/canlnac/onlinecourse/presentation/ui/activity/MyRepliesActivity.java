package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 我的回复.
 */

public class MyRepliesActivity extends BaseActivity {

    @BindView(R.id.my_chats_title) TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychats);
        //绑定视图
        ButterKnife.bind(this);

        title.setText("我的回复");
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        finish();
    }
}
