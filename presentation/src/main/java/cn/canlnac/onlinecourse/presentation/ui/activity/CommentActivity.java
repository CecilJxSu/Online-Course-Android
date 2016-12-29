package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 话题评论.
 */

public class CommentActivity extends BaseActivity {
    @BindView(R.id.chat_comments_list) ZrcListView zrcListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_comments);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.chat_comment_close)
    public void onClickClose(View v) {
        this.finish();
    }
}
