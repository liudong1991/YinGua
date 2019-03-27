package club.wustfly.yingua.net;

import club.wustfly.yingua.model.resp.LoginRespDto;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface kktService {

//    @POST("/sms/send")
//    Call<RespDto<SmsResp>> sendSms(@Body RequestBody route);
//
//    @Multipart
//    @POST("/exchange/imgUpload")
//    Call<RespDto<UploadImgResp>> uploadImg(@Part List<MultipartBody.Part> parts);


    @POST("/home/login/dologin")
    Call<LoginRespDto> login(@Body RequestBody route);

}
