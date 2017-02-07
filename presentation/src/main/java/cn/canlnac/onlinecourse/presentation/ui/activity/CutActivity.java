package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.adamstyrc.cookiecutter.CookieCutterImageView;
import com.adamstyrc.cookiecutter.ImageUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUploadInCutComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UploadModule;
import cn.canlnac.onlinecourse.presentation.model.UploadModel;
import cn.canlnac.onlinecourse.presentation.presenter.UploadInCutPresenter;

/**
 * 裁剪图片.
 */

public class CutActivity extends BaseActivity {
    public final static int SUCCESS = 200;

    @BindView(R.id.ivCrop) CookieCutterImageView cutterImageView;

    private Uri iconUrl;

    @Inject
    UploadInCutPresenter uploadInCutPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cutting);
        //绑定视图
        ButterKnife.bind(this);

        iconUrl = getIntent().getData();

        Point screenSize = ImageUtils.getScreenSize(this);
        Bitmap scaledBitmap = null;
        try {
            scaledBitmap = ImageUtils.decodeUriToScaledBitmap(this, iconUrl, screenSize.x, screenSize.y);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToastMessage("文件不存在");
            finish();
        }
        cutterImageView.setImageBitmap(scaledBitmap);
        cutterImageView.init();
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        finish();
    }

    @OnClick(R.id.submit)
    public void onClickSubmit(View v) {
        File file = new File(getCacheDir() + "/avatar");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                showToastMessage("创建文件失败");
                finish();
            }
        }

        try {
            FileOutputStream fout = new FileOutputStream(file);
            Bitmap bitmap = cutterImageView.getCroppedBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, fout);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            showToastMessage("存储文件失败");
            finish();
        }

        //上传图片
        uploadPic(file);
    }

    /**
     * 上传图片
     * @param file
     */
    public void uploadPic(File file) {
        List<File> files = new ArrayList<>();
        files.add(file);
        //上传图片
        DaggerUploadInCutComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .uploadModule(new UploadModule(files))
                .build().inject(this);

        uploadInCutPresenter.setView(this);
        uploadInCutPresenter.initialize();
    }

    public void uploadReturn(List<UploadModel> uploadModels) {
        Intent intent = new Intent();
        if (uploadModels!=null && uploadModels.size() > 0) {
            intent.putExtra("avatar", uploadModels.get(0).getFileUrl());
        }

        setResult(SUCCESS, intent);
        finish();
    }
}
