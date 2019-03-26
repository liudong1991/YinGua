package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.MainActivity;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ThirdPartyLoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.third_party_login_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("登录");
        setBack();
        setHeaderTopMargin();
    }

    @OnClick({R.id.login_btn})
    void handle(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                startActivity(MainActivity.class);
                break;
        }
    }


}
