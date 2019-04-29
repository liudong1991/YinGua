package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.Address;
import club.wustfly.inggua.model.bean.CouponItem;
import club.wustfly.inggua.model.bean.FileItem;
import club.wustfly.inggua.model.bean.GoodItem;
import club.wustfly.inggua.model.bean.OrderSet;
import club.wustfly.inggua.model.bean.OrderUserItem;

public class ObtainEditRespDto extends RespDto {

    private List<FileItem> file;
    private List<GoodItem> good;
    private List<CouponItem> coupon;
    private List<Address> address;
    private OrderUserItem user;
    private OrderSet set;

    public List<FileItem> getFile() {
        return file;
    }

    public void setFile(List<FileItem> file) {
        this.file = file;
    }

    public List<GoodItem> getGood() {
        return good;
    }

    public void setGood(List<GoodItem> good) {
        this.good = good;
    }

    public List<CouponItem> getCoupon() {
        return coupon;
    }

    public void setCoupon(List<CouponItem> coupon) {
        this.coupon = coupon;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public OrderUserItem getUser() {
        return user;
    }

    public void setUser(OrderUserItem user) {
        this.user = user;
    }

    public OrderSet getSet() {
        return set;
    }

    public void setSet(OrderSet set) {
        this.set = set;
    }
}
