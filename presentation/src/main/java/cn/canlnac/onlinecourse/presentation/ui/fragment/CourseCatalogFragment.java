package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
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
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.QuestionActivity;
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

    private int currentPlayerIndex = -1;

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
        final Map<Integer,TreeNode> parents = new HashMap<>();
        final Map<Integer,TreeNode> childes = new HashMap<>();

        //添加章
        for (CatalogModel catalogModel: catalogModelList) {
            if (!(catalogModel.getParentId() > 0)) {
                IconTreeItem parentItem = new IconTreeItem();
                CatalogViewHolder catalogViewHolder1 = new CatalogViewHolder(getActivity());
                parentItem.text = "第 "+ catalogModel.getIndex() +" 章: " + catalogModel.getName();
                TreeNode parent = new TreeNode(parentItem).setViewHolder(catalogViewHolder1);

                parents.put(catalogModel.getId(), parent);

                parent.setExpanded(true);
                root.addChild(parent);
            }
        }
        //添加节
        for (final CatalogModel catalogModel: catalogModelList) {
            if (catalogModel.getParentId() > 0) {
                IconTreeItem iconTreeItem = new IconTreeItem();
                iconTreeItem.icon = R.drawable.watching_video;
                iconTreeItem.text = catalogModel.getParentId()+"-"+catalogModel.getIndex()+". " + catalogModel.getName();
                iconTreeItem.url = catalogModel.getUrl();
                iconTreeItem.duration = APIUtil.formatDuration(catalogModel.getDuration());

                final CatalogViewHolder catalogViewHolder2 = new CatalogViewHolder(getActivity());
                TreeNode child = new TreeNode(iconTreeItem).setViewHolder(catalogViewHolder2);
                //加入到列表
                childes.put(catalogModel.getId(), child);

                //点击事件
                child.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                    //前一个恢复颜色
                    TreeNode child = childes.get(currentPlayerIndex);
                    if (currentPlayerIndex > 0 && null != child){
                        ((CatalogViewHolder)child.getViewHolder()).setColor(false);
                    }
                    //新的设置颜色
                    currentPlayerIndex = catalogModel.getId();
                    ((CatalogViewHolder)node.getViewHolder()).setColor(true);
                    //切换视频
                    ((CourseActivity)CourseCatalogFragment.this.getActivity()).changeVideo(catalogModel.getUrl());
                    }
                });

                //添加节
                TreeNode parent = parents.get(catalogModel.getParentId());
                if (parent == null) {
                    IconTreeItem parentItem = new IconTreeItem();
                    CatalogViewHolder catalogViewHolder1 = new CatalogViewHolder(getActivity());
                    parent = new TreeNode(parentItem).setViewHolder(catalogViewHolder1);
                }

                parent.addChildren(child);
            }
        }

        Integer[] parentsKeys = parents.keySet().toArray(new Integer[childes.size()]);
        //添加小节小测
        for (int i = 0; i < parentsKeys.length; i++) {
            if(parents.get(parentsKeys[i]) != null) {
                IconTreeItem iconTreeItem = new IconTreeItem();
                iconTreeItem.icon = R.drawable.question_icon;
                iconTreeItem.text = "小测";
                iconTreeItem.url = "";
                iconTreeItem.duration = "";

                final CatalogViewHolder catalogViewHolder2 = new CatalogViewHolder(getActivity());
                TreeNode child = new TreeNode(iconTreeItem).setViewHolder(catalogViewHolder2);

                final int catalogId = parentsKeys[i];
                //点击事件
                child.setClickListener(new TreeNode.TreeNodeClickListener() {
                    @Override
                    public void onClick(TreeNode node, Object value) {
                        Intent intent = new Intent(CourseCatalogFragment.this.getActivity(), QuestionActivity.class);
                        intent.putExtra("catalogId", catalogId);
                        CourseCatalogFragment.this.getActivity().startActivity(intent);
                    }
                });

                parents.get(parentsKeys[i]).addChild(child);
            }
        }

        AndroidTreeView tView = new AndroidTreeView(getActivity(), root);
        tView.setDefaultAnimation(true);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        mContainer.addView(tView.getView());

        //设置第一个视频源
        if (childes.size() > 0) {
            //获取第一章视频源
            Integer[] keys = childes.keySet().toArray(new Integer[childes.size()]);
            Integer minIndex = keys[0];
            for (Integer key: keys) {
                if (key < minIndex) {
                    minIndex = key;
                }
            }

            currentPlayerIndex = minIndex;
            ((CatalogViewHolder)childes.get(minIndex).getViewHolder()).setColor(true);
            //切换视频
            String url = ((CatalogViewHolder)childes.get(minIndex).getViewHolder()).getIconTreeItem().url;
            ((CourseActivity)CourseCatalogFragment.this.getActivity()).changeVideo(url);
        }
    }
}
