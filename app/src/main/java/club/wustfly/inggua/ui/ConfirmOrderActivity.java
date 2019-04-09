package club.wustfly.inggua.ui;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.bean.Address;
import club.wustfly.inggua.model.bean.GoodItem;
import club.wustfly.inggua.model.req.SubmitOrderParam;
import club.wustfly.inggua.model.resp.SubmitOrderRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;
import club.wustfly.inggua.ui.views.SelectSendTimeDialog;

public class ConfirmOrderActivity extends BaseActivity implements SelectSendTimeDialog.OnTimeSelector {

    private static String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};
    private String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};

    @BindView(R.id.order_content)
    LinearLayout order_content;
    @BindView(R.id.divider_bar)
    ImageView divider_bar;
    @BindView(R.id.address_name_txt)
    TextView address_name_txt;
    @BindView(R.id.address_phone_txt)
    TextView address_phone_txt;
    @BindView(R.id.address_txt)
    TextView address_txt;
    @BindView(R.id.wrapper_fee)
    TextView wrapper_fee;
    @BindView(R.id.discount_txt)
    TextView discount_txt;
    @BindView(R.id.good_fee_txt)
    TextView good_fee_txt;
    @BindView(R.id.total_fee_txt)
    TextView total_fee_txt;
    @BindView(R.id.showTimeStr_txt)
    TextView showTimeStr_txt;

    Address address;
    GoodItem good;
    int page;
    int num;
    int condition;
    int discount;
    String fid = "";

    String timeStr;

    String totalFeeStr;
    String packFeeStr;
    String goodFeeStr;


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

        Intent intent = getIntent();
        Gson gson = new Gson();
        fid = intent.getStringExtra("fid");
        address = gson.fromJson(intent.getStringExtra("address"), Address.class);
        good = gson.fromJson(intent.getStringExtra("good"), GoodItem.class);
        page = intent.getIntExtra("page", 1);
        num = intent.getIntExtra("num", 1);
        String boundstr = intent.getStringExtra("boundstr");
        if (!TextUtils.isEmpty(boundstr)) {
            String[] boundStrs = boundstr.split(";");
            condition = Integer.parseInt(boundStrs[0]);
            discount = Integer.parseInt(boundStrs[1]);
        }

        address_name_txt.setText(address.getConsignee());
        address_phone_txt.setText(address.getPhone());
        address_txt.setText(address.getAddress());

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

        values = new String[]{page + "张", good.getSize(), good.getIssingle(), good.getColor(), good.getLayout(), num + "份", good.getBinding()};

        order_content.removeAllViews();
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.confirm_order_content_item_layout, null);
            TextView label = view.findViewById(R.id.label);
            TextView value = view.findViewById(R.id.value);
            label.setText(labels[i]);
            value.setText(values[i]);
            order_content.addView(view);
        }

        double packFee = Double.parseDouble(good.getPackfree()) * page;
        DecimalFormat df = new DecimalFormat("#0.00");
        packFeeStr = df.format(packFee);
        wrapper_fee.setText("￥" + packFeeStr);

        if (TextUtils.isEmpty(boundstr)) {
            discount_txt.setText("无");
        } else {
            discount_txt.setText("满" + condition + "减" + discount + "元");
        }

        double goodFee = Double.parseDouble(good.getPrice()) * page;
        goodFeeStr = df.format(goodFee);
        good_fee_txt.setText("￥" + goodFeeStr);


        totalFeeStr = df.format(packFee + goodFee - discount);
        total_fee_txt.setText("￥" + totalFeeStr);

    }

    @OnClick({R.id.select_send_time, R.id.submit_order})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.select_send_time:
                new SelectSendTimeDialog(this, this).show();
                break;
            case R.id.submit_order:
                submitOrder();
                break;
        }
    }

    private void submitOrder() {
        if (TextUtils.isEmpty(timeStr)) {
            showToast("请选择配送时间");
            return;
        }
        SubmitOrderParam param = new SubmitOrderParam();
        param.setFid(fid);
        param.setUid(Session.getSession().getUser().getId() + "");
        param.setNumber(num + "");
        param.setTotal(totalFeeStr);
        param.setPackfree(packFeeStr);
        param.setMoney(totalFeeStr);
        param.setPage(page + "");
        param.setApptime(timeStr);
        param.setGid(good.getId() + "");
        showProgressDialog();
        RequestWrapper.submitOrder(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSubmitOrderResult(SubmitOrderRespDto respDto) {
        showToast("提交成功");
        Intent intent = new Intent(this, SelectPayChannelActivity.class);
        intent.putExtra("oid", respDto.getOid());
        startActivity(intent);
        finish();
    }

    @Override
    public void selectTime(String showTimeStr, String timeStr) {
        showTimeStr_txt.setText(showTimeStr);
        this.timeStr = timeStr;
    }

}
