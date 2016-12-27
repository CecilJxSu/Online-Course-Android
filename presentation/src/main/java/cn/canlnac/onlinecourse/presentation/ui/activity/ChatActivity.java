package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatModule;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetChatPresenter;

/**
 * 话题.
 */

public class ChatActivity extends BaseActivity {

    @BindView(R.id.chat_close_button) Button close;

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

    /**
     * 关闭
     * @param v
     */
    @OnClick(R.id.chat_close_button)
    public void onClickClose(View v) {
        if (getChatPresenter != null) {
            getChatPresenter.destroy();
        }

        this.finish();
    }

    /**
     * 初始化
     */
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

    /**
     * 显示话题
     * @param chatModel
     */
    public void showChat(ChatModel chatModel) {
        
    }
}
