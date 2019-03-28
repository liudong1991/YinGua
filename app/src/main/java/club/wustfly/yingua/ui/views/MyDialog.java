package club.wustfly.yingua.ui.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.model.bean.Address;
import club.wustfly.yingua.ui.adapter.MyDialogAdapter;

public class MyDialog extends Dialog {

    @BindView(R.id.title)
    TextView titleTxt;
    @BindView(R.id.content)
    FrameLayout content;

    MyDialogType type;
    Map<MyDialogType, Integer> status;
    Address address;
    Address temp_address = new Address();

    MyDialogAdapter myDialogAdapter;

    Context mContext;

    public enum MyDialogType {
        SELCT_BOOKBINDING("选择装订", new String[]{"不装订", "左上角装订", "左侧装订", "上侧装订"}),
        EDIT_LAYOUT("编辑布局", new String[]{"每版打印1页", "每版打印2页", "每版打印4页", "每版打印6页"}),
        EDIT_COLOR("编辑颜色", new String[]{"黑白", "彩色"}),
        EDIT_SINGLE_DOUBLE_PAGE("编辑单双面", new String[]{"单面", "双面"}),
        PAPER_SPECIFICATION("纸张规格", new String[]{"A4", "A3"}),
        EDIT_ADDRESS("编辑地址", new String[]{});

        private String titleStr;
        private String[] items;

        MyDialogType(String titleStr, String[] items) {
            this.titleStr = titleStr;
            this.items = items;
        }

        public String getTitleStr() {
            return titleStr;
        }

        public String[] getItems() {
            return items;
        }
    }

    MyDialog(@NonNull Context context, MyDialogType type) {
        super(context, R.style.MyDialog);
        this.mContext = context;
        this.type = type;
    }

    void setStatus(Map<MyDialogType, Integer> status) {
        this.status = status;
    }

    void setAddress(Address address) {
        this.address = address;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        setContentView(R.layout.my_dialog_layout);


        ButterKnife.bind(this);
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = defaultDisplay.getWidth() * 4 / 5;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);

        init();
    }

    private void init() {

        titleTxt.setText(type.getTitleStr());

        switch (type) {
            case SELCT_BOOKBINDING:
            case EDIT_LAYOUT:
            case EDIT_COLOR:
            case EDIT_SINGLE_DOUBLE_PAGE:
            case PAPER_SPECIFICATION:
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog_common_layout, null);
                content.addView(view1);
                RecyclerView recyclerView = view1.findViewById(R.id.recycler_view);
                Integer selectedIndex = status.get(type);
                selectedIndex = selectedIndex == null ? 0 : selectedIndex;
                myDialogAdapter = new MyDialogAdapter(getContext(), type, selectedIndex);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(myDialogAdapter);
                break;
            case EDIT_ADDRESS:
                View view2 = LayoutInflater.from(getContext()).inflate(R.layout.my_dialog_address_layout, null);
                content.addView(view2);
                EditText name_edit = view2.findViewById(R.id.name);
                EditText phone_edit = view2.findViewById(R.id.phone);
                EditText address_edit = view2.findViewById(R.id.select_send_time);
                name_edit.setText(address.getName());
                phone_edit.setText(address.getPhone());
                address_edit.setText(address.getAddress());

                name_edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        temp_address.setName(editable.toString());
                    }
                });
                phone_edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        temp_address.setPhone(editable.toString());
                    }
                });
                address_edit.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        temp_address.setAddress(editable.toString());
                    }
                });
                break;
        }

    }

    @OnClick({R.id.cancel_btn, R.id.save_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_btn:
                dismiss();
                break;
            case R.id.save_btn:
                switch (type) {
                    case SELCT_BOOKBINDING:
                    case EDIT_LAYOUT:
                    case EDIT_COLOR:
                    case EDIT_SINGLE_DOUBLE_PAGE:
                    case PAPER_SPECIFICATION:
                        status.put(type, myDialogAdapter.getSelectedIndex());
                        break;
                    case EDIT_ADDRESS:
                        address.setValue(temp_address);
                        break;
                }

                if (mOnDialogSaveCallback != null) {
                    mOnDialogSaveCallback.handle(type);
                }
                dismiss();
                break;

        }
    }

    private OnDialogSaveCallback mOnDialogSaveCallback;

    public MyDialog setmOnDialogSaveCallback(OnDialogSaveCallback mOnDialogSaveCallback) {
        this.mOnDialogSaveCallback = mOnDialogSaveCallback;
        return this;
    }

    public interface OnDialogSaveCallback {
        void handle(MyDialogType type);
    }

}
