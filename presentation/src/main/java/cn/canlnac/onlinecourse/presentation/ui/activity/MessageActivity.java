package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMessagesInMessageViewComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessagesInMessageViewModule;
import cn.canlnac.onlinecourse.presentation.model.MessageListModel;
import cn.canlnac.onlinecourse.presentation.model.MessageModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetMessagesPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.MessageAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

public class MessageActivity extends BaseActivity {
    public static final int TO_READ_COUNT = 200;

    @BindView(R.id.message_list) ZrcListView zrcListView;

    private MessageAdapter unreadMessageAdapter;
    private MessageAdapter readMessageAdapter;
    private Handler handler;

    @BindView(R.id.unread_btn) TextView btnUnread;
    @BindView(R.id.read_btn) TextView btnRead;
    int currentTag = 0;

    int unreadStart = 0;
    int unreadCount = 10;
    int unreadTotal = 10;

    int readStart = 0;
    int readCount = 10;
    int readTotal = 10;

    boolean readInitial = false;

    List<MessageModel> unreadMessages = new ArrayList<>();
    List<MessageModel> readMessages = new ArrayList<>();

    @Inject
    GetMessagesPresenter getUnreadMessagesPresenter;
    @Inject
    GetMessagesPresenter getReadMessagesPresenter;

    private int toReadCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        //绑定视图
        ButterKnife.bind(this);

        handler = new Handler();

        // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
        float density = getResources().getDisplayMetrics().density;
        zrcListView.setFirstTopOffset((int) (0 * density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        zrcListView.setFootable(footer);

        // 设置列表项出现动画（可选）
        zrcListView.setItemAnimForTopIn(R.anim.topitem_in);
        zrcListView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        // 下拉刷新事件回调（可选）
        zrcListView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        zrcListView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

        unreadMessageAdapter = new MessageAdapter(this, unreadMessages, false);
        readMessageAdapter = new MessageAdapter(this, readMessages, true);

        zrcListView.setAdapter(unreadMessageAdapter);
        zrcListView.refresh(); // 主动下拉刷新
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        Intent intent = new Intent();
        intent.putExtra("toReadCount", toReadCount);
        setResult(TO_READ_COUNT, intent);
        finish();
    }

    @OnClick(R.id.unread_btn)
    public void onClickUnread(View v) {
        if (currentTag == 1) {
            currentTag = 0;
            btnUnread.setBackgroundResource(R.drawable.background_border_left);
            btnUnread.setTextColor(Color.parseColor("#2196F3"));

            btnRead.setBackgroundResource(R.drawable.background_border_right);
            btnRead.setTextColor(Color.parseColor("#FFFFFF"));
            zrcListView.setAdapter(unreadMessageAdapter);
            unreadMessageAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.read_btn)
    public void onClickRead(View v) {
        if (currentTag == 0) {
            currentTag = 1;
            btnUnread.setBackgroundResource(R.drawable.background_border_left_reverse);
            btnUnread.setTextColor(Color.parseColor("#FFFFFF"));

            btnRead.setBackgroundResource(R.drawable.background_border_right_reverse);
            btnRead.setTextColor(Color.parseColor("#2196F3"));
            zrcListView.setAdapter(readMessageAdapter);

            if (!readInitial) {
                readInitial = true;
                zrcListView.refresh();
                readMessageAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        zrcListView.setRefreshFail(message);
    }

    @Override
    public void onDestroy() {
        zrcListView.setOnLoadMoreStartListener(null);
        zrcListView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    /**
     * 刷新
     */
    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentTag == 0) {
                    unreadStart = 0;
                    unreadMessages.clear();
                    unreadMessageAdapter.notifyDataSetChanged();

                    if (unreadStart == 0) {
                        //获取未读消息
                        initialize(0, false);
                    }
                } else {
                    readStart = 0;
                    readMessages.clear();
                    readMessageAdapter.notifyDataSetChanged();

                    if (readStart == 0) {
                        //获取已读消息
                        initialize(0, true);
                    }
                }
            }
        }, 200);
    }

    /**
     * 加载更多
     */
    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentTag == 0) {
                    unreadStart += unreadCount;
                    if(unreadStart < unreadCount){
                        //获取未读消息
                        initialize(1, false);
                    }else{
                        zrcListView.stopLoadMore();
                    }
                } else {
                    readStart += readCount;
                    if(readStart < readCount){
                        //获取已读消息
                        initialize(1, true);
                    }else{
                        zrcListView.stopLoadMore();
                    }
                }
            }
        }, 1000);
    }

    /**
     * 刷新显示消息
     * @param messages
     * @param isRead
     */
    public void showRefreshMessages(MessageListModel messages, boolean isRead) {
        if (isRead) {
            readTotal = messages.getTotal();
            readMessages.addAll(messages.getMessages());
            readMessageAdapter.notifyDataSetChanged();
            zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
            zrcListView.startLoadMore();                // 开启LoadingMore功能
        } else {
            unreadTotal = messages.getTotal();
            unreadMessages.addAll(messages.getMessages());
            unreadMessageAdapter.notifyDataSetChanged();
            zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
            zrcListView.startLoadMore();                // 开启LoadingMore功能
        }
    }

    /**
     * 更新显示消息
     * @param messages
     * @param isRead
     */
    public void showLoadMoreMessages(MessageListModel messages, boolean isRead) {
        if (isRead) {
            readTotal = messages.getTotal();
            readMessages.addAll(messages.getMessages());
            readMessageAdapter.notifyDataSetChanged();
            zrcListView.setLoadMoreSuccess();
        } else {
            unreadTotal = messages.getTotal();
            unreadMessages.addAll(messages.getMessages());
            unreadMessageAdapter.notifyDataSetChanged();
            zrcListView.setLoadMoreSuccess();
        }
    }

    public void initialize(int state, boolean isRead){
        if (isRead) {
            DaggerGetMessagesInMessageViewComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .getMessagesInMessageViewModule(new GetMessagesInMessageViewModule(readStart,readCount, true))
                    .build().inject(this);

            getReadMessagesPresenter.setView(this, state, isRead);
            getReadMessagesPresenter.initialize();
        } else {
            DaggerGetMessagesInMessageViewComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .getMessagesInMessageViewModule(new GetMessagesInMessageViewModule(unreadStart,unreadCount, false))
                    .build().inject(this);

            getUnreadMessagesPresenter.setView(this, state, isRead);
            getUnreadMessagesPresenter.initialize();
        }
    }

    public void deleteMessage(MessageModel messageModel) {
        readMessages.remove(messageModel);
        readMessageAdapter.notifyDataSetChanged();
    }

    public void readMessage() {
        toReadCount++;
    }
}
