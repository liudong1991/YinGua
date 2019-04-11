package club.wustfly.inggua.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.net.URLEncoder;

import club.wustfly.inggua.model.bean.WxLoginBean;
import club.wustfly.inggua.model.bean.WxUserInfoBean;
import club.wustfly.inggua.model.event.RequestFinishEvent;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    public static String WX_APP_ID = "wx81649484d04cf872";
    public static String WX_APP_SECRET = "f8a42fe93227f71f4ef5dfa00032189f";
    // 获取第一步的code后，请求以下链接获取access_token
    private String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    // 获取用户个人信息
    private String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WX_APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code;
                String get_access_token = getCodeRequest(code);
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(get_access_token).get().build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        final String responseStr = response.body().string();
                        WxLoginBean wxLoginBean = new Gson().fromJson(responseStr, WxLoginBean.class);
                        getUserInfo(getUserInfo(wxLoginBean.getAccess_token(), wxLoginBean.getOpenid()));
                    }
                });

                finish();
                break;
//            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                result = "发送取消";
//                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//                finish();
//                break;
//            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                result = "发送被拒绝";
//                Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//                finish();
//                break;
            default:
                EventBus.getDefault().post(new RequestFinishEvent());
                finish();
                break;
        }
    }

    /**
     * 通过拼接的用户信息url获取用户信息
     *
     * @param user_info_url
     */
    private void getUserInfo(String user_info_url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(user_info_url).get().build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                WxUserInfoBean wxUserInfoBean = new Gson().fromJson(responseStr, WxUserInfoBean.class);
                EventBus.getDefault().post(wxUserInfoBean);
            }
        });

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    /**
     * 获取access_token的URL（微信）
     *
     * @param code 授权时，微信回调给的
     * @return URL
     */
    private String getCodeRequest(String code) {
        String result = null;
        GetCodeRequest = GetCodeRequest.replace("APPID",
                urlEnodeUTF8(WX_APP_ID));
        GetCodeRequest = GetCodeRequest.replace("SECRET",
                urlEnodeUTF8(WX_APP_SECRET));
        GetCodeRequest = GetCodeRequest.replace("CODE", urlEnodeUTF8(code));
        result = GetCodeRequest;
        return result;
    }

    /**
     * 获取用户个人信息的URL（微信）
     *
     * @param access_token 获取access_token时给的
     * @param openid       获取access_token时给的
     * @return URL
     */
    private String getUserInfo(String access_token, String openid) {
        String result = null;
        GetUserInfo = GetUserInfo.replace("ACCESS_TOKEN",
                urlEnodeUTF8(access_token));
        GetUserInfo = GetUserInfo.replace("OPENID", urlEnodeUTF8(openid));
        result = GetUserInfo;
        return result;
    }

    private String urlEnodeUTF8(String str) {
        String result = str;
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}