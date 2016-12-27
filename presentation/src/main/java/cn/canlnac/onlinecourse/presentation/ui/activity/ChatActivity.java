package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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

    @BindView(R.id.chat_detail_body) ScrollView body;

    @BindView(R.id.chat_detail_watch_count) TextView watchCount;
    @BindView(R.id.chat_detail_comment_img) ImageView commentImg;
    @BindView(R.id.chat_detail_comment_count) TextView commentCount;
    @BindView(R.id.chat_detail_footer) RelativeLayout footer;

    private boolean isShowFooter = false;

    private int clickFlag = 0;
    private float moveX = -1;
    private float moveY = -1;

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

        body.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!isShowFooter || event.getY() < body.getHeight() - footer.getHeight() - 2) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            clickFlag = 1;
                            moveX = -1;
                            moveY= -1;
                            break;
                        case MotionEvent.ACTION_UP:
                            moveX = -1;
                            moveY= -1;
                            if (clickFlag == 1) {
                                clickFlag = 0;
                                //点击事件
                                isShowFooter = !isShowFooter;
                                if (isShowFooter) {
                                    footer.setVisibility(View.VISIBLE);
                                } else {
                                    footer.setVisibility(View.INVISIBLE);
                                }
                            }
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (moveY == -1 || moveX == -1) {
                                moveX = event.getX();
                                moveY = event.getY();
                            } else {
                                if (clickFlag == 0) {
                                    break;
                                }
                                if (event.getY() != moveY || event.getX() != moveX) {
                                    clickFlag = 0;
                                }
                            }
                            break;
                        default:
                            clickFlag = 0;

                    }
                }
                return false;
            }
        });

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
