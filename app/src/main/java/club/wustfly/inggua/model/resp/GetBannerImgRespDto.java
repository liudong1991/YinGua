package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.BannerItem;
import club.wustfly.inggua.model.bean.OrderSet;

public class GetBannerImgRespDto extends RespDto {


    List<BannerItem> banner;
    OrderSet set;

    public List<BannerItem> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerItem> banner) {
        this.banner = banner;
    }

    public OrderSet getSet() {
        return set;
    }

    public void setSet(OrderSet set) {
        this.set = set;
    }
}
