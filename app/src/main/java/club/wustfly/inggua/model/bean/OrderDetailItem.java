package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class OrderDetailItem extends BaseModel {


    /**
     * id : 55
     * status : 2
     * ordernum : 2019040957101975
     * page : 1
     * number : 1
     * total : 226.00
     * packfree : 123.00
     * addtime : 1554792729
     * deliverytime : null
     * finishtime : null
     * consignee : 刘丹
     * phone : 17603076004
     * address : 福永街道桥头村桥南274号
     * sid : null
     * money : 226.00
     * issingle : 双页
     * size : A3
     * color : 彩色
     * layout : 123
     * binding : 123
     * staff : []
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
    private String sid;
    private String money;
    private String issingle;
    private String size;
    private String color;
    private String layout;
    private String binding;
    private Staff staff;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
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

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getIssingle() {
        return issingle;
    }

    public void setIssingle(String issingle) {
        this.issingle = issingle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
