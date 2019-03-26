package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.model.Address;
import club.wustfly.yingua.ui.base.BaseActivity;
import club.wustfly.yingua.ui.views.MyAddressDialog;
import club.wustfly.yingua.ui.views.MyCommonDialog;
import club.wustfly.yingua.ui.views.MyDialog;

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


    Map<MyDialog.MyDialogType, Integer> selectStatus = new HashMap<>();
    Address address = new Address();
    int num = 1;

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
    }

    @Override
    public void handle(MyDialog.MyDialogType type) {
        switch (type) {
            case SELCT_BOOKBINDING:
                bookbinding_txt.setText(type.getItems()[selectStatus.get(type)]);
                break;
            case EDIT_LAYOUT:
                layout_txt.setText(type.getItems()[selectStatus.get(type)]);
                break;
            case EDIT_COLOR:
                color_txt.setText(type.getItems()[selectStatus.get(type)]);
                break;
            case EDIT_SINGLE_DOUBLE_PAGE:
                single_double_txt.setText(type.getItems()[selectStatus.get(type)]);
                break;
            case PAPER_SPECIFICATION:
                paper_txt.setText(type.getItems()[selectStatus.get(type)]);
                break;
            case EDIT_ADDRESS:
                address_name_txt.setText(address.getName());
                address_phone_txt.setText(address.getPhone());
                address_txt.setText(address.getAddress());
                break;
        }
    }

    @OnClick({R.id.address_layout, R.id.paper_style, R.id.single_or_double, R.id.print_layout, R.id.print_color, R.id.bookbinding, R.id.minus, R.id.plus, R.id.print_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.address_layout:
                new MyAddressDialog(this, MyDialog.MyDialogType.EDIT_ADDRESS, address).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.paper_style:
                new MyCommonDialog(this, MyDialog.MyDialogType.PAPER_SPECIFICATION, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.single_or_double:
                new MyCommonDialog(this, MyDialog.MyDialogType.EDIT_SINGLE_DOUBLE_PAGE, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.print_layout:
                new MyCommonDialog(this, MyDialog.MyDialogType.EDIT_LAYOUT, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.print_color:
                new MyCommonDialog(this, MyDialog.MyDialogType.EDIT_COLOR, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.bookbinding:
                new MyCommonDialog(this, MyDialog.MyDialogType.SELCT_BOOKBINDING, selectStatus).setmOnDialogSaveCallback(this).show();
                break;
            case R.id.minus:
                if (num > 1) {
                    num = num - 1;
                    num_txt.setText(num + "");
                }
                if (num == 1) {

                }
                break;
            case R.id.plus:
                num = num + 1;
                num_txt.setText(num + "");
                if (num == 2) {

                }
                break;

            case R.id.print_btn:
                startActivity(ConfirmOrderActivity.class);
                break;
        }
    }


}
