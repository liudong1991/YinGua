package club.wustfly.inggua.net;

import club.wustfly.inggua.model.resp.BindPhoneNumRespDto;
import club.wustfly.inggua.model.resp.ForgetPwdRespDto;
import club.wustfly.inggua.model.resp.GetBannerImgRespDto;
import club.wustfly.inggua.model.resp.GetOrderDetailRespDto;
import club.wustfly.inggua.model.resp.GetOrderListRespDto;
import club.wustfly.inggua.model.resp.LoginRespDto;
import club.wustfly.inggua.model.resp.ModifyNicknameRespDto;
import club.wustfly.inggua.model.resp.ObtainEditRespDto;
import club.wustfly.inggua.model.resp.ObtainMyDocumentRespDto;
import club.wustfly.inggua.model.resp.ObtainVerifyCodeRespDto;
import club.wustfly.inggua.model.resp.RegisterRespDto;
import club.wustfly.inggua.model.resp.SelectPayRespDto;
import club.wustfly.inggua.model.resp.UpdateAddressRespDto;
import club.wustfly.inggua.model.resp.UpdateHeadImgRespDto;
import club.wustfly.inggua.model.resp.UploadFileRespDto;
import club.wustfly.inggua.model.resp.WXLoginRespDto;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface kktService {

    @POST("/home/login/dologin")
    Call<LoginRespDto> login(@Body RequestBody route);

    @POST("/home/login/register")
    Call<RegisterRespDto> register(@Body RequestBody route);

    @POST("/home/login/forget")
    Call<ForgetPwdRespDto> forgetPwd(@Body RequestBody route);

    @POST("/home/login/wxlogin")
    Call<WXLoginRespDto> wxLogin(@Body RequestBody route);

    @GET("/home/index/index")
    Call<GetBannerImgRespDto> getBannerImg();

    @POST("/home/user/updateuser")
    Call<ModifyNicknameRespDto> modifyNickname(@Body RequestBody route);

    @GET("/home/order/order")
    Call<GetOrderListRespDto> getOrderList(@Query("uid") int uid, @Query("option") String option, @Query("page") int page);

    @GET("/home/order/orderinfo")
    Call<GetOrderDetailRespDto> getOrderDetail(@Query("id") int id);

    @GET("/home/order/paymode")
    Call<SelectPayRespDto> selectPay(@Query("oid") String oid);

    @GET("/home/login/sendcode")
    Call<ObtainVerifyCodeRespDto> obtainVerifyCode(@Query("phone") String phone, @Query("type") String type, @Query("code") String code);

    @POST("/home/login/bandphone")
    Call<BindPhoneNumRespDto> bindPhoneNum(@Body RequestBody route);

    @POST("/home/user/uploadfile")
    Call<UploadFileRespDto> uploadFile(@Body RequestBody route);

    @GET("/home/user/myfile")
    Call<ObtainMyDocumentRespDto> obtainMyDocment(@Query("uid") String uid, @Query("type") int type);

    @GET("/home/order/edit")
    Call<ObtainEditRespDto> obtainEdit(@Query("uid") int uid, @Query("fid") String fid);

    @POST("/home/order/updateAddress")
    Call<UpdateAddressRespDto> updateAddress(@Body RequestBody route);

    @POST("/home/user/updatehead")
    Call<UpdateHeadImgRespDto> updateHeadImg(@Body RequestBody route);

}
