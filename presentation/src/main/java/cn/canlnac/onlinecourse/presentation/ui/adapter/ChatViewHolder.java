package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerFavoriteChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerLikeChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUnfavoriteChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUnlikeChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.FavoriteChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.LikeChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnfavoriteChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UnlikeChatModule;
import cn.canlnac.onlinecourse.presentation.model.ChatModel;
import cn.canlnac.onlinecourse.presentation.model.Post;
import cn.canlnac.onlinecourse.presentation.presenter.FavoriteChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.LikeChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UnfavoriteChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UnlikeChatPresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.LoginActivity;
import cn.canlnac.onlinecourse.presentation.ui.view.NineGridImageView;

/**
 * 话题 view holder.
 */

public class ChatViewHolder {
    @BindView(R.id.chat_list_view_head) SimpleDraweeView userIcon;
    @BindView(R.id.chat_list_view_username) TextView username;
    @BindView(R.id.chat_list_view_time_and_reviews) TextView timeAndReviews;
    @BindView(R.id.chat_list_view_title) TextView title;

    @BindView(R.id.chat_share) ImageView shareImg;
    @BindView(R.id.chat_favorite) ImageView favoriteImg;
    @BindView(R.id.chat_like) ImageView likeImg;

    @BindView(R.id.chat_list_share)
    TextView share;
    @BindView(R.id.chat_list_favorite)
    TextView favorite;
    @BindView(R.id.chat_list_thump_up)
    TextView thumpUp;

    private Activity activity;
    private int chatId;

    @BindView(R.id.rv_post_list)
    RecyclerView mRvPostLister;
    PostAdapter mNineImageAdapter;

    @Inject
    LikeChatPresenter likeChatPresenter;
    @Inject
    UnlikeChatPresenter unlikeChatPresenter;
    @Inject
    FavoriteChatPresenter favoriteChatPresenter;
    @Inject
    UnfavoriteChatPresenter unfavoriteChatPresenter;

    private boolean isLike;
    private boolean isFavorite;

    public ChatViewHolder(Activity activity, View view, ChatModel chat, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = activity;

        this.chatId = chat.getId();

        userIcon.setImageURI(chat.getAuthor().getIconUrl());
        username.setText(chat.getAuthor().getName());

        timeAndReviews.setText(prettyTime.format(new Date(chat.getDate())) + "\t " + chat.getWatchCount() +"阅读量");
        title.setText(chat.getTitle());

        favorite.setText(chat.getFavoriteCount()+"");
        thumpUp.setText(chat.getLikeCount()+"");

        changeLike(chat.isLike());
        changeFavorite(chat.isFavorite());

        List<Post> mPostList = new ArrayList<>();

        String content = chat.getContent();
        if (content.length() >= 150) {
            content = content.substring(0, 150) + "...";
        }
        Post post = new Post(content, chat.getPictureUrls());
        mPostList.add(post);

        mRvPostLister.setLayoutManager(new LinearLayoutManager(activity));

        mNineImageAdapter = new PostAdapter(activity, mPostList, NineGridImageView.STYLE_GRID);
        mRvPostLister.setAdapter(mNineImageAdapter);
    }

    /**
     * 点赞
     * @param v
     */
    @OnClick(R.id.chat_like)
    public void onClickLike(View v) {
        if (isLike) {
            //取消点赞
            DaggerUnlikeChatComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .unlikeChatModule(new UnlikeChatModule(chatId))
                    .build().inject(ChatViewHolder.this);

            unlikeChatPresenter.setView(ChatViewHolder.this);
            unlikeChatPresenter.initialize();
        } else {
            //点赞
            DaggerLikeChatComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .likeChatModule(new LikeChatModule(chatId))
                    .build().inject(ChatViewHolder.this);

            likeChatPresenter.setView(ChatViewHolder.this);
            likeChatPresenter.initialize();
        }
    }

    /**
     * 改变点赞状态
     * @param isLike
     */
    public void changeLike(boolean isLike) {
        this.isLike = isLike;
        if (isLike) {
            likeImg.setImageResource(R.drawable.thump_up_green_icon);
        } else {
            likeImg.setImageResource(R.drawable.thump_up_icon);
        }
    }

    /**
     * 修改点赞数
     * @param isLike
     */
    public void changeLikeCount(boolean isLike) {
        int count = Integer.parseInt(thumpUp.getText().toString());
        if (isLike) {
            thumpUp.setText(++count+"");
        } else {
            thumpUp.setText(--count+"");
        }
    }

    /**
     * 收藏
     * @param v
     */
    @OnClick(R.id.chat_favorite)
    public void onClickFavorite(View v) {
        if (isFavorite) {
            //取消收藏
            DaggerUnfavoriteChatComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .unfavoriteChatModule(new UnfavoriteChatModule(chatId))
                    .build().inject(ChatViewHolder.this);

            unfavoriteChatPresenter.setView(ChatViewHolder.this);
            unfavoriteChatPresenter.initialize();
        } else {
            //收藏
            DaggerFavoriteChatComponent.builder()
                    .applicationComponent(((AndroidApplication) activity.getApplication()).getApplicationComponent())
                    .activityModule(new ActivityModule(activity))
                    .favoriteChatModule(new FavoriteChatModule(chatId))
                    .build().inject(ChatViewHolder.this);

            favoriteChatPresenter.setView(ChatViewHolder.this);
            favoriteChatPresenter.initialize();
        }
    }

    /**
     * 改变收藏状态
     * @param isFavorite
     */
    public void changeFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
        if (isFavorite) {
            favoriteImg.setImageResource(R.drawable.favorite_green);
        } else {
            favoriteImg.setImageResource(R.drawable.unfavorite);
        }
    }

    /**
     * 修改收藏数
     * @param isFavorite
     */
    public void changeFavoriteCount(boolean isFavorite) {
        int count = Integer.parseInt(favorite.getText().toString());
        if (isFavorite) {
            favorite.setText(++count+"");
        } else {
            favorite.setText(--count+"");
        }
    }

    /**
     * 显示消息
     * @param message   消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 未登陆用户需要登陆
     */
    public void toLogin() {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }
}
