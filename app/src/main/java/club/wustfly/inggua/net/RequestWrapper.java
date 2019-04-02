package club.wustfly.inggua.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import club.wustfly.inggua.YinGuaApplication;
import club.wustfly.inggua.model.req.BindPhoneNumParam;
import club.wustfly.inggua.model.req.ForgetPwdParam;
import club.wustfly.inggua.model.req.GetOrderDetailParam;
import club.wustfly.inggua.model.req.GetOrderListParam;
import club.wustfly.inggua.model.req.LoginParam;
import club.wustfly.inggua.model.req.ModifyNicknameParam;
import club.wustfly.inggua.model.req.ObtainMyDocumentParam;
import club.wustfly.inggua.model.req.ObtainVerifyCodeRequestParam;
import club.wustfly.inggua.model.req.RegisterParam;
import club.wustfly.inggua.model.req.SelectPayParam;
import club.wustfly.inggua.model.req.UploadFileParam;
import club.wustfly.inggua.model.req.WXLoginParam;
import club.wustfly.inggua.model.resp.BindPhoneNumRespDto;
import club.wustfly.inggua.model.resp.ForgetPwdRespDto;
import club.wustfly.inggua.model.resp.GetBannerImgRespDto;
import club.wustfly.inggua.model.resp.GetOrderDetailRespDto;
import club.wustfly.inggua.model.resp.GetOrderListRespDto;
import club.wustfly.inggua.model.resp.LoginRespDto;
import club.wustfly.inggua.model.resp.ModifyNicknameRespDto;
import club.wustfly.inggua.model.resp.ObtainMyDocumentRespDto;
import club.wustfly.inggua.model.resp.ObtainVerifyCodeRespDto;
import club.wustfly.inggua.model.resp.RegisterRespDto;
import club.wustfly.inggua.model.resp.SelectPayRespDto;
import club.wustfly.inggua.model.resp.UploadFileRespDto;
import club.wustfly.inggua.model.resp.WXLoginRespDto;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RequestWrapper {

    public static void login(LoginParam param) {

        Retrofit.getService().login(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<LoginRespDto>() {
        });
    }

    public static void register(RegisterParam param) {
        Retrofit.getService().register(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<RegisterRespDto>() {
        });
    }

    public static void forgetPwd(ForgetPwdParam param) {
        Retrofit.getService().forgetPwd(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<ForgetPwdRespDto>() {
        });
    }

    public static void wxLogin(WXLoginParam param) {
        Retrofit.getService().wxLogin(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<WXLoginRespDto>() {
        });
    }

    public static void getBannerImg() {
        Retrofit.getService().getBannerImg().enqueue(new AbsCallbackWrapper<GetBannerImgRespDto>() {
        });
    }

    public static void modifyNickname(ModifyNicknameParam param) {
        Retrofit.getService().modifyNickname(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<ModifyNicknameRespDto>() {
        });
    }

    public static void getOrderList(final GetOrderListParam param) {
        Retrofit.getService().getOrderList(param.getUid(), param.getOption(), param.getPage()).enqueue(new AbsCallbackWrapper<GetOrderListRespDto>() {
            @Override
            protected void onSuccess(GetOrderListRespDto respDto) {
                GetOrderListRespDto respDto1 = respDto == null ? new GetOrderListRespDto() : respDto;
                respDto1.setTag(param.getTag());
                EventBus.getDefault().post(respDto1);
            }
        });
    }

    public static void getOrderDetail(GetOrderDetailParam param) {
        Retrofit.getService().getOrderDetail(param.getId()).enqueue(new AbsCallbackWrapper<GetOrderDetailRespDto>() {
        });
    }


    public static void selectPay(SelectPayParam param) {
        Retrofit.getService().selectPay(param.getOid()).enqueue(new AbsCallbackWrapper<SelectPayRespDto>() {
        });
    }

    /**
     * @param param.type 1登陆 2注册 3忘记密码 4绑定手机号码
     */
    public static void obtainVerifyCode(final ObtainVerifyCodeRequestParam param) {
        Retrofit.getService().obtainVerifyCode(param.getPhone(), param.getType(), param.getCode()).enqueue(new AbsCallbackWrapper<ObtainVerifyCodeRespDto>() {
            @Override
            protected void onSuccess(ObtainVerifyCodeRespDto respDto) {
                ObtainVerifyCodeRespDto respDto1 = respDto == null ? new ObtainVerifyCodeRespDto() : respDto;
                respDto1.setFrom(param.getFrom());
                EventBus.getDefault().post(respDto1);
            }

            @Override
            protected void onFail(CharSequence errorMsg) {
                Toast.makeText(YinGuaApplication.getInstance(), errorMsg == null ? "请求次数超限，请一小时后重试" : errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void bindPhoneNum(BindPhoneNumParam param) {
        Retrofit.getService().bindPhoneNum(Retrofit.convert(param)).enqueue(new AbsCallbackWrapper<BindPhoneNumRespDto>() {
        });
    }

    public static void uploadFile(UploadFileParam param) {

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("uid", param.getUid());
        for (String ufile : param.getUfile()) {
            File file = new File(ufile);
            builder.addFormDataPart("ufile[]", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
        }

        RequestBody requestBody = builder.build();

        Retrofit.getService().uploadFile(requestBody).enqueue(new AbsCallbackWrapper<UploadFileRespDto>() {
        });

    }

    /**
     * @param param.type 1 word    2 pdf     3 ppt
     */
    public static void obtainMyDocument(ObtainMyDocumentParam param) {
        Retrofit.getService().obtainMyDocment(param.getUid(), param.getType()).enqueue(new AbsCallbackWrapper<ObtainMyDocumentRespDto>() {
        });
    }
}
