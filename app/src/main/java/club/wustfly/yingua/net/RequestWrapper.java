package club.wustfly.yingua.net;

import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import club.wustfly.yingua.YinGuaApplication;
import club.wustfly.yingua.model.req.LoginParam;
import club.wustfly.yingua.model.resp.LoginRespDto;

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
        Retrofit.getService().login(Retrofit.convert(param)).enqueue(new CallbackWrapper<LoginRespDto>() {
            @Override
            protected void onSuccess(LoginRespDto loginRespDto) {
                EventBus.getDefault().post(loginRespDto == null ? new LoginRespDto() : loginRespDto);
            }

            @Override
            protected void onFail(String errorMsg) {
                Toast.makeText(YinGuaApplication.getInstance(), errorMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
