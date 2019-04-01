package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.OrderDetailItem;

public class GetOrderDetailRespDto extends RespDto {

    private OrderDetailItem order;

    public OrderDetailItem getOrder() {
        return order;
    }

    public void setOrder(OrderDetailItem order) {
        this.order = order;
    }
}
