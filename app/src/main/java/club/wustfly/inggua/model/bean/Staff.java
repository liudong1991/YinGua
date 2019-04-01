package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class Staff extends BaseModel {


    /**
     * staffname : 序章
     * phone : 16620021031
     */

    private String staffname;
    private String phone;

    public String getStaffname() {
        return staffname;
    }

    public void setStaffname(String staffname) {
        this.staffname = staffname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
