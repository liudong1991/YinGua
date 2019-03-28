package club.wustfly.yingua.model.req;

import club.wustfly.yingua.model.BaseModel;

public class WXLoginParam extends BaseModel {

    private String openid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
