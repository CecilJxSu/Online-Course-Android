package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.CatalogModel;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.holder.CatalogViewHolder;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.holder.CatalogViewHolder.IconTreeItem;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.model.TreeNode;
import cn.canlnac.onlinecourse.presentation.ui.widget.TreeView.view.AndroidTreeView;
import cn.canlnac.onlinecourse.presentation.util.APIUtil;

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

        return view;
    }

    /**
     * 显示课程列表
     * @param catalogModelList
     */
    public void showCatalogs(List<CatalogModel> catalogModelList) {
        Map<Integer,TreeNode> parents = new HashMap<>();
        //添加章
        for (CatalogModel catalogModel: catalogModelList) {
            if (!(catalogModel.getParentId() > 0)) {
                IconTreeItem parentItem = new IconTreeItem();
                CatalogViewHolder catalogViewHolder1 = new CatalogViewHolder(getActivity());
                parentItem.text = "第 "+ catalogModel.getIndex() +" 章: " + catalogModel.getName();
                TreeNode parent = new TreeNode(parentItem).setViewHolder(catalogViewHolder1);

                parents.put(catalogModel.getIndex(), parent);

                parent.setExpanded(true);
                root.addChild(parent);
            }
        }
        //添加节
        for (CatalogModel catalogModel: catalogModelList) {
            if (catalogModel.getParentId() > 0) {
                IconTreeItem iconTreeItem = new IconTreeItem();
                iconTreeItem.icon = R.drawable.watching_video;
                iconTreeItem.text = catalogModel.getParentId()+"-"+catalogModel.getIndex()+". " + catalogModel.getName();

                iconTreeItem.duration = APIUtil.formatDuration(catalogModel.getDuration());

                CatalogViewHolder catalogViewHolder2 = new CatalogViewHolder(getActivity());
                TreeNode child = new TreeNode(iconTreeItem).setViewHolder(catalogViewHolder2);

                TreeNode parent = parents.get(catalogModel.getParentId());
                if (parent == null) {
                    IconTreeItem parentItem = new IconTreeItem();
                    CatalogViewHolder catalogViewHolder1 = new CatalogViewHolder(getActivity());
                    parent = new TreeNode(parentItem).setViewHolder(catalogViewHolder1);
                }

                parent.addChildren(child);
            }
        }

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        mContainer.addView(tView.getView());
    }
}
