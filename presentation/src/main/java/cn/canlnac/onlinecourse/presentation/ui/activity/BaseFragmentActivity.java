package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.internal.di.components.ApplicationComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;

public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * 显示消息
     * @param message   消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 未登陆用户需要登陆
     */
    public void toLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
