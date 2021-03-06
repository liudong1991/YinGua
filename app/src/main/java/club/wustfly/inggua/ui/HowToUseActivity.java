package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.inggua.R;
import club.wustfly.inggua.ui.base.BaseActivity;

public class HowToUseActivity extends BaseActivity {

    @BindView(R.id.content)
    WebView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use_activity_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("如何使用");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        content.loadUrl("file:////android_asset/html/HowToUse.html");


    }
}
