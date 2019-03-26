package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class PaySuccessActivity extends BaseActivity {

    @BindView(R.id.order_detail)
    LinearLayout order_detail;

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private static String[] values = {"30张", "A4", "单面", "黑白", "黑白", "1份", "不装订"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_success_activity_layout);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        setTitle("支付成功");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();
        order_detail.removeAllViews();
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pay_order_detail_item_layout, null);
            TextView labelTxt = view.findViewById(R.id.label);
            TextView valueTxt = view.findViewById(R.id.value);
            labelTxt.setText(labels[i]);
            valueTxt.setText(values[i]);
            order_detail.addView(view);
        }

    }

    @OnClick({R.id.check_order_btn, R.id.go_back_mainpage_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.check_order_btn:
                break;
            case R.id.go_back_mainpage_btn:
                break;
        }
    }
}
