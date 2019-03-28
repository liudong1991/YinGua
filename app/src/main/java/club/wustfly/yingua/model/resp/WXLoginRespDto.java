package club.wustfly.yingua.model.resp;

import club.wustfly.yingua.model.RespDto;
import club.wustfly.yingua.model.bean.User;

public class WXLoginRespDto extends RespDto {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
