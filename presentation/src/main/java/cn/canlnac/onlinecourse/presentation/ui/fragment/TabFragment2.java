package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetChatsChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetChatsChatModule;
import cn.canlnac.onlinecourse.presentation.model.ChatListModel;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetChatsPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView.OnStartListener;

/**
 * 话题.
 */

public class TabFragment2 extends BaseFragment {
    @BindView(R.id.menu_red)
    FloatingActionMenu menu;

    @BindView(R.id.create_chat)
    FloatingActionButton createChatButton;

    @BindView(R.id.zListView)
    ZrcListView listView;

    private ChatAdapter adapter;
    private Handler handler;
    private ArrayList<ChatModel> chats = new ArrayList<>();

    int start = 0;
    int count = 10;
    int total = 10;
    String sort = "date";

    @Inject
    GetChatsPresenter getChatsPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        final View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        //绑定视图
        ButterKnife.bind(this, view);

        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabFragment2.this.getContext(), PostChatActivity.class);
                TabFragment2.this.startActivityForResult(intent, 200);
                menu.toggle(true);
            }
        });

        handler = new Handler();

        // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
        float density = getResources().getDisplayMetrics().density;
        listView.setFirstTopOffset((int) (0 * density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this.getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this.getActivity());
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);

        // 下拉刷新事件回调（可选）
        listView.setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                refresh();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                loadMore();
            }
        });

        adapter = new ChatAdapter(this.getActivity(),chats);
        listView.setAdapter(adapter);
        listView.refresh(); // 主动下拉刷新

        return view;
    }

    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        listView.setRefreshFail(message);
    }

    @Override
    public void onDestroy() {
        listView.setOnLoadMoreStartListener(null);
        listView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start = 0;
                chats.clear();
                adapter.notifyDataSetChanged();

                if (start == 0) {
                    //获取话题列表
                    if (getActivity() != null) {
                        DaggerGetChatsChatComponent.builder()
                                .applicationComponent(getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getChatsChatModule(new GetChatsChatModule(start, count, sort))
                                .build().inject(TabFragment2.this);

                        getChatsPresenter.setView(TabFragment2.this, 0);
                        getChatsPresenter.initialize();
                    } else {
                        showRefreshError("加载完成");
                    }
                }
            }
        }, 200);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                start += count;
                if(start < total){
                    if (getActivity() != null) {
                        //获取课程列表
                        DaggerGetChatsChatComponent.builder()
                                .applicationComponent(getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getChatsChatModule(new GetChatsChatModule(start, count, sort))
                                .build().inject(TabFragment2.this);

                        getChatsPresenter.setView(TabFragment2.this, 1);
                        getChatsPresenter.initialize();
                    }
                }else{
                    listView.stopLoadMore();
                }
            }
        }, 1000);
    }

    /**
     * 刷新显示话题
     * @param chatListModel
     */
    public void showRefreshChats(ChatListModel chatListModel) {
        total = chatListModel.getTotal();
        chats.addAll(chatListModel.getChats());
        adapter.notifyDataSetChanged();
        listView.setRefreshSuccess("加载成功");  // 通知加载成功
        listView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示话题
     * @param chatListModel
     */
    public void showLoadMoreChats(ChatListModel chatListModel) {
        total = chatListModel.getTotal();
        chats.addAll(chatListModel.getChats());
        adapter.notifyDataSetChanged();
        listView.setLoadMoreSuccess();
    }

    /**
     * 创建话题情况
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //成功创建
        if (resultCode == 200) {
            refresh();
        }
    }
}
