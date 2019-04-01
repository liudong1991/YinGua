package club.wustfly.inggua.model.req;

import java.util.Random;

import club.wustfly.inggua.model.BaseModel;
import club.wustfly.inggua.utils.Util;

public class ObtainVerifyCodeRequestParam extends BaseModel {


    private String phone;
    private String type;
    private String code = Util.generateVerifycode();
    private String from;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param type 1登陆 2注册 3忘记密码 4绑定手机号码
     */
    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    private static int randomVerifyCode() {
        Random random = new Random();
        return 0;
    }
}
