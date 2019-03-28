package club.wustfly.yingua.model.resp;

import club.wustfly.yingua.model.RespDto;
import club.wustfly.yingua.model.bean.OrderDetailItem;

public class GetOrderDetailRespDto extends RespDto {

    private OrderDetailItem order;

    public OrderDetailItem getOrder() {
        return order;
    }

    public void setOrder(OrderDetailItem order) {
        this.order = order;
    }
}
