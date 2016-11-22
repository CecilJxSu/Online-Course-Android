package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.Post;
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.PostAdapter;
import cn.canlnac.onlinecourse.presentation.ui.view.DrawableCenterTextView;
import cn.canlnac.onlinecourse.presentation.ui.view.NineGridImageView;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView.OnStartListener;

/**
 * 话题.
 */

public class TabFragment2 extends Fragment {
    @BindView(R.id.menu_red)
    FloatingActionMenu menu;

    @BindView(R.id.create_chat)
    FloatingActionButton createChatButton;

    @BindView(R.id.zListView)
    ZrcListView listView;

    private Handler handler;
    private ArrayList<String> msgs;
    private int pageId = -1;
    private MyAdapter adapter;

    private static final String[][] names = new String[][]{
            {"加拿大"},
            {"德国"}
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);
        //绑定视图
        ButterKnife.bind(this, view);

        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabFragment2.this.getActivity(), PostChatActivity.class);
                TabFragment2.this.startActivity(intent);
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

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.refresh(); // 主动下拉刷新

        return view;
    }

    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int rand = (int) (Math.random() * 2); // 随机数模拟成功失败。这里从有数据开始。
                if(rand == 0 || pageId == -1){
                    pageId = 0;
                    msgs = new ArrayList<>();
                    for(String name:names[0]){
                        msgs.add(name);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setRefreshSuccess("加载成功"); // 通知加载成功
                    listView.startLoadMore(); // 开启LoadingMore功能
                }else{
                    listView.setRefreshFail("加载失败");
                }
            }
        }, 2 * 1000);
    }

    private void loadMore(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageId++;
                if(pageId<names.length){
                    for(String name:names[pageId]){
                        msgs.add(name);
                    }
                    adapter.notifyDataSetChanged();
                    listView.setLoadMoreSuccess();
                }else{
                    listView.stopLoadMore();
                }
            }
        }, 2 * 1000);
    }

    class MyAdapter extends BaseAdapter {
        @BindView(R.id.chat_list_view_head)
        ImageView headImage;
        @BindView(R.id.chat_list_view_username)
        TextView username;
        @BindView(R.id.chat_list_view_time_and_reviews)
        TextView timeAndReviews;
        @BindView(R.id.chat_list_view_title)
        TextView title;
        /*@BindView(R.id.chat_list_share)
        TextView share;
        @BindView(R.id.chat_list_comment)
        TextView comment;
        @BindView(R.id.chat_list_thump_up)
        TextView thumpUp;*/

        @BindView(R.id.rv_post_list)
        RecyclerView mRvPostLister;
        PostAdapter mNineImageAdapter;

        private List<Post> mPostList;
        private String[] IMG_URL_LIST = {
                "http://ac-QYgvX1CC.clouddn.com/36f0523ee1888a57.jpg",
                "http://ac-QYgvX1CC.clouddn.com/07915a0154ac4a64.jpg",
                "http://ac-QYgvX1CC.clouddn.com/9ec4bc44bfaf07ed.jpg",
                "http://ac-QYgvX1CC.clouddn.com/fa85037f97e8191f.jpg",
                "http://ac-QYgvX1CC.clouddn.com/de13315600ba1cff.jpg",
                "http://ac-QYgvX1CC.clouddn.com/15c5c50e941ba6b0.jpg",
                "http://ac-QYgvX1CC.clouddn.com/10762c593798466a.jpg",
                "http://ac-QYgvX1CC.clouddn.com/eaf1c9d55c5f9afd.jpg",
                "http://ac-QYgvX1CC.clouddn.com/ad99de83e1e3f7d4.jpg",
                "http://ac-QYgvX1CC.clouddn.com/233a5f70512befcc.jpg",
        };

        @Override
        public int getCount() {
            return msgs==null ? 0 : msgs.size();
        }
        @Override
        public Object getItem(int position) {
            return msgs.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;/*
            if(convertView==null) {*/
            view = TabFragment2.this.getActivity().getLayoutInflater().inflate(R.layout.chat_list_view, null);
            /*}else{
                view =  convertView;
            }*/

            //绑定视图
            ButterKnife.bind(MyAdapter.this, view);

            headImage.setImageResource(R.drawable.header);

            username.setText("弗拉德丽嘉");

            timeAndReviews.setText("昨天21:43\t 4282阅读量");

            title.setText("标题标题标题标题标题标题标题");

            /*Drawable shareDrawable = ContextCompat.getDrawable(TabFragment2.this.getActivity(),R.drawable.share_icon);
            shareDrawable.setBounds(0, 0, 40, 40);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
            share.setCompoundDrawables(shareDrawable, null, null, null);//只放左边*/

            mPostList = new ArrayList<>();

            List<String> imgUrls = new ArrayList<>();
            imgUrls.addAll(Arrays.asList(IMG_URL_LIST).subList(0, 8));
            Post post = new Post("Am I handsome? Am I handsome? Am I handsome?Am I handsome? Am I handsome? Am I handsome?Am I handsome? Am I handsome? Am I handsome?Am I handsome? Am I handsome? Am I handsome?", imgUrls);
            mPostList.add(post);

            mRvPostLister.setLayoutManager(new LinearLayoutManager(TabFragment2.this.getActivity()));

            mNineImageAdapter = new PostAdapter(TabFragment2.this.getActivity(), mPostList, NineGridImageView.STYLE_GRID);
            mRvPostLister.setAdapter(mNineImageAdapter);
            return view;
        }
    }
}
