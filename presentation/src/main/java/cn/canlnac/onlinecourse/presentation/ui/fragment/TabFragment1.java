package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.activity.CourseActivity;
import cn.canlnac.onlinecourse.presentation.ui.activity.WebViewActivity;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseGallerryAdapter;

/**
 * 第一页，主要关于课程.
 */

public class TabFragment1 extends Fragment {
    //轮播视图
    @BindView(R.id.carouselView)
    CarouselView carouselView;

    //课程列表
    @BindView(R.id.course_gallery)
    GridView gridView;

    //轮播图片
    int[] carousalImages = {
            R.drawable.carousel_image_1,
            R.drawable.carousel_image_2,
            R.drawable.carousel_image_3,
            R.drawable.carousel_image_4,
            R.drawable.carousel_image_5
    };

    //课程列表，图片列表显示
    int[] listImages = {
            R.drawable.list_image_1,
            R.drawable.list_image_2,
            R.drawable.list_image_1,
            R.drawable.list_image_2
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        //设置轮播
        carouselView.setPageCount(carousalImages.length);

        //设置轮播的监听器
        carouselView.setImageListener(imageListener);

        //设置适配器
        gridView.setAdapter(new CourseGallerryAdapter(this.getContext(), listImages));

        //设置item点击事件
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //打开课程详情页
                Intent intent = new Intent(TabFragment1.this.getActivity(), CourseActivity.class);
                intent.putExtra("courseId", position);      //课程ID
                TabFragment1.this.startActivity(intent);
            }
        });

        //设置不滚动
        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return view;
    }

    //设置轮播图片监听器
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            //设置图片点击事件
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //打开网页视图
                    Intent intent = new Intent(TabFragment1.this.getActivity(), WebViewActivity.class);
                    intent.putExtra("url", "http://cn.bing.com");   //网页
                    TabFragment1.this.startActivity(intent);
                }
            });
            //设置轮播图片
            imageView.setImageResource(carousalImages[position]);
        }
    };
}
