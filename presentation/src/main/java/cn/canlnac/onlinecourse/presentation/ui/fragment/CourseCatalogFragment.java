package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.holder.CatalogViewHolder;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.holder.CatalogViewHolder.IconTreeItem;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.model.TreeNode;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.view.AndroidTreeView;

/**
 * 课程目录.
 */

public class CourseCatalogFragment extends Fragment {
    @BindView(R.id.course_catalog_container)
    ViewGroup mContainer;

    private final TreeNode root = TreeNode.root();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.course_catalogs, container, false);
        //绑定视图
        ButterKnife.bind(this, view);

        for (int i = 1; i <= 5; i++) {
            IconTreeItem parentItem = new IconTreeItem();
            CatalogViewHolder catalogViewHolder1 = new CatalogViewHolder(getActivity());
            parentItem.text = "第 "+ i +" 章: 认识Java";
            TreeNode parent = new TreeNode(parentItem).setViewHolder(catalogViewHolder1);

            for (int j = 1; j <= 5; j++) {
                IconTreeItem iconTreeItem = new IconTreeItem();
                iconTreeItem.icon = R.drawable.favorite;
                iconTreeItem.text = i+"-"+j+". 第"+j+"小节";
                iconTreeItem.duration = "12:01";
                CatalogViewHolder catalogViewHolder2 = new CatalogViewHolder(getActivity());
                TreeNode child = new TreeNode(iconTreeItem).setViewHolder(catalogViewHolder2);
                parent.addChildren(child);
            }

            parent.setExpanded(true);
            root.addChild(parent);
        }

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        mContainer.addView(tView.getView());

        return view;
    }
}
