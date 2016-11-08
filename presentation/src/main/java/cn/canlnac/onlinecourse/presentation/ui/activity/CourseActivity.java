package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 课程详情.
 */

public class CourseActivity extends Activity {
    @BindView(R.id.course_details_text)
    TextView textView;

    @BindView(R.id.course_details_close)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_details);

        ButterKnife.bind(this);

        //获取意图和数据
        Intent intent = getIntent();
        int courseId = intent.getIntExtra("courseId", -1);  //课程ID
        textView.setText("course: " + courseId);

        //点击按钮关闭
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseActivity.this.finish();
            }
        });
    }
}
