package club.wustfly.inggua.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.wustfly.inggua.R;
import club.wustfly.inggua.model.bean.OrderDetailItem;
import club.wustfly.inggua.model.bean.Staff;
import club.wustfly.inggua.model.req.DeleteOrderParam;
import club.wustfly.inggua.model.req.GetOrderDetailParam;
import club.wustfly.inggua.model.req.SignForParam;
import club.wustfly.inggua.model.resp.DeleteOrderRespDto;
import club.wustfly.inggua.model.resp.GetOrderDetailRespDto;
import club.wustfly.inggua.model.resp.SignForRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;

public class OrderDetailActivity extends BaseActivity {

    private static final String TAG = OrderDetailActivity.class.getSimpleName();

    private static final String STATUS_UNPAY = "status_unpay";
    private static final String STATUS_PAYED = "status_payed";
    private static final String STATUS_FINISHED = "status_finished";

    private static final int REQUEST_CALL_PERMISSION = 1001;

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
    @BindView(R.id.sender_name_txt)
    TextView sender_name_txt;
    @BindView(R.id.sender_phone_txt)
    TextView sender_phone_txt;
    @BindView(R.id.address_name_txt)
    TextView address_name_txt;
    @BindView(R.id.address_phone_txt)
    TextView address_phone_txt;
    @BindView(R.id.address_txt)
    TextView address_txt;
    @BindView(R.id.order_id_txt)
    TextView order_id_txt;
    @BindView(R.id.goods_total_fee)
    TextView goods_total_fee;
    @BindView(R.id.wrapper_fee)
    TextView wrapper_fee;
    @BindView(R.id.order_total_fee)
    TextView order_total_fee;
    @BindView(R.id.real_pay_amount)
    TextView real_pay_amount;
    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.create_time_txt)
    TextView create_time_txt;
    @BindView(R.id.send_time_txt)
    TextView send_time_txt;
    @BindView(R.id.deal_time_txt)
    TextView deal_time_txt;
    @BindView(R.id.contact_sender_btn)
    TextView contact_sender_btn;
    @BindView(R.id.confirm_recieve_goods_btn)
    TextView confirm_recieve_goods_btn;
    @BindView(R.id.deal_time_container)
    LinearLayout deal_time_container;


    private String[] labels = {"打印张数", "纸张规格", "单双面", "颜色", "布局", "份数", "装订"};

    private String[] values = {"--", "--", "--", "--", "--", "--", "--"};

    Integer id;//订单id

    OrderDetailItem order;

    String phone = "";


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

        id = getIntent().getIntExtra("oid", -1);

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

        fillOrderDetail();

        showProgressDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getOrderDetail();
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

    private void getOrderDetail() {
        GetOrderDetailParam param = new GetOrderDetailParam();
        param.setId(id);

        RequestWrapper.getOrderDetail(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveGetOrderDetailResult(GetOrderDetailRespDto respDto) {
        order = respDto.getOrder();
        Integer sts = order.getStatus();
        switch (sts) {
            case 1:
                status = STATUS_UNPAY;
                break;
            case 2:
            case 3:
                status = STATUS_PAYED;
                break;
            case 4:
                status = STATUS_FINISHED;
                break;
        }

        initStatus();
        fillData(order);


    }

    private void fillData(final OrderDetailItem order) {
        List<Staff> staffs = order.getStaff();
        if (staffs != null && staffs.size() > 0) {
            phone = staffs.get(0).getPhone();
            sender_name_txt.setText(staffs.get(0).getStaffname());
            sender_phone_txt.setText(phone);
        }

        address_name_txt.setText(order.getConsignee());
        address_phone_txt.setText(order.getPhone());
        address_txt.setText(order.getAddress());

        order_id_txt.setText(order.getOrdernum());

        values = new String[]{order.getPage() + "张", order.getSize(), order.getIssingle(), order.getColor(), order.getLayout(), order.getNumber() + "份", order.getBinding()};
        fillOrderDetail();

        goods_total_fee.setText("￥" + order.getTotal());
        wrapper_fee.setText("￥" + order.getPackfree());
        order_total_fee.setText("￥" + order.getMoney());
        real_pay_amount.setText("￥" + order.getMoney());

        order_id.setText(order.getId() + "");
        String addtime = order.getAddtime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!TextUtils.isEmpty(addtime)) {
            long l = Long.parseLong(addtime);
            Date date = new Date(l);
            create_time_txt.setText(sdf.format(date));
        } else {
            create_time_txt.setText("");
        }
        final String deliverytime = order.getDeliverytime();
        if (!TextUtils.isEmpty(deliverytime)) {
            long l = Long.parseLong(deliverytime);
            Date date = new Date(l);
            send_time_txt.setText(sdf.format(date));
        } else {
            send_time_txt.setText("");
        }
        String finishtime = order.getFinishtime();
        if (!TextUtils.isEmpty(finishtime)) {
            long l = Long.parseLong(finishtime);
            Date date = new Date(l);
            deal_time_txt.setText(sdf.format(date));
        } else {
            deal_time_txt.setText("");
        }

        if (order.getStatus() == 1) {
            pay_btn.setText("付款");
            pay_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(OrderDetailActivity.this, SelectPayChannelActivity.class);
                    intent.putExtra("oid", order.getId());
                    startActivity(intent);
                }
            });
        } else if (order.getStatus() == 2) {
            pay_btn.setText("待配送");
            pay_btn.setOnClickListener(null);
        }

        contact_sender_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(phone)) return;
                showContacts();
            }
        });

        confirm_recieve_goods_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signFor();
            }
        });

        delete_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteOrder();
            }
        });
    }

    private void deleteOrder() {
        DeleteOrderParam param = new DeleteOrderParam();
        param.setId(id + "");
        showProgressDialog();
        RequestWrapper.deleteOrder(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveDeleteOrderResult(DeleteOrderRespDto respDto) {
        showToast("删除成功");
        finish();
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
                Integer orderStatus = order.getStatus();
                if (orderStatus == 2) {
                    sender_container.setVisibility(View.GONE);
                    send_deal_time_container.setVisibility(View.GONE);

                    pay_btn.setVisibility(View.VISIBLE);
                    payed_status_container.setVisibility(View.GONE);
                    delete_order_btn.setVisibility(View.GONE);
                } else {
                    sender_container.setVisibility(View.VISIBLE);
                    send_deal_time_container.setVisibility(View.VISIBLE);
                    deal_time_container.setVisibility(View.GONE);

                    pay_btn.setVisibility(View.GONE);
                    payed_status_container.setVisibility(View.VISIBLE);
                    delete_order_btn.setVisibility(View.GONE);
                }
                break;
            case STATUS_FINISHED:
                logo2.setImageResource(R.mipmap.order_detail_pay_logo_yellow);
                logo3.setImageResource(R.mipmap.order_detail_deal_finish_logo_yellow);
                progress_2.setBackgroundColor(Color.parseColor("#F7E731"));
                label2.setTextColor(Color.parseColor("#FD7C13"));
                label3.setTextColor(Color.parseColor("#FD7C13"));

                sender_container.setVisibility(View.VISIBLE);
                send_deal_time_container.setVisibility(View.VISIBLE);
                deal_time_container.setVisibility(View.VISIBLE);

                pay_btn.setVisibility(View.GONE);
                payed_status_container.setVisibility(View.GONE);
                delete_order_btn.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void callSender() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data);
        startActivity(intent);
    }

    public void showContacts() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OrderDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
        } else {
            callSender();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callSender();
                } else {
                    Toast.makeText(getApplicationContext(), "获取拨打电话权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void signFor() {
        SignForParam param = new SignForParam();
        param.setTag(TAG);
        param.setId(id);
        param.setStatus(4);
        showProgressDialog();
        RequestWrapper.signFor(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveSignForResult(SignForRespDto respDto) {
        if (!TAG.equals(respDto.getTag())) return;
        showToast("签收成功");
        getOrderDetail();
    }
}
