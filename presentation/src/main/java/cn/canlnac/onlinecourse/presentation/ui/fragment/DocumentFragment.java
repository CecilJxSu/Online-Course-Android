package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerDownloadInDocumentComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetDocumentsInCourseComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.DownloadModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetDocumentsInCourseModule;
import cn.canlnac.onlinecourse.presentation.model.DocumentListModel;
import cn.canlnac.onlinecourse.presentation.model.DocumentModel;
import cn.canlnac.onlinecourse.presentation.presenter.DownloadInDocumentPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.GetDocumentsInCoursePresenter;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.PDFViewActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.DocumentAdapter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleFooter;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.SimpleHeader;
import cn.canlnac.onlinecourse.presentation.ui.widget.ZrcListView.ZrcListView;

/**
 * 课程文档.
 */

public class DocumentFragment extends BaseFragment {

    @BindView(R.id.course_comments_list)
    ZrcListView zrcListView;

    @Inject
    GetDocumentsInCoursePresenter getDocumentsInCoursePresenter;
    @Inject
    DownloadInDocumentPresenter downloadInDocumentPresenter;

    int start = 0;
    int count = 10;
    int total = 10;
    String sort = "date";

    private DocumentAdapter adapter;
    private Handler handler;

    List<DocumentModel> documents = new ArrayList<>();

    //获取指定类型的文件
    String[] types = {
            "application/pdf",
            "application/msword",
            "application/vnd.ms-excel",
            "application/vnd.ms-powerpoint",
            "text/plain"
    };

    private boolean showingFile = false;

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

        zrcListView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                if (showingFile) {
                    showToastMessage("下载文件中");
                    return;
                }

                if (documents.get(position).getType().contains("pdf")) {
                    setShowingFile(true);

                    String[] hostPath = documents.get(position).getUrl().split("/");

                    File targetFile = new File(getContext().getCacheDir() + "/" + hostPath[hostPath.length-1]);
                    DaggerDownloadInDocumentComponent.builder()
                            .applicationComponent(getApplicationComponent())
                            .activityModule(getActivityModule())
                            .downloadModule(new DownloadModule(documents.get(position).getUrl(), targetFile))
                            .build().inject(DocumentFragment.this);

                    downloadInDocumentPresenter.setView(DocumentFragment.this, documents.get(position).getType());
                    downloadInDocumentPresenter.initialize();
                } else {
                    showToastMessage("文件类型暂不支持");
                }
            }
        });

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

        adapter = new DocumentAdapter(getActivity(), documents);
        zrcListView.setAdapter(adapter);
        zrcListView.refresh(); // 主动下拉刷新

        return view;
    }

    @Override
    public void onDestroy() {
        zrcListView.setOnLoadMoreStartListener(null);
        zrcListView.setOnRefreshStartListener(null);

        super.onDestroy();
    }

    public void showFile(String type, File file) {
        if (type.contains("pdf")) {
            Intent intent = new Intent(getContext(), PDFViewActivity.class);
            intent.putExtra("pdfFile", file);
            startActivity(intent);
        }
    }

    public void setShowingFile(boolean showing) {
        this.showingFile = showing;
    }

    /**
     * 刷新
     */
    private void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CourseActivity activity = (CourseActivity)getActivity();

                if (activity != null) {
                    if (activity.getCourseId() <= 0) {
                        zrcListView.setRefreshFail("课程不存在");
                        return;
                    }

                    if (start == 0) {
                        //获取文档
                        DaggerGetDocumentsInCourseComponent.builder()
                                .applicationComponent(getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getDocumentsInCourseModule(new GetDocumentsInCourseModule(activity.getCourseId(), start, count, sort))
                                .build().inject(DocumentFragment.this);

                        getDocumentsInCoursePresenter.setView(DocumentFragment.this, 0);
                        getDocumentsInCoursePresenter.initialize();

                    } else {
                        showRefreshError("加载完成");
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
                start += count;
                if(start < total){
                    //获取评论
                    CourseActivity activity = (CourseActivity)getActivity();
                    if (activity != null) {
                        DaggerGetDocumentsInCourseComponent.builder()
                                .applicationComponent(((AndroidApplication)getActivity().getApplication()).getApplicationComponent())
                                .activityModule(getActivityModule())
                                .getDocumentsInCourseModule(new GetDocumentsInCourseModule(activity.getCourseId(), start, count, sort))
                                .build().inject(DocumentFragment.this);

                        getDocumentsInCoursePresenter.setView(DocumentFragment.this, 1);
                        getDocumentsInCoursePresenter.initialize();
                    }
                }else{
                    zrcListView.stopLoadMore();
                }
            }
        }, 1000);
    }

    /**
     * 刷新显示文档
     * @param documentListModel
     */
    public void showRefresh(DocumentListModel documentListModel) {
        total = documentListModel.getTotal();

        List<DocumentModel> documents = new ArrayList<>();
        for (DocumentModel document:documentListModel.getDocuments()) {
            if (Arrays.asList(types).contains(document.getType())) {
                documents.add(document);
            }
        }
        this.documents.addAll(documents);
        adapter.notifyDataSetChanged();
        zrcListView.setRefreshSuccess("加载成功");  // 通知加载成功
        zrcListView.startLoadMore();                // 开启LoadingMore功能
    }

    /**
     * 更新显示文档
     * @param documentListModel
     */
    public void showLoadMore(DocumentListModel documentListModel) {
        total = documentListModel.getTotal();

        List<DocumentModel> documents = new ArrayList<>();
        for (DocumentModel document:documentListModel.getDocuments()) {
            if (Arrays.asList(types).contains(document.getType())) {
                documents.add(document);
            }
        }
        this.documents.addAll(documents);
        adapter.notifyDataSetChanged();
        zrcListView.setLoadMoreSuccess();
    }


    /**
     * 刷新失败
     */
    public void showRefreshError(String message) {
        zrcListView.setRefreshFail(message);
    }
}
