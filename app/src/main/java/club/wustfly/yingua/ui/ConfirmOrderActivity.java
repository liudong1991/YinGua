package club.wustfly.yingua.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.ui.base.BaseActivity;
import club.wustfly.yingua.ui.views.SelectSendTimeDialog;

public class ConfirmOrderActivity extends BaseActivity {

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};
    private static String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};

    @BindView(R.id.order_content)
    LinearLayout order_content;
    @BindView(R.id.divider_bar)
    ImageView divider_bar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_activity_layout);

        ButterKnife.bind(this);

        init();

    }

    private void init() {

        setTitle("确认订单");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        divider_bar.post(new Runnable() {
            @Override
            public void run() {
                int width = divider_bar.getWidth();
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), R.mipmap.confirm_order_divider, options);
                int height = (int) (width * 1.0 / options.outWidth * options.outHeight);

                ViewGroup.LayoutParams lp = divider_bar.getLayoutParams();
                lp.width = width;
                lp.height = height;
                divider_bar.setLayoutParams(lp);
            }
        });

        order_content.removeAllViews();
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.confirm_order_content_item_layout, null);
            TextView label = view.findViewById(R.id.label);
            TextView value = view.findViewById(R.id.value);
            label.setText(labels[i]);
            value.setText(values[i]);
            order_content.addView(view);
        }


    }

    @OnClick({R.id.select_send_time, R.id.submit_order})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.select_send_time:
                new SelectSendTimeDialog(this).show();
                break;
            case R.id.submit_order:
                startActivity(SelectPayChannelActivity.class);
                break;
        }
    }
}
