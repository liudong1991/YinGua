package club.wustfly.inggua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.MainActivity;
import club.wustfly.inggua.R;
import club.wustfly.inggua.model.bean.OrderItem;
import club.wustfly.inggua.ui.base.BaseActivity;

public class PaySuccessActivity extends BaseActivity {

    @BindView(R.id.order_detail)
    LinearLayout order_detail;
    @BindView(R.id.wrapper_fee)
    TextView wrapper_fee;
    @BindView(R.id.total_fee_txt)
    TextView total_fee_txt;

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private String[] values = {"30张", "A4", "单面", "黑白", "黑白", "1份", "不装订"};

    OrderItem orderItem;


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

        Intent intent = getIntent();
        String order = intent.getStringExtra("order");
        orderItem = new Gson().fromJson(order, OrderItem.class);

        values = new String[]{orderItem.getPage() + "张", orderItem.getSize(), orderItem.getIssingle(), orderItem.getColor(), orderItem.getLayout(), orderItem.getNumber() + "份", orderItem.getBinding()};

        order_detail.removeAllViews();
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pay_order_detail_item_layout, null);
            TextView labelTxt = view.findViewById(R.id.label);
            TextView valueTxt = view.findViewById(R.id.value);
            labelTxt.setText(labels[i]);
            valueTxt.setText(values[i]);
            order_detail.addView(view);
        }

        wrapper_fee.setText("￥" + orderItem.getPackfree());
        total_fee_txt.setText("￥" + orderItem.getMoney());

    }

    @OnClick({R.id.check_order_btn, R.id.go_back_mainpage_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.check_order_btn:
                Intent intent = new Intent(this, OrderDetailActivity.class);
                intent.putExtra("oid", orderItem.getId());
                startActivity(intent);
                finish();
                break;
            case R.id.go_back_mainpage_btn:
                startActivity(MainActivity.class);
                break;
        }
    }
}
