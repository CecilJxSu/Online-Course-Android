package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.activity.PDFViewActivity;

/**
 * 课程文档.
 */

public class DocumentFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.documents, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.open_pdf_view)
    public void onClickBtn(View v) {
        Intent intent = new Intent(getContext(), PDFViewActivity.class);
        intent.putExtra("pdfFile","sample.pdf");
        startActivity(intent);
    }
}
