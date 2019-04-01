package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class OrderDetailItem extends BaseModel {


    /**
     * id : 6
     * status : 4
     * ordernum : 2019030655100971
     * page : 0
     * number : 2
     * total : 25
     * packfree : 5
     * addtime : 1551864119
     * deliverytime : 1551930236
     * finishtime : 1551930307
     * consignee : 序章
     * phone : 16620021031
     * address : 广东省广州海珠区海港花园
     * sid : 1
     * goodprice : 120
     * staff : {"staffname":"序章","phone":"16620021031"}
     */

    private Integer id;
    private Integer status;
    private String ordernum;
    private Integer page;
    private Integer number;
    private String total;
    private String packfree;
    private String addtime;
    private String deliverytime;
    private String finishtime;
    private String consignee;
    private String phone;
    private String address;
    private Integer sid;
    private Integer goodprice;
    private Staff staff;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPackfree() {
        return packfree;
    }

    public void setPackfree(String packfree) {
        this.packfree = packfree;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getDeliverytime() {
        return deliverytime;
    }

    public void setDeliverytime(String deliverytime) {
        this.deliverytime = deliverytime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
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

    public Integer getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public Integer getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(int goodprice) {
        this.goodprice = goodprice;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
