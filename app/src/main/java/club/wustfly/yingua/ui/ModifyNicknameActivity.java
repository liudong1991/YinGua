package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ModifyNicknameActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_nickname_activity_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("修改昵称");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();
    }


}
