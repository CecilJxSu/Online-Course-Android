package cn.canlnac.onlinecourse.presentation.ui.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.model.DocumentModel;

/**
 * 文档 view holder.
 */

public class DocumentViewHolder {

    @BindView(R.id.document_name) TextView name;
    @BindView(R.id.document_size) TextView size;
    @BindView(R.id.document_type) ImageView type;

    private Activity activity;
    private PrettyTime prettyTime;


    public DocumentViewHolder(Activity activity, View view, DocumentModel document, PrettyTime prettyTime) {
        ButterKnife.bind(this, view);

        this.activity = activity;
        this.prettyTime = prettyTime;

        name.setText(document.getName());

        String sizeStr;
        if (document.getSize() < 1024) {
            sizeStr = document.getSize() + "";
        } else if (document.getSize() < 1024 * 1024) {
            sizeStr = document.getSize() / 1024 + "K";
        } else if (document.getSize() >= 1024 * 1024) {
          sizeStr = document.getSize() / (1024 * 1024) + "M";
        } else {
            sizeStr = "";
        }
        size.setText(sizeStr);

        switch (document.getType()) {
            case "application/pdf":
                type.setImageResource(R.drawable.pdf);
                break;
            case "application/msword":
                type.setImageResource(R.drawable.doc);
                break;
            case "application/vnd.ms-excel":
                type.setImageResource(R.drawable.xls);
                break;
            case "application/vnd.ms-powerpoint":
                type.setImageResource(R.drawable.ppt);
                break;
            case "text/plain":
                type.setImageResource(R.drawable.txt);
                break;
            default:
                type.setImageResource(R.drawable.txt);
        }
    }
}
