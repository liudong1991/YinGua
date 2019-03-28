package club.wustfly.yingua.net;

import org.greenrobot.eventbus.EventBus;

import club.wustfly.yingua.model.req.ForgetPwdParam;
import club.wustfly.yingua.model.req.GetOrderDetailParam;
import club.wustfly.yingua.model.req.GetOrderListParam;
import club.wustfly.yingua.model.req.LoginParam;
import club.wustfly.yingua.model.req.ModifyNicknameParam;
import club.wustfly.yingua.model.req.RegisterParam;
import club.wustfly.yingua.model.req.WXLoginParam;
import club.wustfly.yingua.model.resp.ForgetPwdRespDto;
import club.wustfly.yingua.model.resp.GetBannerImgRespDto;
import club.wustfly.yingua.model.resp.GetOrderDetailRespDto;
import club.wustfly.yingua.model.resp.GetOrderListRespDto;
import club.wustfly.yingua.model.resp.LoginRespDto;
import club.wustfly.yingua.model.resp.ModifyNicknameRespDto;
import club.wustfly.yingua.model.resp.RegisterRespDto;
import club.wustfly.yingua.model.resp.WXLoginRespDto;

public class RequestWrapper {

//    public static void sendSms(SmsParam smsParam) {
//        Retrofit.getService().sendSms(Retrofit.convert(smsParam)).enqueue(new CallbackWrapper<SmsResp>() {
//            @Override
//            protected void onSuccess(SmsResp smsResp) {
//                EventBus.getDefault().post(smsResp == null ? new SmsResp() : smsResp);
//            }
//
//            @Override
//            protected void onFail(String errorMsg) {
//                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public static void uploadImg(final String param) {
//        Retrofit.getService().uploadImg(Retrofit.convertImgUpload(param)).enqueue(new CallbackWrapper<UploadImgResp>() {
//            @Override
//            protected void onSuccess(UploadImgResp uploadImgResp) {
//                EventBus.getDefault().post(uploadImgResp == null ? new UploadImgResp(param) : uploadImgResp.setImgPath(param));
//            }
//
//            @Override
//            protected void onFail(String errorMsg) {
//                Toast.makeText(KKTApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
//            }
//        });
//    }

    public static void login(LoginParam param) {
        Retrofit.getService().login(Retrofit.convert(param)).enqueue(new CallbackWrapper<LoginRespDto>());
    }

    public static void register(RegisterParam param) {
        Retrofit.getService().register(Retrofit.convert(param)).enqueue(new CallbackWrapper<RegisterRespDto>());
    }

    public static void forgetPwd(ForgetPwdParam param) {
        Retrofit.getService().forgetPwd(Retrofit.convert(param)).enqueue(new CallbackWrapper<ForgetPwdRespDto>());
    }

    public static void wxLogin(WXLoginParam param) {
        Retrofit.getService().wxLogin(Retrofit.convert(param)).enqueue(new CallbackWrapper<WXLoginRespDto>());
    }

    public static void getBannerImg() {
        Retrofit.getService().getBannerImg().enqueue(new CallbackWrapper<GetBannerImgRespDto>());
    }

    public static void modifyNickname(ModifyNicknameParam param) {
        Retrofit.getService().modifyNickname(Retrofit.convert(param)).enqueue(new CallbackWrapper<ModifyNicknameRespDto>());
    }

    public static void getOrderList(final GetOrderListParam param) {
        Retrofit.getService().getOrderList(param.getUid(), param.getOption(), param.getPage()).enqueue(new CallbackWrapper<GetOrderListRespDto>() {
            @Override
            protected void onSuccess(GetOrderListRespDto respDto) {
                GetOrderListRespDto respDto1 = respDto == null ? new GetOrderListRespDto() : respDto;
                respDto1.setTag(param.getTag());
                EventBus.getDefault().post(respDto1);
            }
        });
    }

    public static void getOrderDetail(GetOrderDetailParam param){
        Retrofit.getService().getOrderDetail(param.getId()).enqueue(new CallbackWrapper<GetOrderDetailRespDto>());
    }

}
