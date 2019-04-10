package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.inggua.R;
import club.wustfly.inggua.ui.base.BaseActivity;

public class ServiceAgreementActivity extends BaseActivity {

    @BindView(R.id.content)
    WebView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_agreement_activity_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("服务协议");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        WebSettings settings = content.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        content.loadUrl("file:////android_asset/html/Protocol.html");

    }
}
