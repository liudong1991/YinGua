package club.wustfly.yingua.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;

public class OrderDetailActivity extends BaseActivity {

    private static final String STATUS_UNPAY = "status_unpay";
    private static final String STATUS_PAYED = "status_payed";
    private static final String STATUS_FINISHED = "status_finished";

    private String status = STATUS_PAYED;

    @BindView(R.id.label1)
    TextView label1;
    @BindView(R.id.label2)
    TextView label2;
    @BindView(R.id.label3)
    TextView label3;
    @BindView(R.id.logo1)
    ImageView logo1;
    @BindView(R.id.logo2)
    ImageView logo2;
    @BindView(R.id.logo3)
    ImageView logo3;
    @BindView(R.id.progress_1)
    View progress_1;
    @BindView(R.id.progress_2)
    View progress_2;
    @BindView(R.id.order_detail)
    LinearLayout order_detail;
    @BindView(R.id.sender_container)
    RelativeLayout sender_container;
    @BindView(R.id.send_deal_time_container)
    LinearLayout send_deal_time_container;
    @BindView(R.id.pay_btn)
    TextView pay_btn;
    @BindView(R.id.payed_status_container)
    LinearLayout payed_status_container;
    @BindView(R.id.delete_order_btn)
    TextView delete_order_btn;

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private static String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail_activity_layout);
        ButterKnife.bind(this);

        init();

    }

    private void init() {

        setTitle("订单详情");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        label1.post(new Runnable() {
            @Override
            public void run() {
                float logo1_x = logo1.getX();
                int logo1_width = logo1.getWidth();
                int label1_width = label1.getWidth();

                float label1_x = logo1_x + logo1_width / 2 - label1_width / 2;
                label1.setX(label1_x);
                label1.requestLayout();

                float logo3_x = logo3.getX();
                int logo3_width = logo3.getWidth();
                int label3_width = label3.getWidth();

                float label3_x = logo3_x + logo3_width / 2 - label3_width / 2;
                label3.setX(label3_x);
                label3.requestLayout();
            }
        });

        order_detail.removeAllViews();
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.pay_order_detail_item_layout, null);
            TextView labelTxt = view.findViewById(R.id.label);
            TextView valueTxt = view.findViewById(R.id.value);
            labelTxt.setText(labels[i]);
            valueTxt.setText(values[i]);
            order_detail.addView(view);
        }

        initStatus();

    }

    private void initStatus() {
        switch (status) {
            case STATUS_UNPAY:
                logo2.setImageResource(R.mipmap.order_detail_pay_logo_grey);
                logo3.setImageResource(R.mipmap.order_detail_deal_finish_logo_grey);
                progress_2.setBackgroundColor(Color.parseColor("#B3B3B3"));
                label2.setTextColor(Color.parseColor("#333333"));
                label3.setTextColor(Color.parseColor("#333333"));

                sender_container.setVisibility(View.GONE);

                send_deal_time_container.setVisibility(View.GONE);

                pay_btn.setVisibility(View.VISIBLE);
                payed_status_container.setVisibility(View.GONE);
                delete_order_btn.setVisibility(View.GONE);
                break;
            case STATUS_PAYED:
                logo2.setImageResource(R.mipmap.order_detail_pay_logo_yellow);
                logo3.setImageResource(R.mipmap.order_detail_deal_finish_logo_grey);
                progress_2.setBackgroundColor(Color.parseColor("#F7E731"));
                label2.setTextColor(Color.parseColor("#FD7C13"));
                label3.setTextColor(Color.parseColor("#333333"));

                sender_container.setVisibility(View.VISIBLE);

                send_deal_time_container.setVisibility(View.VISIBLE);

                pay_btn.setVisibility(View.GONE);
                payed_status_container.setVisibility(View.VISIBLE);
                delete_order_btn.setVisibility(View.GONE);
                break;
            case STATUS_FINISHED:
                logo2.setImageResource(R.mipmap.order_detail_pay_logo_yellow);
                logo3.setImageResource(R.mipmap.order_detail_deal_finish_logo_yellow);
                progress_2.setBackgroundColor(Color.parseColor("#F7E731"));
                label2.setTextColor(Color.parseColor("#FD7C13"));
                label3.setTextColor(Color.parseColor("#FD7C13"));

                sender_container.setVisibility(View.VISIBLE);

                send_deal_time_container.setVisibility(View.VISIBLE);

                pay_btn.setVisibility(View.GONE);
                payed_status_container.setVisibility(View.GONE);
                delete_order_btn.setVisibility(View.VISIBLE);
                break;
        }
    }
}
