package club.wustfly.yingua.model.req;

import club.wustfly.yingua.model.BaseModel;

public class GetOrderListParam extends BaseModel {

    private Integer uid;
    private String option;
    private Integer page;
    private String tag;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
