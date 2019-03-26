package club.wustfly.yingua.model;

public class Address {

    private String name = "";
    private String phone = "";
    private String address = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        this.name = address.getName();
        this.phone = address.getPhone();
        this.address = address.getAddress();
    }
}
