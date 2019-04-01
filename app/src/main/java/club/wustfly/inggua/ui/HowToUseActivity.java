package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import club.wustfly.inggua.R;
import club.wustfly.inggua.ui.base.BaseActivity;

public class HowToUseActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_use_activity_layout);

        init();
    }

    private void init() {
        setTitle("如何使用");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();
    }
}
