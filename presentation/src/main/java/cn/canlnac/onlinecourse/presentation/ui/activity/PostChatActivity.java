package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerCreateChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUploadInChatComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.CreateChatModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UploadModule;
import cn.canlnac.onlinecourse.presentation.model.UploadModel;
import cn.canlnac.onlinecourse.presentation.presenter.CreateChatPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UploadInChatPresenter;
import cn.canlnac.onlinecourse.presentation.ui.view.RichTextEditor;
import cn.canlnac.onlinecourse.presentation.ui.view.RichTextEditor.EditData;
import id.zelory.compressor.Compressor;

/**
 * 发表话题.
 */
public class PostChatActivity extends BaseActivity {
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    private static final int REQUEST_CODE_CAPTURE_CAMERA = 1022;

    @BindView(R.id.post_chat_send_button)
    Button sendButton;
    @BindView(R.id.post_chat_close_button)
    Button closeButton;
    @BindView(R.id.post_chat_image_button)
    Button imageButton;
    @BindView(R.id.post_chat_photo_button)
    Button photoButton;
    @BindView(R.id.richEditor)
    RichTextEditor editor;
    @BindView(R.id.post_chat_topic) EditText chatTitle;

    @Inject
    UploadInChatPresenter uploadInChatPresenter;
    @Inject
    CreateChatPresenter createChatPresenter;

    private Map<String,Object> postChatData;

    private static final File PHOTO_DIR = new File(
            Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    private File mCurrentPhotoFile;// 照相机拍照得到的图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_chat);

        ButterKnife.bind(this);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostChatActivity.this.finish();
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开系统相册
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
            }
        });

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开相机
                openCamera();
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<EditData> editList = editor.buildEditData();
                // 下面的代码可以上传、或者保存
                dealEditData(editList);
            }
        });
    }

    /**
     * 负责处理编辑数据提交
     */
    protected void dealEditData(List<EditData> editList) {
        if (chatTitle.getText() == null ||
            chatTitle.getText().toString().isEmpty() ||
            editList == null || editList.size() <= 0
        ) {
            showToastMessage("未填写完成");
            return;
        }

        postChatData = new HashMap<>();
        postChatData.put("title", chatTitle.getText().toString());

        StringBuilder html = new StringBuilder();
        StringBuilder content = new StringBuilder();
        List<String> pictureUrls = new ArrayList<>();

        for (EditData itemData : editList) {
            if (itemData.inputStr != null && !itemData.toString().trim().isEmpty()) {
                content.append(itemData.inputStr);

                html.append("<p>");
                itemData.inputStr = itemData.inputStr.trim().replace("\r\n","</p><p>");
                html.append(itemData.inputStr.replace("\n","</p><p>"));
                html.append("</p>");
            } else if (itemData.imagePath != null) {
                html.append("<img src = \"");
                html.append(itemData.imagePath);
                html.append("\" />");
                pictureUrls.add(itemData.imagePath);
            }
        }
        //没有内容
        if (html.toString().isEmpty()) {
            showToastMessage("未填写完成");
            return;
        }

        postChatData.put("html", html.toString());
        postChatData.put("content", content.toString());
        postChatData.put("pictureUrls", pictureUrls);

        uploadPic(pictureUrls);
    }

    /**
     * 上传图片
     * @param pictureUrls
     */
    public void uploadPic(List<String> pictureUrls) {
        if (pictureUrls.size() > 0) {
            List<File> files = new ArrayList<>();
            File file;
            for (String path: pictureUrls) {
                file = new File(path);
                File compress = Compressor.getDefault(this).compressToFile(file);
                //图片不存在
                if (!file.exists() || file.isDirectory()) {
                    showToastMessage("图片不存在");
                    return;
                }
                files.add(compress);
            }
            //上传图片
            DaggerUploadInChatComponent.builder()
                    .applicationComponent(getApplicationComponent())
                    .activityModule(getActivityModule())
                    .uploadModule(new UploadModule(files))
                    .build().inject(this);

            uploadInChatPresenter.setView(this);
            uploadInChatPresenter.initialize();
        } else {
            postChat(new ArrayList<UploadModel>());
        }
    }

    /**
     * 发表话题
     * @param uploadModels
     */
    public void postChat(List<UploadModel> uploadModels) {
        //替换url
        String html = postChatData.get("html").toString();
        List<String> pictureUrls = (List<String>)postChatData.get("pictureUrls");
        for (int i = 0; i < uploadModels.size(); i++) {
            html = html.replace(pictureUrls.get(i), RestApiConnection.API_FILE + "/" + uploadModels.get(i).getFileUrl());
            pictureUrls.set(i, uploadModels.get(i).getFileUrl());
        }
        //update post
        postChatData.put("html", html);
        postChatData.put("pictureUrls", pictureUrls);

        //发表话题
        DaggerCreateChatComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .createChatModule(new CreateChatModule(postChatData))
                .build().inject(this);

        createChatPresenter.setView(this);
        createChatPresenter.initialize();
    }

    @OnClick(R.id.post_chat_bottom)
    public void onClickBottom(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    protected void openCamera() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMERA);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date) + ".jpg";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            insertBitmap(getRealFilePath(uri));
        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMERA) {
            insertBitmap(mCurrentPhotoFile.getAbsolutePath());
        }
    }

    /**
     * 添加图片到富文本剪辑器
     *
     * @param imagePath
     */
    private void insertBitmap(String imagePath) {
        editor.insertImage(imagePath);
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public String getRealFilePath(final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
