package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.canlnac.onlinecourse.data.net.RestApiConnection;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.HasComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetUserProfileInSettingComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerUpdateUserProfileComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.GetUserProfileInSettingComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.components.UpdateUserProfileComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetUserProfileModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.UpdateUserProfileModule;
import cn.canlnac.onlinecourse.presentation.model.ProfileModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetUserProfileInSettingPresenter;
import cn.canlnac.onlinecourse.presentation.presenter.UpdateUserProfilePresenter;

public class ProfileActivity extends BaseActivity implements HasComponent<GetUserProfileInSettingComponent> {
    private static final int REQUEST_CODE_PICK_IMAGE = 1023;
    public static final int UPDATE_AVATAR = 201;

    @BindView(R.id.profile_nickname) EditText nickname;
    @BindView(R.id.profile_iconUrl) SimpleDraweeView iconUrl;
    @BindView(R.id.profile_gender_male) RadioButton male;
    @BindView(R.id.profile_gender_female) RadioButton female;
    @BindView(R.id.profile_universityId) EditText universityId;
    @BindView(R.id.profile_realname) EditText realname;
    @BindView(R.id.profile_email) EditText email;
    @BindView(R.id.profile_phone) EditText phone;
    @BindView(R.id.profile_department) EditText department;
    @BindView(R.id.profile_major) EditText major;
    @BindView(R.id.profile_dormitoryAddress) EditText dormitoryAddress;

    private int userId;

    private String avatar;

    @Inject
    GetUserProfileInSettingPresenter getUserProfileInSettingPresenter;

    @Inject
    UpdateUserProfilePresenter updateUserProfilePresenter;

    private UpdateUserProfileComponent updateUserProfileComponent;
    private GetUserProfileInSettingComponent getUserProfileInSettingComponent;

    @Override
    public GetUserProfileInSettingComponent getComponent() {
        return getUserProfileInSettingComponent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //绑定视图
        ButterKnife.bind(this);

        userId = getIntent().getIntExtra("userId", -1);
        if (userId <= 0) {
            toLogin();
            this.finish();
        }

        initialize();
    }

    public void initialize(){
        getUserProfileInSettingComponent = DaggerGetUserProfileInSettingComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .getUserProfileModule(new GetUserProfileModule(userId))
                .build();
        getComponent(GetUserProfileInSettingComponent.class).inject(this);

        getUserProfileInSettingPresenter.setView(this);
        getUserProfileInSettingPresenter.initialize();
    }

    @OnClick(R.id.close)
    public void onClickClose(View v) {
        Intent intent = new Intent();
        if (avatar!=null) {
            intent.putExtra("avatar", RestApiConnection.API_BASE_URL + "file/" + avatar);
        }
        setResult(UPDATE_AVATAR, intent);
        finish();
    }

    public void showProfile(ProfileModel profileModel) {
        nickname.setText(profileModel.getNickname());
        universityId.setText(profileModel.getUniversityId());
        realname.setText(profileModel.getRealname());
        email.setText(profileModel.getEmail());
        phone.setText(profileModel.getPhone());
        department.setText(profileModel.getDepartment());
        major.setText(profileModel.getMajor());
        dormitoryAddress.setText(profileModel.getDormitoryAddress());
        iconUrl.setImageURI(profileModel.getIconUrl());
        if (profileModel.getGender() != null && profileModel.getGender().equals("female")) {
            female.setChecked(true);
        } else {
            male.setChecked(true);
        }
    }

    @OnClick(R.id.submit)
    public void onClickSubmit(View v) {
        String sNickname = nickname.getText().toString().trim();
        String sUniversityId = universityId.getText().toString().trim();
        String sRealname = realname.getText().toString().trim();
        String sEmail = email.getText().toString().trim();
        String sPhone = phone.getText().toString().trim();
        String sDepartment = department.getText().toString().trim();
        String sMajor = major.getText().toString().trim();
        String sDormitoryAddress = dormitoryAddress.getText().toString().trim();
        String sGender = female.isChecked()?"female":"male";

        Map<String,String> profiles = new HashMap<>();
        profiles.put("nickname",sNickname);
        profiles.put("universityId",sUniversityId);
        profiles.put("realname",sRealname);
        profiles.put("email",sEmail);
        profiles.put("phone",sPhone);
        profiles.put("department",sDepartment);
        profiles.put("major",sMajor);
        profiles.put("dormitoryAddress",sDormitoryAddress);
        profiles.put("gender",sGender);
        if (avatar!=null) {
            profiles.put("iconUrl",avatar);
        }

        updateUserProfileComponent = DaggerUpdateUserProfileComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .updateUserProfileModule(new UpdateUserProfileModule(profiles))
                .build();
        updateUserProfileComponent.inject(this);

        updateUserProfilePresenter.setView(this);
        updateUserProfilePresenter.initialize();
    }

    @OnClick(R.id.profile_iconUrl)
    public void onClickIcon(View v) {
        //打开系统相册
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");// 相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && data != null && data.getData() != null) {
            Uri uri = data.getData();

            Intent intent = new Intent(getBaseContext(), CutActivity.class);
            intent.setData(uri);
            startActivityForResult(intent,CutActivity.SUCCESS);
        } else if (resultCode == CutActivity.SUCCESS) {
            avatar = data.getStringExtra("avatar");
            iconUrl.setImageURI(RestApiConnection.API_BASE_URL + "file/" + avatar);
        }
    }
}
