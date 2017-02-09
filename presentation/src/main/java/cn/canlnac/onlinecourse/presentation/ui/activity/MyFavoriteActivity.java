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
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMyFavoriteChatsComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMyFavoriteCoursesComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyFavoriteChatsModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMyFavoriteCoursesModule;
import cn.canlnac.onlinecourse.presentation.model.ChatListModel;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.model.CourseListModel;
import cn.canlnac.onlinecourse.presentation.model.CourseModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetMyFavoriteChatsPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetMyFavoriteCoursesPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.ChatAdapter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseListAdapter;
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

    private ChatAdapter chatAdapter;
    private CourseListAdapter courseAdapter;
    private Handler handler;

    int chatStart = 0;
    int chatCount = 10;
    int chatTotal = 10;

    int courseStart = 0;
    int courseCount = 10;
    int courseTotal = 10;

    boolean chatInitial = false;

    List<ChatModel> chats = new ArrayList<>();
    List<CourseModel> courses = new ArrayList<>();

    @Inject
    GetMyFavoriteChatsPresenter getMyFavoriteChatsPresenter;

    @Inject
    GetMyFavoriteCoursesPresenter getMyFavoriteCoursesPresenter;

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
                Intent intent;
                if (currentTag == 0) {
                    intent = new Intent(MyFavoriteActivity.this, CourseActivity.class);
                    intent.putExtra("courseId", courses.get(position).getId());      //课程ID
                } else {
                    intent = new Intent(MyFavoriteActivity.this, ChatActivity.class);
                    intent.putExtra("chatId", chats.get(position).getId());      //话题ID

                }
                MyFavoriteActivity.this.startActivity(intent);
            }
        });

        chatAdapter = new ChatAdapter(this, chats);
        courseAdapter = new CourseListAdapter(this,courses);

        zrcListView.setAdapter(courseAdapter);
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
            zrcListView.setAdapter(courseAdapter);
            courseAdapter.notifyDataSetChanged();
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
            zrcListView.setAdapter(chatAdapter);

            if (!chatInitial) {
                chatInitial = true;
                zrcListView.refresh();
                chatAdapter.notifyDataSetChanged();
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
                    courseStart = 0;
                    courses.clear();
                    courseAdapter.notifyDataSetChanged();

                    if (courseStart == 0) {
                        //获取我的课程
                        initializeCourse(0);
                    }
                } else {
                    chatStart = 0;
                    chats.clear();
                    chatAdapter.notifyDataSetChanged();

                    if (chatStart == 0) {
                        //获取我的话题
                        initializeChat(0);
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
                    courseStart += courseCount;
                    if(courseStart < courseCount){
                        //获取我的课程
                        initializeCourse(1);
                    }else{
                        zrcListView.stopLoadMore();
                    }
                } else {
                    chatStart += chatCount;
                    if(chatStart < chatCount){
                        //获取我的话题
                        initializeChat(1);
                    }else{
                        zrcListView.stopLoadMore();
                    }
                }
            }
        }, 1000);
    }

    /**
     * 刷新显示话题
     * @param chatListModel
     */
    public void showRefreshChats(ChatListModel chatListModel) {
        chatTotal = chatListModel.getTotal();
        chats.addAll(chatListModel.getChats());
        chatAdapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 刷新显示课程
     * @param courseListModel
     */
    public void showRefreshCourses(CourseListModel courseListModel) {
        courseTotal = courseListModel.getTotal();
        courses.addAll(courseListModel.getCourses());
        courseAdapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示话题
     * @param chatListModel
     */
    public void showLoadMoreChats(ChatListModel chatListModel) {
        chatTotal = chatListModel.getTotal();
        chats.addAll(chatListModel.getChats());
        chatAdapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }

    /**
     * 更新显示课程
     * @param courseListModel
     */
    public void showLoadMoreCourses(CourseListModel courseListModel) {
        courseTotal = courseListModel.getTotal();
        courses.addAll(courseListModel.getCourses());
        courseAdapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }

    public void initializeChat(int state){
        DaggerGetMyFavoriteChatsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getMyFavoriteChatsModule(new GetMyFavoriteChatsModule(chatStart,chatCount))
                .build().inject(this);

        getMyFavoriteChatsPresenter.setView(this, state);
        getMyFavoriteChatsPresenter.initialize();
    }

    public void initializeCourse(int state){
        DaggerGetMyFavoriteCoursesComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getMyFavoriteCoursesModule(new GetMyFavoriteCoursesModule(courseStart,courseCount))
                .build().inject(this);

        getMyFavoriteCoursesPresenter.setView(this, state);
        getMyFavoriteCoursesPresenter.initialize();
    }
}
