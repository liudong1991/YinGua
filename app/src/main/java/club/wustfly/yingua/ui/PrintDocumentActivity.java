package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class PrintDocumentActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_document_activity_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        setTitle("文档打印");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

    }

    @OnClick({R.id.word_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.word_btn:
                startActivity(MyDocmentActivity.class);
                break;
        }
    }


}
