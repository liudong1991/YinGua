package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ForgetPasswordActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forget_password_layout);

        init();
    }

    private void init() {
        setTitle("忘记密码");
        setBack();
        setHeaderTopMargin();
    }
}
