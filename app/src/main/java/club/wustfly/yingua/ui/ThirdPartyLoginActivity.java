package club.wustfly.yingua.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;
import club.wustfly.yingua.MainActivity;
import club.wustfly.yingua.R;
import club.wustfly.yingua.cache.Session;
import club.wustfly.yingua.model.event.LoginFinishEvent;
import club.wustfly.yingua.model.req.WXLoginParam;
import club.wustfly.yingua.model.resp.WXLoginRespDto;
import club.wustfly.yingua.net.RequestWrapper;
import club.wustfly.yingua.ui.base.BaseActivity;

public class ThirdPartyLoginActivity extends BaseActivity {

    String openid = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.third_party_login_layout);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setTitle("登录");
        setBack();
        setHeaderTopMargin();

        //todo:微信登录传入openid
        openid = getIntent().getStringExtra("openid");

    }

    @OnClick({R.id.login_btn})
    void handleClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                wxLogin();
                break;
        }
    }

    private void wxLogin() {
        WXLoginParam param = new WXLoginParam();
        param.setOpenid(openid);

        showProgressDialog();
        RequestWrapper.wxLogin(param);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveWxLoginResult(WXLoginRespDto wxLoginRespDto) {
        Session.getSession().login(wxLoginRespDto.getUser());
        startActivity(MainActivity.class);
        EventBus.getDefault().post(new LoginFinishEvent());
        finish();
    }


}
