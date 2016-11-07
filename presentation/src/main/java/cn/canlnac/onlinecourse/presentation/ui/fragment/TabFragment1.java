package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.adapter.CourseGallerryAdapter;

/**
 * Created by cecil on 2016/10/31.
 */

public class TabFragment1 extends Fragment {
    //轮播视图
    @BindView(R.id.carouselView)
    CarouselView carouselView;

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
        ButterKnife.bind(this,view);

        //设置轮播
        carouselView.setPageCount(carousalImages.length);

        //设置轮播的监听器
        carouselView.setImageListener(imageListener);

        gridView.setAdapter(new CourseGallerryAdapter(this.getContext(), listImages));

        gridView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        return view;
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(carousalImages[position]);
        }
    };
}
