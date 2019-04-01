package club.wustfly.inggua.model.resp;

import java.util.List;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.OrderItem;

public class GetOrderListRespDto extends RespDto {

    private String tag;

    private List<OrderItem> order;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<OrderItem> getOrder() {
        return order;
    }

    public void setOrder(List<OrderItem> order) {
        this.order = order;
    }
}
