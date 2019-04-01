package club.wustfly.inggua.model.resp;

import club.wustfly.inggua.model.RespDto;
import club.wustfly.inggua.model.bean.OrderItem;

public class SelectPayRespDto extends RespDto {

    private OrderItem orderItem;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
