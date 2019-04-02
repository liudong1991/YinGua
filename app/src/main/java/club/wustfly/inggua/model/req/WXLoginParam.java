package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class WXLoginParam extends BaseModel {

    private String openid;

    private String nickname;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
