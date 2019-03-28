package club.wustfly.yingua.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import club.wustfly.yingua.MainActivity;
import club.wustfly.yingua.R;
import club.wustfly.yingua.cache.Session;
import club.wustfly.yingua.model.event.LoginFinishEvent;
import club.wustfly.yingua.model.req.LoginParam;
import club.wustfly.yingua.model.resp.LoginRespDto;
import club.wustfly.yingua.net.RequestWrapper;
import club.wustfly.yingua.ui.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.yz_code_login_label)
    TextView yz_code_login_label;
    @BindView(R.id.m_code_login_label)
    TextView m_code_login_label;
    @BindView(R.id.yz_code_login)
    View yz_code_login;
    @BindView(R.id.m_code_login)
    View m_code_login;

    @BindView(R.id.phone_input_verify)
    EditText phone_input_verify;
    @BindView(R.id.verify_code_input)
    EditText verify_code_input;
    @BindView(R.id.phone_input_password)
    EditText phone_input_password;
    @BindView(R.id.password_input)
    EditText password_input;

    String phone_verify = "";
    String verify_code = "";
    String phone_password = "";
    String password = "";

    String type = "1";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setTitle("登录");
        setSubtitle("注册", new IOnSubtitle() {
            @Override
            public void handle() {
                startActivity(RegisterActivity.class);
            }
        });
        setHeaderTopMargin();
        phone_input_verify.addTextChangedListener(new TextWatcher() {

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
                phone_verify = editable.toString();
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
                verify_code = editable.toString();
            }
        });
        phone_input_password.addTextChangedListener(new TextWatcher() {

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
                phone_password = editable.toString();
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

    }

    @OnClick({R.id.yz_code_login_label, R.id.m_code_login_label, R.id.forget_password_btn, R.id.login_btn, R.id.weixin_login_btn})
    public void handClick(View view) {
        switch (view.getId()) {
            case R.id.yz_code_login_label:
                yz_code_login.setVisibility(View.VISIBLE);
                m_code_login.setVisibility(View.GONE);
                yz_code_login_label.setTextColor(Color.parseColor("#FBDC19"));
                m_code_login_label.setTextColor(Color.parseColor("#333333"));
                yz_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.login_type_dot));
                m_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                type = "1";
                break;
            case R.id.m_code_login_label:
                yz_code_login.setVisibility(View.GONE);
                m_code_login.setVisibility(View.VISIBLE);
                yz_code_login_label.setTextColor(Color.parseColor("#333333"));
                m_code_login_label.setTextColor(Color.parseColor("#FBDC19"));
                yz_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                m_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.login_type_dot));

                type = "2";
                break;
            case R.id.forget_password_btn:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.login_btn:
                login();
                break;
            case R.id.weixin_login_btn:
                startActivity(ThirdPartyLoginActivity.class);
                break;
        }
    }


    private void login() {
        LoginParam param = new LoginParam();
        param.setType(type);
        if ("1".equals(type)) {
            boolean matches = Pattern.compile("^\\d{11}$").matcher(phone_verify).matches();
            if (!matches) {
                showToast("请输入有效的11位手机号");
                return;
            }
            matches = Pattern.compile("^\\d{4}$").matcher(verify_code).matches();
            if (!matches) {
                showToast("请输入4位数字验证码");
                return;
            }

            param.setPhone(phone_verify);
            param.setPassword(verify_code);
        } else if ("2".equals(type)) {
            boolean matches = Pattern.compile("^\\d{11}$").matcher(phone_password).matches();
            if (!matches) {
                showToast("请输入有效的11位手机号");
                return;
            }
            matches = Pattern.compile("[0-9a-zA-Z_]{6,16}").matcher(password).matches();
            if (!matches) {
                showToast("请输入6至16位字母、数字和下划线组合的密码");
                return;
            }

            param.setPhone(phone_password);
            param.setPassword(password);
        }

        showProgressDialog();
        RequestWrapper.login(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveLoginResult(LoginRespDto loginRespDto) {
        Session.getSession().login(loginRespDto.getUser());
        startActivity(MainActivity.class);
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void customFinish(LoginFinishEvent event) {
        finish();
    }

}
