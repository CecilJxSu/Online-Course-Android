package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerDeleteMessageComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMessageComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.DeleteMessageModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessageModule;
import cn.canlnac.onlinecourse.presentation.model.MessageModel;
import cn.canlnac.onlinecourse.presentation.model.SimpleUserModel;
import cn.canlnac.onlinecourse.presentation.presenter.DeleteMessagePresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetMessagePresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.MessageActivity;

/**
 * 消息 view holder.
 */

public class MessageViewHolder {
    @BindView(R.id.header) SimpleDraweeView header;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.time) TextView time;
    @BindView(R.id.content) TextView content;
    @BindView(R.id.delete) TextView delete;
    @BindView(R.id.toRead) TextView toRead;

    private MessageActivity activity;

    private MessageModel messageModel;

    @Inject
    GetMessagePresenter getMessagePresenter;
    @Inject
    DeleteMessagePresenter deleteMessagePresenter;

    public MessageViewHolder(final Activity activity, View view, final MessageModel message, boolean isRead, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = (MessageActivity)activity;

        this.messageModel = message;

        if (message.getFromUser() != null) {
            header.setImageURI(message.getFromUser().getIconUrl());
            username.setText(message.getFromUser().getName());
        } else {
            username.setText("系统消息");
        }

        time.setText(prettyTime.format(new Date(message.getDate())));
        if (message.getContent() == null || message.getContent().trim().isEmpty()) {
            String contentStr = "";

            if (message.getFromUser() == null) {
                SimpleUserModel simpleUserModel = new SimpleUserModel();
                simpleUserModel.setName("有人");
                message.setFromUser(simpleUserModel);
            }

            if (message.getPositionModel() != null) {
                switch (message.getType()) {
                    case "course":
                        switch (message.getActionType()) {
                            case "like":
                                contentStr = message.getFromUser().getName() + " 点赞了你的课程: \n《" + message.getPositionModel().getName() + "》";
                                break;
                            case "favorite":
                                contentStr = message.getFromUser().getName() + " 收藏了你的课程: \n《" + message.getPositionModel().getName()+ "》";
                                break;
                            case "comment":
                                contentStr = message.getFromUser().getName() + " 评论了你的课程: \n《" + message.getPositionModel().getName()+ "》";
                                break;
                        }
                        break;
                    case "chat":
                        switch (message.getActionType()) {
                            case "like":
                                contentStr = message.getFromUser().getName() + " 点赞了你的话题: \n《" + message.getPositionModel().getName()+ "》";
                                break;
                            case "favorite":
                                contentStr = message.getFromUser().getName() + " 收藏了你的话题: \n《" + message.getPositionModel().getName()+ "》";
                                break;
                            case "comment":
                                contentStr = message.getFromUser().getName() + " 评论了你的话题: \n《" + message.getPositionModel().getName()+ "》";
                                break;
                        }
                        break;
                    case "comment":
                        switch (message.getActionType()) {
                            case "like":
                                contentStr = message.getFromUser().getName() + " 点赞了你的评论: \n" + message.getPositionModel().getName();
                                break;
                            case "favorite":
                                contentStr = message.getFromUser().getName() + " 收藏了你的评论: \n" + message.getPositionModel().getName();
                                break;
                            case "reply":
                                contentStr = message.getFromUser().getName() + " 回复了你的评论: \n" + message.getPositionModel().getName();
                                break;
                            case "comment":
                                contentStr = message.getFromUser().getName() + " 回复了你的评论: \n" + message.getPositionModel().getName();
                                break;
                        }
                        break;
                    case "system":
                        break;
                    case "user":
                        switch (message.getActionType()) {
                            case "following":
                                contentStr = message.getFromUser().getName() + " 关注了你！";
                                break;
                        }
                        break;
                    default:
                }
            } else {
                if (message.getType().equals("user") && message.getActionType().equals("following")) {
                    contentStr = message.getFromUser().getName() + " 关注了你！";
                }
            }

            message.setContent(contentStr);
        }
        content.setText(message.getContent());

        if (isRead) {
            delete.setVisibility(View.VISIBLE);
        } else {
            toRead.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.toRead)
    public void onClickToRead(View v){
        toRead.setVisibility(View.GONE);

        DaggerGetMessageComponent.builder()
                .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(activity))
                .getMessageModule(new GetMessageModule(messageModel.getId()))
                .build().inject(this);

        getMessagePresenter.setView(activity);
        getMessagePresenter.initialize();
    }

    @OnClick(R.id.delete)
    public void onClickDelete(View v) {
        delete.setVisibility(View.GONE);

        DaggerDeleteMessageComponent.builder()
                .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(activity))
                .deleteMessageModule(new DeleteMessageModule(messageModel.getId()))
                .build().inject(this);
        deleteMessagePresenter.setView(activity);
        deleteMessagePresenter.initialize();
    }
}
