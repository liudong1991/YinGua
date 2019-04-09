package club.wustfly.inggua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.model.bean.OrderItem;
import club.wustfly.inggua.model.bean.WXPayBean;
import club.wustfly.inggua.model.event.PaySuccessEvent;
import club.wustfly.inggua.model.req.ObtainPayTokenParam;
import club.wustfly.inggua.model.req.SelectPayParam;
import club.wustfly.inggua.model.resp.ObtainPayTokenRespDto;
import club.wustfly.inggua.model.resp.SelectPayRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;
import club.wustfly.inggua.wxapi.WXEntryActivity;

public class SelectPayChannelActivity extends BaseActivity {

    @BindView(R.id.order_detail)
    LinearLayout order_detail;
    @BindView(R.id.weixin_select)
    ImageView weixin_select;
    @BindView(R.id.zfb_select)
    ImageView zfb_select;
    @BindView(R.id.order_id_txt)
    TextView order_id_txt;
    @BindView(R.id.wrapper_fee)
    TextView wrapper_fee;
    @BindView(R.id.total_fee_txt)
    TextView total_fee_txt;

    int oid;
    int payMode = 1;// 1--微信支付  2--支付宝支付

    private String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private String[] values = {"30张", "A4", "单面", "黑白", "每版1页", "1份", "不装订"};

    OrderItem orderItem;

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

        oid = getIntent().getIntExtra("oid", -1);

        fillOrderDetail();

        selectOrderPay();


    }

    private void selectOrderPay() {

        SelectPayParam param = new SelectPayParam();
        param.setOid(oid + "");
        showProgressDialog();
        RequestWrapper.selectPay(param);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveSelectOrderPay(SelectPayRespDto respDto) {
        orderItem = respDto.getOrder();
        values = new String[]{orderItem.getPage() + "张", orderItem.getSize(), orderItem.getIssingle(), orderItem.getColor(), orderItem.getLayout(), orderItem.getNumber() + "份", orderItem.getBinding()};
        fillOrderDetail();
        order_id_txt.setText(orderItem.getId() + "");
        wrapper_fee.setText("￥" + orderItem.getPackfree());
        total_fee_txt.setText("￥" + orderItem.getMoney());
    }

    private void fillOrderDetail() {
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
                payMode = 1;
                break;
            case R.id.zfb_pay:
                weixin_select.setImageResource(R.mipmap.pay_channel_unselected);
                zfb_select.setImageResource(R.mipmap.pay_channel_selected);
                payMode = 2;
                break;
            case R.id.next_handle_btn:
                obtainPayToken();
                break;
        }
    }

    private void obtainPayToken() {
        ObtainPayTokenParam param = new ObtainPayTokenParam();
        param.setPaymode(payMode);
        param.setOid(oid);
        showProgressDialog();
        RequestWrapper.obtainPayToken(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveObtainPayTokenResult(ObtainPayTokenRespDto respDto) {
        if (respDto.getPaymode() == 1) {
            wxPay(respDto.getData());
        } else if (respDto.getPaymode() == 2) {
            alipay(respDto.getData());
        }
    }

    private void wxPay(String data) {
        Gson gson = new Gson();
        WXPayBean wxPayBean = gson.fromJson(data, WXPayBean.class);
        Log.i("wust", "wust-lz" + wxPayBean.toString());
        IWXAPI api = WXAPIFactory.createWXAPI(this, null);
        api.registerApp(WXEntryActivity.WX_APP_ID);
        PayReq req = new PayReq();
        req.appId = wxPayBean.getAppid();
        req.partnerId = wxPayBean.getPartnerid();
        req.prepayId = wxPayBean.getPrepayid();
        req.nonceStr = wxPayBean.getNoncestr();
        req.timeStamp = wxPayBean.getTimestamp();
        req.packageValue = "Sign=WXPay";
        req.sign = wxPayBean.getSign();
        api.sendReq(req);
    }

    private void alipay(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(SelectPayChannelActivity.this);
                final Map<String, String> result = payTask.payV2(data, true);
                Log.i(getClass().getName(), "wust==>alipay result:" + result);
                if ("9000".equals(result.get("resultStatus"))) {
                    EventBus.getDefault().post(new PaySuccessEvent());
                    if (TextUtils.isEmpty(result.get("memo"))) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SelectPayChannelActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    if (TextUtils.isEmpty(result.get("memo"))) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SelectPayChannelActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivePayResult(PaySuccessEvent event) {
        Intent intent = new Intent(this, PaySuccessActivity.class);
        intent.putExtra("order", orderItem.toString());
        startActivity(intent);
        finish();
    }

}
