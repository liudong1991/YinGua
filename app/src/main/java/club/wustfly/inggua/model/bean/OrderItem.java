package club.wustfly.inggua.model.bean;

import club.wustfly.inggua.model.BaseModel;

public class OrderItem extends BaseModel {


    /**
     * id : 6
     * ordernum : 2019030655100971
     * status : 2
     * number : 2
     * page : 0
     * total : 25
     * packfree : 5
     * size : A3
     * issingle : 双页
     * color : 彩色
     * layout : 每版一页
     */

    private Integer id;
    private String ordernum;
    private Integer status;
    private Integer number;
    private Integer page;
    private String total;
    private String packfree;
    private String size;
    private String issingle;
    private String color;
    private String layout;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIssingle() {
        return issingle;
    }

    public void setIssingle(String issingle) {
        this.issingle = issingle;
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
}
