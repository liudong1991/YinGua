package club.wustfly.inggua.model.bean;

import android.text.TextUtils;

import club.wustfly.inggua.model.BaseModel;

public class Address extends BaseModel {

    private Integer id;
    private String consignee = "";
    private String phone = "";
    private String address = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setValue(Address address) {
        this.consignee = address.getConsignee();
        this.phone = address.getPhone();
        this.address = address.getAddress();
    }

    public boolean isOk() {
        return !(TextUtils.isEmpty(consignee) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address));
    }
}
