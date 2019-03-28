package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import club.wustfly.yingua.R;
import club.wustfly.yingua.model.req.RegisterParam;
import club.wustfly.yingua.model.resp.RegisterRespDto;
import club.wustfly.yingua.net.RequestWrapper;
import club.wustfly.yingua.ui.base.BaseActivity;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.phone_input)
    EditText phone_input;
    @BindView(R.id.verify_code_input)
    EditText verify_code_input;
    @BindView(R.id.password_input)
    EditText password_input;
    @BindView(R.id.password_re_input)
    EditText password_re_input;

    String phone = "";
    String verifyCode = "";
    String password = "";
    String rePassword = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        init();
    }

    private void init() {
        setTitle("注册");
        setBack();
        setHeaderTopMargin();

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
                phone = editable.toString();
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
        password_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editable.toString();
            }
        });
        password_re_input.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rePassword = editable.toString();
            }
        });

    }


    @OnClick({})
    public void handleClick(View view) {
        switch (view.getId()) {
            case R.id.recieve_verify_code_btn:
                recieveVerifyCode();
                break;
            case R.id.register_btn:
                register();
                break;
        }
    }

    private void recieveVerifyCode() {

    }


    private void register() {
        boolean matches = Pattern.compile("^\\d{11}$").matcher(phone).matches();
        if (!matches) {
            showToast("请输入有效的11位手机号");
            return;
        }
        matches = Pattern.compile("^\\d{4}$").matcher(verifyCode).matches();
        if (!matches) {
            showToast("请输入4位数字验证码");
            return;
        }

        matches = Pattern.compile("[0-9a-zA-Z_]{6,16}").matcher(password).matches();
        if (!matches) {
            showToast("请输入6至16位字母、数字和下划线组合的密码");
            return;
        }

        if (!password.equals(rePassword)) {
            showToast("两次输入密码不一致");
            return;
        }

        RegisterParam param = new RegisterParam();
        param.setPhone(phone);
        param.setPassword(password);

        showProgressDialog();
        RequestWrapper.register(param);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveRegisterResult(RegisterRespDto registerRespDto) {
        showToast("注册成功");
        finish();
    }


}
