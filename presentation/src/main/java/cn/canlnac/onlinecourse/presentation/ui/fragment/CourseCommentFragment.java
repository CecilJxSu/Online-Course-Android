package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CommentModel;
import cn.canlnac.onlinecourse.presentation.model.ReplyModel;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CommentAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 课程评论.
 */

public class CourseCommentFragment extends Fragment {
    @BindView(R.id.course_comments_list)
    ZrcListView zrcListView;

    private CommentAdapter adapter;
    private Handler handler;

    private ArrayList<String> msgs;
    private int pageId = -1;

    private static final String[][] names = new String[][]{
            {"加拿大"},
            {"德国"}
    };

    List<CommentModel> comments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_comments, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        handler = new Handler();

        // 设置默认偏移量，主要用于实现透明标题栏功能。（可选）
        float density = getResources().getDisplayMetrics().density;
        zrcListView.setFirstTopOffset((int) (0 * density));

        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this.getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        zrcListView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this.getActivity());
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

        CommentModel comment1 = new CommentModel();
        comment1.setUserIcon(R.drawable.header);
        comment1.setUserName("弗拉德丽嘉1");
        comment1.setContent("啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦" +
                "啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦" +
                "啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦");
        comment1.setPostTime("昨天");
        comment1.setLikeCount("100");
        comment1.setLike(false);
        comment1.setReply(true);

        List<ReplyModel> replies = new ArrayList<>();
        ReplyModel reply1 = new ReplyModel();
        reply1.setUserName("abdsfdsffsdf");
        reply1.setContent("啦啦啦!!!");
        reply1.setPostTime("一小时前");
        reply1.setReply(true);

        ReplyModel reply2 = new ReplyModel();
        reply2.setUserName("弗拉德丽嘉1");
        reply2.setToUserName("abdsfdsffsdf");
        reply2.setContent("Bla bla blaBla bla blaBla bla blaBla bla blaBla bla blaBla bla bla!!!");
        reply2.setPostTime("一小时前");
        reply2.setReply(false);

        replies.add(reply1);
        replies.add(reply2);

        comment1.setReplies(replies);
        comments.add(comment1);

        CommentModel comment2 = new CommentModel();
        comment2.setUserIcon(R.drawable.header);
        comment2.setUserName("弗拉德丽嘉2");
        comment2.setContent("Blala...Blala...Blala...Blala..." +
                "Blala...Blala...Blala...Blala..." +
                "Blala...Blala...Blala...Blala...");
        comment2.setPostTime("1小时前");
        comment2.setLikeCount("212");
        comment2.setLike(true);
        comment2.setReply(false);
        comments.add(comment2);

        adapter = new CommentAdapter(getActivity(), comments);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新

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
                    zrcListView.setRefreshSuccess("加载成功"); // 通知加载成功
                    zrcListView.startLoadMore(); // 开启LoadingMore功能
                }else{
                    zrcListView.setRefreshFail("加载失败");
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
                    zrcListView.setLoadMoreSuccess();
                }else{
                    zrcListView.stopLoadMore();
                }
            }
        }, 2 * 1000);
    }
}
