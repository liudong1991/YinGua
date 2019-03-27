package club.wustfly.yingua.model.resp;

import club.wustfly.yingua.model.BaseModel;

public class User extends BaseModel {


    /**
     * id : 1
     * username : 序章
     * phone : 16620021030
     * restime : 1551429755
     * status : 1
     * headimg : /static/upload/headimg/default.png
     * password :
     */

    private int id;
    private String username;
    private String phone;
    private String restime;
    private int status;
    private String headimg;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRestime() {
        return restime;
    }

    public void setRestime(String restime) {
        this.restime = restime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
