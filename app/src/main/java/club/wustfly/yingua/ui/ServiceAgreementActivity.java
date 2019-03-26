package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ServiceAgreementActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_agreement_activity_layout);

        init();
    }

    private void init() {
        setTitle("服务协议");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();
    }
}
