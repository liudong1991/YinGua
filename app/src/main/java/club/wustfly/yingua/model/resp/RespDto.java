package club.wustfly.yingua.model.resp;

import club.wustfly.yingua.model.BaseModel;

public class RespDto extends BaseModel {

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
