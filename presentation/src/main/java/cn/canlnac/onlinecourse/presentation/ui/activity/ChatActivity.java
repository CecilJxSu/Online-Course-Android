package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatModule;
import cn.canlnac.onlinecourse.presentation.presenter.GetChatPresenter;

/**
 * 话题.
 */

public class ChatActivity extends BaseActivity {
    private int chatId;

    @Inject
    GetChatPresenter getChatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_details);
        ButterKnife.bind(this);

        //获取话题Id
        this.chatId = getIntent().getIntExtra("chatId", -1);

        if (this.chatId == -1) {
            this.finish();
        }

        this.initialize();
    }

    public void initialize() {
        //获取话题
        DaggerGetChatComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getChatModule(new GetChatModule(chatId))
                .build().inject(this);

        getChatPresenter.setView(ChatActivity.this);
        getChatPresenter.initialize();
    }
}
