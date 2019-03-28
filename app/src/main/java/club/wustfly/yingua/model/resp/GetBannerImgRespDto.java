package club.wustfly.yingua.model.resp;

import java.util.List;

import club.wustfly.yingua.model.RespDto;
import club.wustfly.yingua.model.bean.BannerItem;

public class GetBannerImgRespDto extends RespDto {


    List<BannerItem> banner;

    public List<BannerItem> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerItem> banner) {
        this.banner = banner;
    }
}
