package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.adapter.PagerAdapter;

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
        //添加换页事件
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        //设置选项卡选择事件
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 创建菜单栏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
                return true;
            case R.id.menu_search:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}