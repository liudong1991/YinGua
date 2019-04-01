package club.wustfly.inggua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.MainActivity;
import club.wustfly.inggua.R;
import club.wustfly.inggua.model.req.ObtainVerifyCodeRequestParam;
import club.wustfly.inggua.model.resp.ObtainVerifyCodeRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;

public class BindPhoneNumActivity extends BaseActivity {

    private static final String TAG = BindPhoneNumActivity.class.getSimpleName();

    @BindView(R.id.phone_input)
    EditText phone_input;
    @BindView(R.id.verify_code_input)
    EditText verify_code_input;
    @BindView(R.id.obtain_verify_code_btn)
    TextView obtain_verify_code_btn;

    String phoneNum = "";
    String verifyCode = "";

    ObtainVerifyCodeRequestParam param = new ObtainVerifyCodeRequestParam();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bind_phone_num_activity_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("绑定手机");
        setBack();
        setHeaderBackgroundColor("#FFFFFF");
        setHeaderTopPadding();

        phone_input.addTextChangedListener(new TextWatcher() {

            CharSequence old;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                old = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.length() > 0 && !Pattern.compile("[0-9]+").matcher(str).matches()) {
                    editable.delete(0, editable.length());
                    editable.append(old);
                } else if (str.length() > 11) {
                    editable.delete(11, str.length());
                }
                phoneNum = editable.toString();
            }
        });
        verify_code_input.addTextChangedListener(new TextWatcher() {

            CharSequence old;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                old = charSequence;
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (str.length() > 0 && !Pattern.compile("[0-9]+").matcher(str).matches()) {
                    editable.delete(0, editable.length());
                    editable.append(old);
                } else if (str.length() > 4) {
                    editable.delete(4, str.length());
                }
                verifyCode = editable.toString();
            }
        });
    }

    @OnClick({R.id.obtain_verify_code_btn, R.id.bind_btn})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.obtain_verify_code_btn:
                obtainVerifyCode();
                break;
            case R.id.bind_btn:
                startActivity(MainActivity.class);
                finish();
                break;
        }

    }

    private void obtainVerifyCode() {
        boolean matches = Pattern.compile("^\\d{11}$").matcher(phoneNum).matches();
        if (!matches) {
            showToast("请输入有效的11位手机号");
            return;
        }
        ObtainVerifyCodeRequestParam param = new ObtainVerifyCodeRequestParam();
        param.setPhone(phoneNum);
        param.setType("4");
        param.setFrom(TAG);

        showProgressDialog();
        RequestWrapper.obtainVerifyCode(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveObtainVerifyCodeResult(ObtainVerifyCodeRespDto respDto) {
        if (!TAG.equals(respDto.getFrom())) return;
        showToast("发送成功");
    }
}
