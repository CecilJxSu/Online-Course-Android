package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.AndroidApplication;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.internal.di.components.DaggerGetMessagesComponent;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.ActivityModule;
import cn.canlnac.onlinecourse.presentation.internal.di.modules.GetMessagesModule;
import cn.canlnac.onlinecourse.presentation.model.MessageListModel;
import cn.canlnac.onlinecourse.presentation.presenter.GetMessagesPresenter;
import cn.canlnac.onlinecourse.presentation.ui.adapter.PagerAdapter;
import cn.canlnac.onlinecourse.presentation.ui.fragment.TabFragment3;
import cn.canlnac.onlinecourse.presentation.util.DensityUtil;

/**
 * 应用主屏幕.
 */

public class MainActivity extends AppCompatActivity {
    //工具栏
    @BindView(R.id.toolbar) Toolbar mToolbar;
    //tabs面板
    @BindView(R.id.tab_layout) TabLayout mTabLayout;
    //换页视图
    @BindView(R.id.pager) ViewPager mViewPager;

    private MenuItem messageItem;

    @Inject
    GetMessagesPresenter getMessagesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //绑定视图
        ButterKnife.bind(this);

        //设置工具栏
        setSupportActionBar(mToolbar);

        //设置选项面板
        mTabLayout.addTab(mTabLayout.newTab().setText("课程"));
        mTabLayout.addTab(mTabLayout.newTab().setText("话题"));
        mTabLayout.addTab(mTabLayout.newTab().setText("个人"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //创建适配器
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), mTabLayout.getTabCount());
        //设置适配器
        mViewPager.setAdapter(adapter);
        //缓存3个页面，如果不设置，第一页会重建
        mViewPager.setOffscreenPageLimit(3);
        //添加换页事件
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        //设置选项卡选择事件
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 2) {
                    ((TabFragment3)(adapter.getItem(2))).initialize();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initialize();
    }

    /**
     * 创建菜单栏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        messageItem = menu.findItem(R.id.menu_message);

        return true;
    }

    /**
     * 设置消息数目
     * @param menuItem  消息菜单
     * @param text      数目，字符串
     */
    public void setMessageNum(MenuItem menuItem, String text) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_message_white_24dp).copy(Bitmap.Config.ARGB_8888,true);

        //没有数目
        if (text == null || text.isEmpty()) {
            menuItem.setIcon(new BitmapDrawable(getResources(),bitmap));
            return;
        }

        //一位数
        int strokeWidth = 2;
        int textSize = 11;

        //超过2位数
        if (text.length() >= 2) {
            textSize = 10;
        }
        //超过3位数
        if (text.length() >= 3) {
            text = "...";
        }

        Paint textPaint = new Paint();
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setStrokeWidth(strokeWidth);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(DensityUtil.sp2px(getBaseContext(), textSize));

        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);

        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#FF0000"));

        Canvas canvas = new Canvas(bitmap);

        int radius = DensityUtil.dip2px(getBaseContext(), 7);
        canvas.drawCircle(bitmap.getWidth() - radius, radius, radius, circlePaint);
        canvas.drawText(text, bitmap.getWidth() - radius, radius + bounds.height()/2, textPaint);

        menuItem.setIcon(new BitmapDrawable(getResources(), bitmap));
    }

    /**
     * 菜单选项选中
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_message:
                Intent intent = new Intent(this,MessageActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 显示消息
     * @param message   消息
     */
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void initialize(){
        DaggerGetMessagesComponent.builder()
                .applicationComponent(((AndroidApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .getMessagesModule(new GetMessagesModule(0, 1, false))
                .build().inject(this);

        getMessagesPresenter.setView(this);
        getMessagesPresenter.initialize();
    }

    public void showMessages(MessageListModel messageListModel) {
        if (messageItem != null && messageListModel.getTotal() > 0) {
            setMessageNum(messageItem,messageListModel.getTotal()+"");
        }
    }
}