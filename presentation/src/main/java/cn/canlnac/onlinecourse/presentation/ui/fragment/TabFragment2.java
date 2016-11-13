package cn.canlnac.onlinecourse.presentation.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;
import cn.canlnac.onlinecourse.presentation.ui.activity.PostChatActivity;

/**
 * 话题.
 */

public class TabFragment2 extends Fragment {
    @BindView(R.id.menu_red)
    FloatingActionMenu menu;

    @BindView(R.id.create_chat)
    FloatingActionButton createChatButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //获取布局
        View view = inflater.inflate(R.layout.tab_fragment_2, container, false);

        //绑定视图
        ButterKnife.bind(this, view);

        createChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TabFragment2.this.getActivity(), PostChatActivity.class);
                TabFragment2.this.startActivity(intent);
                menu.toggle(true);
            }
        });

        return view;
    }
}
