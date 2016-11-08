package cn.canlnac.onlinecourse.presentation.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.canlnac.onlinecourse.presentation.R;

/**
 * 页面浏览.
 */

public class WebViewActivity extends Activity {
    @BindView(R.id.webview)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        ButterKnife.bind(this);

        //获取意图和数据
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //开启javascript
        webView.getSettings().setJavaScriptEnabled(true);
        //加载url
        webView.loadUrl(url);
    }
}
