package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.data.cache.FileManager;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.layout.SplashIndicator;
import cn.canlnac.onlinecourse.presentation.ui.view.SplashVideoView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * 应用启动页.
 */

public class SplashActivity extends Activity {
    //视频视图
    @BindView(R.id.splash_video_view) SplashVideoView mVideoView;
    //滑动页视图
    @BindView(R.id.splash_image) ViewPager mVpImage;
    //指示器视图
    @BindView(R.id.indicator) SplashIndicator mIndicator;

    //滑动页的图片视图
    List<View> mViewList = new ArrayList<>();

    //滑动页的图片资源ID
    int[] mImageResIds = new int[]{R.drawable.intro_text_1,R.drawable.intro_text_2,R.drawable.intro_text_3};
    //滑动页适配器
    CustomPagerAdapter mAdapter;

    //当前页
    int mCurrentPage = 0;
    //循环
    Subscription mLoop;

    final FileManager fileManager = new FileManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加偏好设置，记录是否第一次启动欢迎页
        Long isNotFirst = fileManager.getFromPreferences(this,"Splash","isNotFirst");

        //直接进入主页
        if (isNotFirst == 1) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        setContentView(R.layout.splash);    //设置布局：视频视图，滑动页，指示器视图

        //绑定视图
        ButterKnife.bind(this);

        //设置视频视图的URI
        mVideoView.setVideoURI(Uri.parse(getVideoPath()));

        //根据图片资源，创建图片视图
        for (int i = 0; i < mImageResIds.length; i++){
            //图片视图的布局
            View view = LayoutInflater.from(this).inflate(R.layout.splash_intro, null, false);
            //根据布局获取其中的图片视图，并设置图片资源
            ((ImageView) view.findViewById(R.id.iv_intro_text)).setImageResource(mImageResIds[i]);

            //图片视图添加点击事件，进入主界面
            if (i == mImageResIds.length - 1) {
                view.findViewById(R.id.iv_intro_text).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //偏好设置，保存记录
                        fileManager.writeToPreferences(SplashActivity.this,"Splash","isNotFirst",1);

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        SplashActivity.this.startActivity(intent);
                        SplashActivity.this.finish();
                    }
                });
            }
            //添加到列表中
            mViewList.add(view);
        }

        //创建滑动页的适配器
        mAdapter = new CustomPagerAdapter(mViewList);
        //设置滑动页的适配器
        mVpImage.setAdapter(mAdapter);
        //添加滑动页面事件
        mVpImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * 页面选中
             * @param position  选中页位置
             */
            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;                //设置当前页的位置
                mIndicator.setSelected(mCurrentPage);   //设置指示器
                startLoop();                            //播放对应的视频
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //在activity加载后，播放第一页视频
        startLoop();
    }

    /**
     * 获取视频位置
     * @return  视频位置
     */
    private String getVideoPath() {
        return "android.resource://" + this.getPackageName() + "/" + R.raw.splash;
    }

    /**
     * 开始轮询
     */
    private void startLoop() {
        //取消上一个订阅事件
        if (null != mLoop) {
            mLoop.unsubscribe();
        }
        //重新创建事件，并订阅
        mLoop = Observable.interval(0, 6 * 1000, TimeUnit.MILLISECONDS).subscribe(new Action1<Long> () {
            @Override
            public void call(Long aLong) {
                //跳到对应页的视频位置
                mVideoView.seekTo(mCurrentPage * 6 * 1000);
                if (!mVideoView.isPlaying()) {
                    mVideoView.start(); //播放视频
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (null != mLoop) {
            mLoop.unsubscribe();
        }
        if (null != mVideoView) {
            mVideoView.stopPlayback();
        }

        super.onDestroy();
    }

    public static class CustomPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public CustomPagerAdapter(List<View> viewList) {
            this.mViewList = viewList;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));
            return mViewList.get(position);
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
