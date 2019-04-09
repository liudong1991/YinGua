package club.wustfly.inggua.model.req;

import club.wustfly.inggua.model.BaseModel;

public class SubmitOrderParam extends BaseModel {

    private String fid;
    private String uid;
    private String number;
    private String total;
    private String packfree;
    private String money;
    private String page;
    private String apptime;
    private String gid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getApptime() {
        return apptime;
    }

    public void setApptime(String apptime) {
        this.apptime = apptime;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
