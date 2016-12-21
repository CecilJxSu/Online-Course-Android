package cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.model.TreeNode;

/**
 * 目录.
 */
public class CatalogViewHolder extends TreeNode.BaseNodeViewHolder<CatalogViewHolder.IconTreeItem> {
    @BindView(R.id.course_catalog_list_image) ImageView image;
    @BindView(R.id.course_catalog_list_text) TextView text;
    @BindView(R.id.course_catalog_list_duration) TextView duration;

    public CatalogViewHolder(Context context) {
        super(context);
    }

    public void setColor(boolean isSet) {
        if (isSet) {
            image.setImageResource(R.drawable.watching_video_green);
            text.setTextColor(0xFF00FF00);
            duration.setTextColor(0xFF00FF00);
        } else {
            image.setImageResource(R.drawable.watching_video);
            text.setTextColor(0xFF999999);
            duration.setTextColor(0xFF999999);
        }
    }

    @Override
    public View createNodeView(TreeNode node, IconTreeItem value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.course_catalog_list, null, false);

        ButterKnife.bind(this, view);

        if (value.icon > 0) {
            image.setImageResource(value.icon);
        } else {
            ((ViewGroup)view).removeView(image);
        }
        if (value.text != null && !value.text.isEmpty()) {
            text.setText(value.text);
        }
        if (value.duration != null && !value.duration.isEmpty()) {
            duration.setText(value.duration);
        }

        return view;
    }

    public static class IconTreeItem {
        public int icon;
        public String text;
        public String duration;
    }
}
