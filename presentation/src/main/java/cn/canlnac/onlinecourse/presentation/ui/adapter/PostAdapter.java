package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.Post;
import cn.canlnac.onlinecourse.presentation.ui.view.NineGridImageView;

/**
 * Created by Jaeger on 16/2/24.
 *
 * Email: chjie.jaeger@gamil.com
 * GitHub: https://github.com/laobie
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private LayoutInflater mInflater;
    private List<Post> mPostList;
    private int mShowStyle;

    public PostAdapter(Context context, List<Post> postList, int showStyle) {
        super();
        mPostList = postList;
        mInflater = LayoutInflater.from(context);
        mShowStyle = showStyle;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(mPostList.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mShowStyle == NineGridImageView.STYLE_FILL) {
            return new PostViewHolder(mInflater.inflate(R.layout.nine_grid_view, parent, false));
        } else {
            return new PostViewHolder(mInflater.inflate(R.layout.nine_grid_view, parent, false));
        }
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ngl_images)
        NineGridImageView mNglContent;
        @BindView(R.id.chat_list_view_content)
        TextView mTvContent;

        NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String s) {
                Picasso.with(context)
                    .load(s)
                    .placeholder(R.drawable.ic_default_image)
                    .into(imageView);
            }

            @Override
            public ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            public void onItemImageClick(Context context, int index, List<String> list) {
                Toast.makeText(context, "image position is " + index, Toast.LENGTH_SHORT).show();
            }
        };

        public PostViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(PostViewHolder.this, itemView);

            mNglContent.setAdapter(mAdapter);
        }

        public void bind(Post post) {
            mNglContent.setImagesData(post.getImgUrlList());
            mTvContent.setText(post.getContent());
        }
    }
}
