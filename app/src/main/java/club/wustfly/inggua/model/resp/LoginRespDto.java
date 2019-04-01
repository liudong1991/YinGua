package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.User;

public class LoginRespDto extends RespDto {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
