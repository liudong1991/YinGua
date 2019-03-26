package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class SelectPayChannelActivity extends BaseActivity {

    @BindView(R.id.order_detail)
    LinearLayout order_detail;
    @BindView(R.id.weixin_select)
    ImageView weixin_select;
    @BindView(R.id.zfb_select)
    ImageView zfb_select;

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private static String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pay_channel_activity_layout);
        ButterKnife.bind(this);


        init();

    }

    private void init() {

        setTitle("选择支付方式");
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

    @OnClick({R.id.weixin_pay, R.id.zfb_pay, R.id.next_handle_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.weixin_pay:
                weixin_select.setImageResource(R.mipmap.pay_channel_selected);
                zfb_select.setImageResource(R.mipmap.pay_channel_unselected);
                break;
            case R.id.zfb_pay:
                weixin_select.setImageResource(R.mipmap.pay_channel_unselected);
                zfb_select.setImageResource(R.mipmap.pay_channel_selected);
                break;
            case R.id.next_handle_btn:
                startActivity(PaySuccessActivity.class);
                break;
        }
    }
}