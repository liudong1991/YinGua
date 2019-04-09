package club.wustfly.inggua.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.bean.Address;
import club.wustfly.inggua.model.bean.CouponItem;
import club.wustfly.inggua.model.bean.FileItem;
import club.wustfly.inggua.model.bean.GoodItem;
import club.wustfly.inggua.model.req.ObtainEditParam;
import club.wustfly.inggua.model.req.UpdateAddressParam;
import club.wustfly.inggua.model.resp.ObtainEditRespDto;
import club.wustfly.inggua.model.resp.UpdateAddressRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;
import club.wustfly.inggua.ui.views.MyAddressDialog;
import club.wustfly.inggua.ui.views.MyCommonDialog;
import club.wustfly.inggua.ui.views.MyDialog;

import static club.wustfly.inggua.ui.views.MyDialog.MyDialogType.EDIT_COLOR;
import static club.wustfly.inggua.ui.views.MyDialog.MyDialogType.EDIT_LAYOUT;
import static club.wustfly.inggua.ui.views.MyDialog.MyDialogType.EDIT_SINGLE_DOUBLE_PAGE;
import static club.wustfly.inggua.ui.views.MyDialog.MyDialogType.PAPER_SPECIFICATION;
import static club.wustfly.inggua.ui.views.MyDialog.MyDialogType.SELCT_BOOKBINDING;

public class EditPrintActivity extends BaseActivity implements MyDialog.OnDialogSaveCallback {

    @BindView(R.id.address_name_txt)
    TextView address_name_txt;
    @BindView(R.id.address_phone_txt)
    TextView address_phone_txt;
    @BindView(R.id.address_txt)
    TextView address_txt;

    @BindView(R.id.paper_txt)
    TextView paper_txt;
    @BindView(R.id.single_double_txt)
    TextView single_double_txt;
    @BindView(R.id.color_txt)
    TextView color_txt;
    @BindView(R.id.layout_txt)
    TextView layout_txt;
    @BindView(R.id.bookbinding_txt)
    TextView bookbinding_txt;

    @BindView(R.id.num)
    TextView num_txt;

    @BindView(R.id.total_page_txt)
    TextView total_page_txt;
    @BindView(R.id.coupon_txt)
    TextView coupon_txt;
    @BindView(R.id.total_fee_txt)
    TextView total_fee_txt;


    Map<MyDialog.MyDialogType, Integer> selectStatus = new HashMap<>();
    Address address = new Address();
    Address tempAddress = new Address();

    String fid = "";
    ObtainEditRespDto respDto;

    GoodItem item = new GoodItem("A4", "单页", "黑白", "每版1页", "不装订");

    int num = 1; //份数
    int totalPage = 0; // 每份页数
    double price = 0; //单价
    double packFee = 0; //包装费

    int realPaperPage = 0;
    String boundStr;

    MyDialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_print_activity_layout);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("文件名");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        fid = getIntent().getStringExtra("fid");

        obtainEdit();
    }

    @Override
    public void handle(MyDialog.MyDialogType type) {
        switch (type) {
            case SELCT_BOOKBINDING:
                bookbinding_txt.setText(type.getItems()[selectStatus.get(type)]);
                item.setBinding(type.getItems()[selectStatus.get(type)]);
                queryPrice();
                calcuTotalFee();
                break;
            case EDIT_LAYOUT:
                layout_txt.setText(type.getItems()[selectStatus.get(type)]);
                item.setLayout(type.getItems()[selectStatus.get(type)]);
                queryPrice();
                calcuTotalFee();
                break;
            case EDIT_COLOR:
                color_txt.setText(type.getItems()[selectStatus.get(type)]);
                item.setColor(type.getItems()[selectStatus.get(type)]);
                queryPrice();
                calcuTotalFee();
                break;
            case EDIT_SINGLE_DOUBLE_PAGE:
                single_double_txt.setText(type.getItems()[selectStatus.get(type)]);
                item.setIssingle(type.getItems()[selectStatus.get(type)]);
                queryPrice();
                calcuTotalFee();
                break;
            case PAPER_SPECIFICATION:
                paper_txt.setText(type.getItems()[selectStatus.get(type)]);
                item.setSize(type.getItems()[selectStatus.get(type)]);
                queryPrice();
                calcuTotalFee();
                break;
            case EDIT_ADDRESS:
                if (!tempAddress.isOk()) {
                    showToast("请输入正确的地址信息");
                    return;
                }
                UpdateAddressParam param = new UpdateAddressParam();
                param.setUid(Session.getSession().getUser().getId() + "");
                param.setConsignee(tempAddress.getConsignee());
                param.setPhone(tempAddress.getPhone());
                param.setAddress(tempAddress.getAddress());
                showProgressDialog();
                RequestWrapper.updateAddress(param);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveUpdateAddressResult(UpdateAddressRespDto respDto) {
        address.setValue(tempAddress);
        address_name_txt.setText(address.getConsignee());
        address_phone_txt.setText(address.getPhone());
        address_txt.setText(address.getAddress());

        myDialog.dismiss();
    }

    @OnClick({R.id.address_layout, R.id.paper_style, R.id.single_or_double, R.id.print_layout, R.id.print_color, R.id.bookbinding, R.id.minus, R.id.plus, R.id.print_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.address_layout:
                tempAddress.setValue(address);
                myDialog = new MyAddressDialog(this, MyDialog.MyDialogType.EDIT_ADDRESS, tempAddress).setmOnDialogSaveCallback(this);
                myDialog.show();
                break;
            case R.id.paper_style:
                new MyCommonDialog(this, PAPER_SPECIFICATION, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.single_or_double:
                new MyCommonDialog(this, EDIT_SINGLE_DOUBLE_PAGE, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.print_layout:
                new MyCommonDialog(this, EDIT_LAYOUT, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.print_color:
                new MyCommonDialog(this, EDIT_COLOR, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.bookbinding:
                new MyCommonDialog(this, SELCT_BOOKBINDING, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.minus:
                if (num > 1) {
                    num = num - 1;
                    num_txt.setText(num + "");
                }
                if (num == 1) {

                }
                calcuTotalFee();
                break;
            case R.id.plus:
                num = num + 1;
                num_txt.setText(num + "");
                if (num == 2) {

                }
                calcuTotalFee();
                break;

            case R.id.print_btn:
                if (!address.isOk()) {
                    showToast("请输入地址");
                    return;
                }

                if (item.getId() == null) return;

                Intent intent = new Intent(this, ConfirmOrderActivity.class);
                intent.putExtra("fid", fid);
                intent.putExtra("address", address.toString());
                intent.putExtra("good", item.toString());
                intent.putExtra("num", num);
                intent.putExtra("page", realPaperPage * num);
                intent.putExtra("boundstr", boundStr);
                startActivity(intent);
                break;
        }
    }

    private void obtainEdit() {
        ObtainEditParam param = new ObtainEditParam();
        param.setUid(Session.getSession().getUser().getId());
        param.setFid(fid);
        showProgressDialog();
        RequestWrapper.obtainEdit(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveObtainEditResult(ObtainEditRespDto respDto) {
        this.respDto = respDto;
        collectItems(respDto);
        calcuPage();
        queryPrice();
        calcuTotalFee();

        List<Address> addressList = respDto.getAddress();
        if (addressList.size() > 0) {
            this.address = addressList.get(0);
            this.tempAddress = addressList.get(0);
        }
        address_name_txt.setText(address.getConsignee());
        address_phone_txt.setText(address.getPhone());
        address_txt.setText(address.getAddress());
    }

    private void collectItems(ObtainEditRespDto respDto) {
        List<String> sizeList = new ArrayList<>();
        List<String> isSigleList = new ArrayList<>();
        List<String> colorList = new ArrayList<>();
        List<String> layoutList = new ArrayList<>();
        List<String> bindingList = new ArrayList<>();
        List<GoodItem> good = respDto.getGood();
        for (GoodItem g : good) {
            sizeList.add(g.getSize());
            isSigleList.add(g.getIssingle());
            colorList.add(g.getColor());
            layoutList.add(g.getLayout());
            bindingList.add(g.getBinding());
        }
        sizeList = new ArrayList<>(new HashSet<>(sizeList));
        isSigleList = new ArrayList<>(new HashSet<>(isSigleList));
        colorList = new ArrayList<>(new HashSet<>(colorList));
        layoutList = new ArrayList<>(new HashSet<>(layoutList));
        bindingList = new ArrayList<>(new HashSet<>(bindingList));
        SELCT_BOOKBINDING.setItems(bindingList.toArray(new String[bindingList.size()]));
        EDIT_LAYOUT.setItems(layoutList.toArray(new String[layoutList.size()]));
        EDIT_COLOR.setItems(colorList.toArray(new String[colorList.size()]));
        EDIT_SINGLE_DOUBLE_PAGE.setItems(isSigleList.toArray(new String[isSigleList.size()]));
        PAPER_SPECIFICATION.setItems(sizeList.toArray(new String[sizeList.size()]));

        item = new GoodItem(sizeList.get(0), isSigleList.get(0), colorList.get(0), layoutList.get(0), bindingList.get(0));
        bookbinding_txt.setText(item.getBinding());
        layout_txt.setText(item.getLayout());
        color_txt.setText(item.getColor());
        single_double_txt.setText(item.getIssingle());
        paper_txt.setText(item.getSize());
    }

    private void calcuPage() {
        List<FileItem> file = respDto.getFile();
        int page = 0;
        for (FileItem f : file) {
            page += f.getPage();
        }
        totalPage = page;
    }

    private void queryPrice() {
        List<GoodItem> good = respDto.getGood();
        boolean flag = false;
        for (GoodItem g : good) {
            if (item.equals(g)) {
                try {
                    price = Double.parseDouble(g.getPrice());
                    packFee = Double.parseDouble(g.getPackfree());
                    item.setId(g.getId());
                    item.setPrice(g.getPrice());
                    item.setPackfree(g.getPackfree());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                flag = true;
                break;
            }
        }
        if (!flag) {
            price = 0;
            packFee = 0;
            item.setId(null);
        }
    }

    private void calcuTotalFee() {
//        int layout = 1;
//        if ("每版1页".equals(item.getLayout())) {
//            layout = 1;
//        } else if ("每版2页".equals(item.getLayout())) {
//            layout = 2;
//        } else if ("每版4页".equals(item.getLayout())) {
//            layout = 4;
//        } else if ("每版6页".equals(item.getLayout())) {
//            layout = 6;
//        }

        int single_double = 1;
        if ("单页".equals(item.getIssingle())) {
            single_double = 1;
        } else if ("双页".equals(item.getIssingle())) {
            single_double = 2;
        }

        realPaperPage = (int) Math.ceil(/*Math.ceil(*/totalPage * 1.0 /*/ layout)*/ / single_double);

        total_page_txt.setText(realPaperPage * num + "页");

        double totalFee = realPaperPage * (price + packFee) * num;

        List<CouponItem> coupon = respDto.getCoupon();

        int couponIndex = -1;
        for (int i = 0; i < coupon.size() - 1; i++) {
            if (i == 0 && totalFee < Integer.parseInt(coupon.get(0).getCondition())) break;
            CouponItem item1 = coupon.get(i);
            CouponItem item2 = coupon.get(i + 1);
            if (totalFee >= Integer.parseInt(item1.getCondition()) && totalFee < Integer.parseInt(item2.getCondition())) {
                couponIndex = i;
                break;
            } else {
                couponIndex = i + 1;
            }
        }

        double lastRealFee;

        if (couponIndex == -1) {
            coupon_txt.setText("无");
            lastRealFee = totalFee;
            boundStr = "";
        } else {
            boundStr = coupon.get(couponIndex).getCondition() + ";" + coupon.get(couponIndex).getMoney();
            coupon_txt.setText("满" + coupon.get(couponIndex).getCondition() + "元优惠" + coupon.get(couponIndex).getMoney() + "元");
            lastRealFee = totalFee - Integer.parseInt(coupon.get(couponIndex).getMoney());
        }

        DecimalFormat df = new DecimalFormat("#0.00");
        total_fee_txt.setText(df.format(lastRealFee) + "元");

    }

}
