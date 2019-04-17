package club.wustfly.inggua.ui;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.inggua.MainActivity;
import club.wustfly.inggua.R;
import club.wustfly.inggua.cache.Session;
import club.wustfly.inggua.model.bean.User;
import club.wustfly.inggua.model.bean.WxUserInfoBean;
import club.wustfly.inggua.model.event.LoginFinishEvent;
import club.wustfly.inggua.model.req.LoginParam;
import club.wustfly.inggua.model.req.ObtainVerifyCodeRequestParam;
import club.wustfly.inggua.model.req.WXLoginParam;
import club.wustfly.inggua.model.resp.LoginRespDto;
import club.wustfly.inggua.model.resp.ObtainVerifyCodeRespDto;
import club.wustfly.inggua.model.resp.WXLoginRespDto;
import club.wustfly.inggua.net.RequestWrapper;
import club.wustfly.inggua.ui.base.BaseActivity;
import club.wustfly.inggua.wxapi.WXEntryActivity;

public class LoginActivity extends BaseActivity {

    public static final String OTHER_APP = "OTHER APP";

    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final int WX_LOGIN_BIND_PHONE_REQUESTCODE = 1001;

    @BindView(R.id.yz_code_login_label)
    TextView yz_code_login_label;
    @BindView(R.id.m_code_login_label)
    TextView m_code_login_label;
    @BindView(R.id.yz_code_login)
    View yz_code_login;
    @BindView(R.id.m_code_login)
    View m_code_login;

    @BindView(R.id.obtain_verify_code_btn)
    TextView obtain_verify_code_btn;
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

    User user;

    private IWXAPI api;

    int count = 0;
    Timer timer = new Timer();

    String from = "";
    String resPath = "";

    ObtainVerifyCodeRequestParam param = new ObtainVerifyCodeRequestParam();

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

        Intent zIntent = getIntent();

        Log.i("wust-lz", "wust-lz==>" + zIntent);
        Set<String> categories = zIntent.getCategories();
        if (categories == null || !categories.contains("android.intent.category.LAUNCHER")) {
            from = OTHER_APP;
            Uri uri = zIntent.getData();
            String path = uri.getPath();
            File file = new File(Environment.getExternalStorageDirectory(), path.replace("/external/", ""));
            resPath = file.getAbsolutePath();
        }

        if (Session.getSession().isLogin()) {
            goMain();
        }

    }

    private void goMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("resPath", resPath);
        startActivity(intent);
        finish();
    }

    @OnClick({R.id.yz_code_login_label, R.id.m_code_login_label, R.id.forget_password_btn, R.id.login_btn, R.id.weixin_login_btn, R.id.obtain_verify_code_btn})
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
                //startActivity(ThirdPartyLoginActivity.class);
                wxLogin();
                break;
            case R.id.obtain_verify_code_btn:
                obtainVerifyCode();
                break;
        }
    }

    public void wxLogin() {
        showProgressDialog();
        // 通过WXAPIFactory工厂获取IWXApI的示例
        api = WXAPIFactory.createWXAPI(this, WXEntryActivity.WX_APP_ID, true);
        // 将应用的appid注册到微信
        api.registerApp(WXEntryActivity.WX_APP_ID);

        if (api != null && api.isWXAppInstalled()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo";
            api.sendReq(req);
        } else {
            Toast.makeText(this, "用户未安装微信", Toast.LENGTH_SHORT).show();
            dismissProgressDialog();
        }
    }

    private void obtainVerifyCode() {
        boolean matches = Pattern.compile("^\\d{11}$").matcher(phone_verify).matches();
        if (!matches) {
            showToast("请输入有效的11位手机号");
            return;
        }
        param = new ObtainVerifyCodeRequestParam();
        param.setPhone(phone_verify);
        param.setType("1");
        param.setFrom(TAG);

        showProgressDialog();
        RequestWrapper.obtainVerifyCode(param);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void recieveObtainVerifyCodeResult(ObtainVerifyCodeRespDto respDto) {
        if (!TAG.equals(respDto.getFrom())) return;
        showToast("发送成功");
        obtain_verify_code_btn.setEnabled(false);
        count = 60;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        count = count - 1;
                        if (count > 0) {
                            obtain_verify_code_btn.setText("获取验证码(" + count + ")");
                        } else {
                            obtain_verify_code_btn.setText("获取验证码");
                            obtain_verify_code_btn.setEnabled(true);
                            timer.cancel();
                        }
                    }
                });
            }
        }, 0, 1000);
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
            if (!verify_code.equals(this.param.getCode())) {
                showToast("验证码输入有误");
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
        goMain();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void customFinish(LoginFinishEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveWxLogin(WxUserInfoBean wxUserInfoBean) {
        //showProgressDialog();
        wxLogin(wxUserInfoBean.getOpenid(), wxUserInfoBean.getNickname());
    }

    private void wxLogin(String openid, String nickname) {
        WXLoginParam param = new WXLoginParam();
        param.setOpenid(openid);
        param.setNickname(nickname);
        showProgressDialog();
        RequestWrapper.wxLogin(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveWxLoginResult(WXLoginRespDto wxLoginRespDto) {
        user = wxLoginRespDto.getUser();
        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            Intent intent = new Intent(this, BindPhoneNumActivity.class);
            intent.putExtra("uid", user.getId());
            startActivityForResult(intent, WX_LOGIN_BIND_PHONE_REQUESTCODE);
            return;
        }
        Session.getSession().login(user);
        goMain();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WX_LOGIN_BIND_PHONE_REQUESTCODE && resultCode == RESULT_OK) {
            String phone = data.getStringExtra("phone");
            user.setPhone(phone);
            Session.getSession().login(user);
            goMain();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer = null;
    }
}
