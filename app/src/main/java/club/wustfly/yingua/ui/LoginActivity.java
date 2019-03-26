package club.wustfly.yingua.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.MainActivity;
import club.wustfly.yingua.R;
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
    }

    @OnClick({R.id.yz_code_login_label, R.id.m_code_login_label, R.id.forget_password_btn, R.id.login_btn, R.id.weixin_login_btn})
    public void handClick(View view) {
        switch (view.getId()) {
            case R.id.yz_code_login_label:
                yz_code_login.setVisibility(View.VISIBLE);
                m_code_login.setVisibility(View.GONE);
                yz_code_login_label.setTextColor(Color.parseColor("#FBDC19"));
                m_code_login_label.setTextColor(Color.parseColor("#333333"));
                Log.i("wust", ContextCompat.getDrawable(this, R.mipmap.login_type_dot).toString());
                yz_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.login_type_dot));
                m_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                break;
            case R.id.m_code_login_label:
                yz_code_login.setVisibility(View.GONE);
                m_code_login.setVisibility(View.VISIBLE);
                yz_code_login_label.setTextColor(Color.parseColor("#333333"));
                m_code_login_label.setTextColor(Color.parseColor("#FBDC19"));
                yz_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                m_code_login_label.setCompoundDrawablesWithIntrinsicBounds(null, null, null, ContextCompat.getDrawable(this, R.mipmap.login_type_dot));
                break;
            case R.id.forget_password_btn:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.login_btn:
                startActivity(MainActivity.class);
                finish();
                break;
            case R.id.weixin_login_btn:
                startActivity(ThirdPartyLoginActivity.class);
                break;
        }
    }


}
