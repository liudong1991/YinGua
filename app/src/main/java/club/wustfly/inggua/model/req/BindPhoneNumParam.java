package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class BindPhoneNumParam extends BaseModel {

    private String phone;
    private String id;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
