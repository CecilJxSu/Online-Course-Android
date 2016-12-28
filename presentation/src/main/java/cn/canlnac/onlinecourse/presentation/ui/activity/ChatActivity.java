package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzhoujay.richtext.RichText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetUserProfileInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetUserProfileModule;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.model.ProfileModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetUserProfileInChatPresenter;

/**
 * 话题.
 */

public class ChatActivity extends BaseActivity {

    @BindView(R.id.chat_close_button) Button close;
    @BindView(R.id.chat_detail_body) ScrollView body;

    @BindView(R.id.chat_detail_header) SimpleDraweeView header;
    @BindView(R.id.chat_detail_name) TextView name;
    @BindView(R.id.chat_detail_department) TextView department;

    @BindView(R.id.chat_detail_title) TextView title;
    @BindView(R.id.chat_detail_content) TextView content;

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
    @Inject
    GetUserProfileInChatPresenter getUserProfileInChatPresenter;

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

        footer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFooter();
            }
        });

        body.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getY() < body.getHeight() - body.getScrollY() - footer.getHeight() - 2) {
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
                                toggleFooter();
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

    public void toggleFooter() {
        isShowFooter = !isShowFooter;
        if (isShowFooter) {
            footer.setVisibility(View.VISIBLE);
        } else {
            footer.setVisibility(View.INVISIBLE);
        }
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

    @OnClick(R.id.chat_detail_comment_img)
    public void onClickComment(View v) {
        Intent intent = new Intent(this, CommentActivity.class);
        startActivity(intent);
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
        header.setImageURI(chatModel.getAuthor().getIconUrl());
        watchCount.setText(chatModel.getWatchCount()+"");
        commentCount.setText(chatModel.getCommentCount()+"");
        title.setText(chatModel.getTitle());

        RichText.fromHtml(chatModel.getHtml()).into(content);

        //获取用户资料
        DaggerGetUserProfileInChatComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getUserProfileModule(new GetUserProfileModule(chatModel.getAuthor().getId()))
                .build().inject(this);

        getUserProfileInChatPresenter.setView(ChatActivity.this);
        getUserProfileInChatPresenter.initialize();
    }

    /**
     * 显示用户资料
     * @param profileModel
     */
    public void showProfile(ProfileModel profileModel) {
        String userType = "学生";
        if (profileModel.getUserStatus().equals("teacher")) {
            userType = "老师";
        } else if (profileModel.getUserStatus().equals("admin")) {
            userType = "管理员";
        }
        name.setText(profileModel.getRealname() + " · " + userType);
        department.setText(profileModel.getDepartment() + " - " + profileModel.getMajor());
    }
}
