package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.OrderItem;

public class SelectPayRespDto extends RespDto {

    private OrderItem order;

    public OrderItem getOrder() {
        return order;
    }

    public void setOrder(OrderItem order) {
        this.order = order;
    }
}
