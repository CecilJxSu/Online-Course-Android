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
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMyChatsComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyChatsModule;
import cn.canlnac.onlinecourse.presentation.model.ChatListModel;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetMyChatsPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 我的收藏.
 */

public class MyFavoriteActivity extends BaseActivity {

    @BindView(R.id.myfavorites_list)
    ZrcListView zrcListView;

    @BindView(R.id.my_favorites_courses_btn) TextView btnCourses;
    @BindView(R.id.my_favorites_chats_btn) TextView btnChats;
    int currentTag = 0;

    private ChatAdapter adapter;
    private Handler handler;

    int start = 0;
    int count = 10;
    int total = 10;

    List<ChatModel> favorites = new ArrayList<>();

    @Inject
    GetMyChatsPresenter getMyChatsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfavorites);
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

        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyFavoriteActivity.this, ChatActivity.class);
                intent.putExtra("chatId", favorites.get(position).getId());      //话题ID
                MyFavoriteActivity.this.startActivity(intent);
            }
        });

        adapter = new ChatAdapter(this, favorites);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        finish();
    }

    @OnClick(R.id.my_favorites_courses_btn)
    public void onClickCourses(View v) {
        if (currentTag == 1) {
            currentTag = 0;
            btnCourses.setBackgroundResource(R.drawable.background_border_left);
            btnCourses.setTextColor(Color.parseColor("#2196F3"));

            btnChats.setBackgroundResource(R.drawable.background_border_right);
            btnChats.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @OnClick(R.id.my_favorites_chats_btn)
    public void onClickChats(View v) {
        if (currentTag == 0) {
            currentTag = 1;
            btnCourses.setBackgroundResource(R.drawable.background_border_left_reverse);
            btnCourses.setTextColor(Color.parseColor("#FFFFFF"));

            btnChats.setBackgroundResource(R.drawable.background_border_right_reverse);
            btnChats.setTextColor(Color.parseColor("#2196F3"));
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
                start = 0;
                favorites.clear();
                adapter.notifyDataSetChanged();

                if (start == 0) {
                    //获取我的话题
                    initialize(0);
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
                start += count;
                if(start < total){
                    //获取我的话题
                    initialize(1);
                }else{
                    zrcListView.stopLoadMore();
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
        favorites.addAll(chatListModel.getChats());
        adapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示话题
     * @param chatListModel
     */
    public void showLoadMoreChats(ChatListModel chatListModel) {
        total = chatListModel.getTotal();
        favorites.addAll(chatListModel.getChats());
        adapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }

    public void initialize(int state){
        /*DaggerGetMyChatsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getMyChatsModule(new GetMyChatsModule(start,count))
                .build().inject(this);

        getMyChatsPresenter.setView(this, state);
        getMyChatsPresenter.initialize();*/
    }
}
